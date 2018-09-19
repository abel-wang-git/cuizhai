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
        Workbook w = setFile(request, list,c);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode((UtilFun.DateToString(new Date(), UtilFun.YMD) + "-协查.xlsx"), "UTF-8"));
        response.flushBuffer();
        w.write(response.getOutputStream());
    }

    private Workbook setFile(HttpServletRequest request, List<Map> list,Map c) {
        String[] title = {"合同号", "客户姓名", "性别", "身份证", "总欠款", "客户手机", "催收时间", "催收记录", "催收员", "备注"};
        String[] title1 = {"公司名称", "委托日期", "客户姓名", "合同号", "联络时间", "联络类型", "联络号码", "联络人", "结果代码", "催收记录"};

        String realPath = request.getSession().getServletContext().getRealPath("");
        File realPathDirectory = new File(realPath);
        File downDir = new File(realPathDirectory.getParent() + File.separator + "urge" + File.separator);
        if (!downDir.exists()) {
            downDir.mkdirs();
        }
        File outputFile = new File(downDir + File.separator + new Date().getTime() + "-" + "催记.xlsx");
        Workbook w = new XSSFWorkbook();
        Sheet sheet = w.createSheet("sheet1");
        if(c.get("type").equals("宜信")){
            title=title1;
            setyiContent(list,title,sheet);

        }else{
            setContent(list, title, sheet);
        }
        //表頭信息
        Row row = sheet.createRow(0);
        for (int i = 0; i < title.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(title[i]);
        }

        return w;
    }

    private void setyiContent(List<Map> list, String[] title, Sheet sheet) {

        for (int i = 0; i < list.size(); i++) {
            Row crow = sheet.createRow(i + 1);
            for (int j = 0; j < title.length; j++) {
                String[] title1 = {"公司名称", "委托日期", "客户姓名", "合同号", "联络时间", "联络类型", "联络号码", "联络人", "结果代码", "催收记录"};

                Cell cell = crow.createCell(j);
                cell.setCellType(CellType.STRING);
                if (title[j].equals("公司名称")) cell.setCellValue("宏鑫通");
                if (title[j].equals("委托日期")) cell.setCellValue((String) list.get(i).get("appointData"));
                if (title[j].equals("客户姓名") || title[j].equals("债务人姓名")) cell.setCellValue((String) list.get(i).get("cname"));
                if (title[j].equals("合同号")) cell.setCellValue((String) list.get(i).get("contractNum"));
                if (title[j].equals("联络时间")) cell.setCellValue(UtilFun.DateToString(UtilFun.StringToDate(list.get(i).get("createDate").toString(), UtilFun.YYYYMMDD), UtilFun.YYYYMMDD));
                if (title[j].equals("联络类型")) cell.setCellValue("01");

                if (title[j].equals("联络号码")) cell.setCellValue(list.get(i).get("suTarget").toString().split("]")[1].replace("'",""));
                if (title[j].equals("联络人")) cell.setCellValue(list.get(i).get("suTarget").toString().split("]")[0].replace("[",""));
                if (title[j].equals("结果代码")){
                    if(list.get(i).get("suStatus").equals("正常接通")){
                        cell.setCellValue("W03");
                    }else{
                        cell.setCellValue("W04");
                    }
                }
                if (title[j].equals("催收记录")) cell.setCellValue(list.get(i).get("suRemark").toString());
            }
        }

    }

    private void setContent(List<Map> list, String[] title, Sheet sheet) {
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
                if (title[j].equals("催收时间"))
                    cell.setCellValue(UtilFun.DateToString(UtilFun.StringToDate(list.get(i).get("createDate").toString(), UtilFun.YMD), UtilFun.YMD));
                if (title[j].equals("催收日期"))
                    cell.setCellValue(UtilFun.DateToString(UtilFun.StringToDate(list.get(i).get("createDate").toString(), UtilFun.YYYYMMDD), UtilFun.YYYYMMDD));
                if (title[j].equals("催收记录")) cell.setCellValue((String) list.get(i).get("suResult"));
                if (title[j].equals("催收拨打号码")) cell.setCellValue((String) list.get(i).get("suTarget"));
                if (title[j].equals("催收方式")) cell.setCellValue("电联");
                if (title[j].equals("案件状态")) cell.setCellValue((String) list.get(i).get("status"));
                if (title[j].equals("催收详细情况")) cell.setCellValue((String) list.get(i).get("suRmarks"));
                if (title[j].equals("催收员")) cell.setCellValue((String) list.get(i).get("sname"));
                if (title[j].equals("备注")) cell.setCellValue((String) list.get(i).get("rmarks"));
            }
        }
    }


}