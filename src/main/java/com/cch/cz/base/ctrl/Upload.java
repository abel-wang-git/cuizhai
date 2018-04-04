package com.cch.cz.base.ctrl;

import com.cch.cz.base.AjaxReturn;
import com.cch.cz.common.ExcelTool;
import com.cch.cz.common.Execl;
import com.cch.cz.common.UploadUtil;
import com.cch.cz.common.UtilFun;
import com.cch.cz.entity.Cases;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/16.
 * 文件上传
 */
@Controller
public class Upload {

    @RequestMapping(value = "/upload")
    @ResponseBody
    public AjaxReturn uploadImage (HttpServletRequest request) {
        AjaxReturn ajaxRetrun = new AjaxReturn();
        Map data = new HashMap();
        try {
            File name = UploadUtil.upload(request,"image/");
            List<Cases> list= ExcelTool.read(name,1);
            data.put("path","http://localhost:8080/image/"+name);
            data.put("data",list);
        } catch (IOException e) {
            ajaxRetrun.setMessage("上传失败");
            e.printStackTrace ();
        } catch (InvalidFormatException e) {
            ajaxRetrun.setMessage("上传失败");
            e.printStackTrace();
        }
        ajaxRetrun.setData(data);
        ajaxRetrun.setMessage("上传成功");
        return ajaxRetrun;
    }
}
