package com.cch.cz.ctrl.cz;

import com.alibaba.fastjson.JSON;
import com.cch.cz.base.AjaxReturn;
import com.cch.cz.base.Table;
import com.cch.cz.common.UtilFun;
import com.cch.cz.entity.Cases;
import com.cch.cz.entity.UrgeRecord;
import com.cch.cz.service.CasesService;
import com.cch.cz.service.CompanyService;
import com.cch.cz.service.UrgeRecordService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

import static com.github.pagehelper.page.PageMethod.startPage;

/**
 *崔记管理
 */
@Controller
@RequestMapping(value = "/urge")
public class UrgeRecordCtrl {

    @Resource
    private UrgeRecordService urgeRecordService;
    @Resource
    private CasesService casesService;
    @Resource
    private CompanyService companyService;

    @GetMapping(value = "/list")
    public  String list(Model model){

        return "/cz/urge/list";
    }
    @PostMapping(value = "/add")
    @ResponseBody
    public AjaxReturn add(@RequestBody UrgeRecord urgeRecord){
        urgeRecord.setCreateDate(UtilFun.DateToString(new Date(),UtilFun.YYYYMMDD));
        urgeRecordService.save(urgeRecord);
        return new AjaxReturn(0,"添加成功");
    }

    @PostMapping(value = "/bycase")
    @ResponseBody
    public Table listByCase(@RequestParam Long cases){
        List<UrgeRecord> list = urgeRecordService.findByCase(cases);
        Table table = new Table();
        table.setData(list);
        table.setCount(list.size());
        return table;
    }

    @PostMapping(value = "/list")
    @ResponseBody
    public Table list(@RequestParam int page,
                      @RequestParam int limit,
                      @RequestParam(defaultValue = "") String where){
        Map c=new HashMap();
        if(UtilFun.isEmptyString(where))c= JSON.parseObject(where,Map.class);
        PageHelper.startPage(page, limit);
        List<Map> list = urgeRecordService.manager(c);
        Page<Map> p=(Page<Map>)list;
        Table table = new Table();
        table.setData(list);
        table.setCount((int)p.getTotal());
        return table;
    }

    @GetMapping(value = "/manager")
    public String manager(Model model) {
        model.addAttribute("companys", companyService.findAll());
        return "/cz/urge/manager";
    }

    @PostMapping(value = "/exp",produces="text/plain;charset=UTF-8")
    @ResponseBody
    public void list(HttpServletResponse response,
                     HttpServletRequest request,
                     @RequestParam(defaultValue = "") String where,
                     @RequestParam String type) throws IOException {
        //数据
        Map c= new HashMap();
        if(UtilFun.isEmptyString(where))c=JSON.parseObject(where,Map.class);
        List<Map> list = urgeRecordService.manager(c);
        //表头
        String[] title = {"合同号", "客户姓名", "性别", "身份证", "总欠款", "客户手机", "催收时间", "催收记录", "催收员", "备注"};
        String[] title1 = {"公司名称", "委托日期", "客户姓名", "合同号", "联络时间", "联络类型", "联络号码", "联络人", "结果代码", "催收记录"};


        String realPath = request.getSession().getServletContext().getRealPath("");
        File realPathDirectory = new File(realPath);
        File downDir = new File(realPathDirectory.getParent() + File.separator + "urge" + File.separator);
        if (!downDir.exists()) {
            downDir.mkdirs();
        }
        File outputFile= new File(downDir + File.separator +  new Date().getTime()+"-"+"催记.xlsx");
        Workbook w = new XSSFWorkbook();
        Sheet sheet = w.createSheet("sheet1");
        //表頭信息
        if (c.size()>0&&c.get("type").equals("宜信")){
            title= title1;
            setyiContent(list, title, sheet);
        }else {
            setContent(list, title, sheet);
        }
        Row row = sheet.createRow(0);
        for(int i = 0;i<title.length;i++){
            Cell cell = row.createCell(i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(title[i]);
        }

        //内容
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode( (type.equals("1")?(UtilFun.DateToString(new Date(),UtilFun.YMD)+"-佰仟.xlsx"):(UtilFun.DateToString(new Date(),UtilFun.YMD)+"-有贝.xlsx")),"UTF-8"));
        response.flushBuffer();
        w.write(response.getOutputStream());
    }

