package com.cch.cz.base.ctrl;

import com.cch.cz.authority.entity.User;
import com.cch.cz.base.AjaxReturn;
import com.cch.cz.common.ExcelTool;
import com.cch.cz.common.UploadUtil;
import com.cch.cz.common.UtilFun;
import com.cch.cz.entity.Cases;
import com.cch.cz.entity.Staff;
import com.cch.cz.entity.Supplement;
import com.cch.cz.entity.UploadLog;
import com.cch.cz.exception.UploadException;
import com.cch.cz.service.UploadLogService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Controller
public class Supplementa {
    @Resource
    private UploadLogService uploadLogService;
    @RequestMapping(value = "/supplementa")
    @ResponseBody
    public AjaxReturn supplement (HttpServletRequest request) throws UploadException {
        AjaxReturn ajaxRetrun = new AjaxReturn();
        Map data = new HashMap();
        ajaxRetrun.setCode(1);
        try {
            File name = UploadUtil.upload(request,"archcase/");
            Workbook workbook = WorkbookFactory.create(new FileInputStream(name));
            List<Supplement> result = new ArrayList();
            String filename=name.getName().substring(0,name.getName().lastIndexOf("."));
            String supplementname[] =filename.split("-");
            List<Sheet> sheets = new ArrayList<>();
            if (supplementname.length<3){
                ajaxRetrun.setMessage("文件名格式错误");
                return ajaxRetrun;
            }

            //遍历sheet
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                sheets.add(sheet);
                for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
                    Cell a = sheet.getRow(0).getCell(j);
                    if (null == sheet.getRow(0).getCell(j) || ExcelTool.getCellValue(sheet.getRow(0).getCell(j)).trim() == null) {
                        throw new UploadException("表头存在空值");
                    }
                }
            }

            data.put("path","http://localhost:8080/image/"+name);
            data.put("data",result);
        } catch (IOException e) {
            ajaxRetrun.setMessage("上传失败");
            e.printStackTrace ();
            return ajaxRetrun;
        } catch (InvalidFormatException e) {
            ajaxRetrun.setMessage("上传失败");
            e.printStackTrace();
            return ajaxRetrun;
        }catch (UploadException u){
            ajaxRetrun.setMessage(u.getMessage());
            u.printStackTrace();
            return ajaxRetrun;
        } catch (Exception e){
            ajaxRetrun.setMessage("上传失败");
            e.printStackTrace();
            return ajaxRetrun;
        }
        UploadLog log = new UploadLog();
        log.setDate(new Date());
        User user= (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        log.setUser(user.getUserName());
        uploadLogService.save(log);
        ajaxRetrun.setData(data);
        ajaxRetrun.setCode(0);
        ajaxRetrun.setMessage("上传成功");
        return ajaxRetrun;
    }
}
