package com.cch.cz.ctrl;

import com.alibaba.fastjson.JSON;
import com.cch.cz.base.AjaxReturn;
import com.cch.cz.base.Table;
import com.cch.cz.common.UtilFun;
import com.cch.cz.entity.Supplement;
import com.cch.cz.entity.SupplementUrge;
import com.cch.cz.entity.UrgeRecord;
import com.cch.cz.mapper.SupplementUrgeMapper;
import com.cch.cz.service.CompanyService;
import com.cch.cz.service.SupplementUrgeService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping(value = "/surge")
public class SupplementUrgeCtrl {
    @Resource
    private SupplementUrgeService supplementUrgeServicer;
    @Resource
    private CompanyService companyService;

    @PostMapping(value = "/add")
    @ResponseBody
    public AjaxReturn add(@RequestBody SupplementUrge urgeRecord) {
        urgeRecord.setCreateDate(UtilFun.DateToString(new Date(), UtilFun.YYYYMMDD));
        supplementUrgeServicer.save(urgeRecord);
        return new AjaxReturn(0, "添加成功");
    }

    @PostMapping(value = "/bycase")
    @ResponseBody
    public Table listByCase(@RequestParam Long cases) {
        SupplementUrge supplementUrge = new SupplementUrge();
        supplementUrge.setCaseId(cases);
        List<SupplementUrge> list = supplementUrgeServicer.findByEntity(supplementUrge);
        Table table = new Table();
        table.setData(list);
        table.setCount(list.size());
        return table;
    }

    @GetMapping(value = "/manager")
    public String manager(Model model) {
        model.addAttribute("companys", companyService.findAll());
        return "/cz/surge/manager";
    }

    @PostMapping(value = "/list")
    @ResponseBody
    public Table list(@RequestParam int page,
                      @RequestParam int limit,
                      @RequestParam(defaultValue = "") String where) {
        Map c = new HashMap();
        if (UtilFun.isEmptyString(where)) c = JSON.parseObject(where, Map.class);
        PageHelper.startPage(page, limit);

        List<Map> list = supplementUrgeServicer.manager(c);
        Page<Map> p = (Page<Map>) list;
        Table table = new Table();
        table.setData(list);
        table.setCount((int) p.getTotal());
        return table;
    }


    @PostMapping(value = "/exp", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public void list(HttpServletResponse response,
                     HttpServletRequest request,
                     @RequestParam(defaultValue = "") String where) throws IOException {
        //数据
        Map c = new HashMap();
        if (UtilFun.isEmptyString(where)) c = JSON.parseObject(where, Map.class);
        List<Map> list = supplementUrgeServicer.manager(c);
        String[] title = {"合同号", "客户姓名", "性别", "身份证", "总欠款", "客户手机", "催收时间", "催收记录", "催收员", "备注"};
        String realPath = request.getSession().getServletContext().getRealPath("");
        File realPathDirectory = new File(realPath);
        File downDir = new File(realPathDirectory.getParent() + File.separator + "urge" + File.separator);
        if (!downDir.exists()) {
            downDir.mkdirs();
        }
        File outputFile = new File(downDir + File.separator + new Date().getTime() + "-" + "催记.xlsx");
        Workbook w = new XSSFWorkbook();
        Sheet sheet = w.createSheet("sheet1");
        //表頭信息
        Row row = sheet.createRow(0);
        for (int i = 0; i < title.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(title[i]);
        }
        //内容
        for (int i = 0; i < list.size(); i++) {
            Row crow = sheet.createRow(i + 1);
            for (int j = 0; j < title.length; j++) {
                Cell cell = crow.createCell(j);
                cell.setCellType(CellType.STRING);
                if (title[j].equals("合同号")) cell.setCellValue((String) list.get(i).get("contractNum"));
                if (title[j].equals("客户姓名") || title[j].equals("债务人姓名"))
                    cell.setCellValue((String) list.get(i).get("cname"));
                if (title[j].equals("性别")) cell.setCellValue((String) list.get(i).get("sex"));
                if (title[j].equals("身份证")) cell.setCellValue((String) list.get(i).get("idCard"));
                if (title[j].equals("总欠款")) cell.setCellValue((String) list.get(i).get("sumArrears"));
                if (title[j].equals("客户手机")) cell.setCellValue((String) list.get(i).get("customerPhoneNumber"));
//                if(title.get(j).equals("客户办公电话"))cell.setCellValue((String) list.get(i).get("customerOfficePhone"));
//                if(title.get(j).equals("客户配偶姓名"))cell.setCellValue((String) list.get(i).get("customerSpouse"));
//                if(title.get(j).equals("客户配偶联系电话"))cell.setCellValue((String) list.get(i).get("customerSpousePhone"));
//                if(title.get(j).equals("客户亲戚姓名"))cell.setCellValue((String) list.get(i).get("customerRelativeName"));
//                if(title.get(j).equals("客户与亲戚关系"))cell.setCellValue((String) list.get(i).get("customerRelationship"));
//                if(title.get(j).equals("客户亲戚联系电话"))cell.setCellValue((String) list.get(i).get("customerRelativePhone"));
//                if(title.get(j).equals("其他联系人姓名"))cell.setCellValue((String) list.get(i).get("customerRelativeOther"));
//                if(title.get(j).equals("其他联系人关系"))cell.setCellValue((String) list.get(i).get("customerRelaOther"));
//                if(title.get(j).equals("其他联系人电话"))cell.setCellValue((String) list.get(i).get("customerOtherPhone"));
                if (title[j].equals("催收时间"))
                    cell.setCellValue(UtilFun.DateToString(UtilFun.StringToDate(list.get(i).get("createDate").toString(), UtilFun.YMD), UtilFun.YMD));
                if (title[j].equals("催收日期"))
                    cell.setCellValue(UtilFun.DateToString(UtilFun.StringToDate(list.get(i).get("createDate").toString(), UtilFun.YYYYMMDD), UtilFun.YYYYMMDD));
                if (title[j].equals("催收记录")) cell.setCellValue((String) list.get(i).get("result"));
                if (title[j].equals("催收拨打号码")) cell.setCellValue((String) list.get(i).get("target"));
                if (title[j].equals("催收方式")) cell.setCellValue("电联");
                if (title[j].equals("案件状态")) cell.setCellValue((String) list.get(i).get("status"));
                if (title[j].equals("催收详细情况")) cell.setCellValue((String) list.get(i).get("rmarks"));
                if (title[j].equals("催收员")) cell.setCellValue((String) list.get(i).get("sname"));
                if (title[j].equals("备注")) cell.setCellValue((String) list.get(i).get("rmarks"));
//                if(title.get(j).equals("提线流水号"))cell.setCellValue((String) list.get(i).get("result"));
            }
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode((UtilFun.DateToString(new Date(), UtilFun.YMD) + "-协查.xlsx"), "UTF-8"));
        response.flushBuffer();
        w.write(response.getOutputStream());
    }




}