    private void setyiContent(List<Map> list, String[] title, Sheet sheet) {
        for (int i = 0; i < list.size(); i++) {
            Row crow = sheet.createRow(i + 1);
            for (int j = 0; j < title.length; j++) {
                Cell cell = crow.createCell(j);
                cell.setCellType(CellType.STRING);
                if (title[j].equals("公司名称")) cell.setCellValue("宏鑫通");
                if (title[j].equals("委托日期")) cell.setCellValue((String) list.get(i).get("appointData"));
                if (title[j].equals("客户姓名") || title[j].equals("债务人姓名")) cell.setCellValue((String) list.get(i).get("cname"));
                if (title[j].equals("合同号")) cell.setCellValue((String) list.get(i).get("contractNum"));
                if (title[j].equals("联络时间")) cell.setCellValue(UtilFun.DateToString(UtilFun.StringToDate(list.get(i).get("createDate").toString(), UtilFun.YYYYMMDD), UtilFun.YYYYMMDD));
                if (title[j].equals("联络类型")) cell.setCellValue("01");
                if (title[j].equals("联络号码")) cell.setCellValue(list.get(i).get("target").toString().split("]")[1].replace("'",""));
                if (title[j].equals("联络人")) cell.setCellValue(list.get(i).get("target").toString().split("]")[0].replace("[",""));
                if (title[j].equals("结果代码")){
                    if(list.get(i).get("status").equals("正常接通")){
                        cell.setCellValue("W03");
                    }else{
                        cell.setCellValue("W04");
                    }
                }
                if (title[j].equals("催收记录")) cell.setCellValue(list.get(i).get("rmarks").toString());
            }
        }


    }

    private void setContent(List<Map> list, String[] title, Sheet sheet) {
        for (int i = 0; i <list.size() ; i++) {
            Row crow = sheet.createRow(i+1);
            for (int j = 0; j <title.length ; j++) {
                Cell cell = crow.createCell(j);
                cell.setCellType(CellType.STRING);
                if(title[j].equals("合同号"))cell.setCellValue((String) list.get(i).get("contractNum"));
                if(title[j].equals("客户姓名")|| title[j].equals("债务人姓名"))cell.setCellValue((String) list.get(i).get("cname"));
                if(title[j].equals("性别"))cell.setCellValue((String) list.get(i).get("sex"));
                if(title[j].equals("身份证"))cell.setCellValue((String) list.get(i).get("idCard"));
                if(title[j].equals("总欠款"))cell.setCellValue((String) list.get(i).get("sumArrears"));
                if(title[j].equals("客户手机"))cell.setCellValue((String) list.get(i).get("customerPhoneNumber"));
                if(title[j].equals("催收时间"))cell.setCellValue( UtilFun.DateToString(UtilFun.StringToDate(list.get(i).get("createDate").toString(),UtilFun.YMD),UtilFun.YMD));
                if(title[j].equals("催收日期"))cell.setCellValue( UtilFun.DateToString(UtilFun.StringToDate(list.get(i).get("createDate").toString(),UtilFun.YYYYMMDD),UtilFun.YYYYMMDD));
                if(title[j].equals("催收记录"))cell.setCellValue((String) list.get(i).get("result"));
                if(title[j].equals("催收拨打号码"))cell.setCellValue((String) list.get(i).get("target"));
                if(title[j].equals("催收方式"))cell.setCellValue("电联");
                if(title[j].equals("案件状态"))cell.setCellValue((String) list.get(i).get("status"));
                if(title[j].equals("催收详细情况"))cell.setCellValue((String) list.get(i).get("rmarks"));
                if(title[j].equals("催收员"))cell.setCellValue((String) list.get(i).get("sname"));
                if(title[j].equals("备注")) cell.setCellValue((String) list.get(i).get("rmarks"));
            }
        }
    }


}
