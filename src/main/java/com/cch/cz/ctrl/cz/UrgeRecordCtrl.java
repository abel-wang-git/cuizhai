package com.cch.cz.ctrl.cz;

import com.alibaba.fastjson.JSON;
import com.cch.cz.base.AjaxReturn;
import com.cch.cz.base.Table;
import com.cch.cz.common.UtilFun;
import com.cch.cz.entity.Cases;
import com.cch.cz.entity.UrgeRecord;
import com.cch.cz.service.CasesService;
import com.cch.cz.service.CityService;
import com.cch.cz.service.UrgeRecordService;
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
    private CityService cityService;
    @GetMapping(value = "/list")
    public  String list(Model model){
        model.addAttribute("province",cityService.province());
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
        PageHelper.startPage(page, limit);
        Map c=new HashMap();
        if(UtilFun.isEmptyString(where))c= JSON.parseObject(where,Map.class);
        List<Map> list = urgeRecordService.manager(c);
        Table table = new Table();
        table.setData(list);
        table.setCount(list.size());
        return table;
    }

    @GetMapping(value = "/manager")
    public String manager(){
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
        List<String> title = new ArrayList<>();
        //表头
//        "客户办公电话","客户配偶姓名","客户配偶联系电话","客户亲戚姓名","客户与亲戚关系","客户亲戚联系电话","其他联系人姓名","其他联系人关系","其他联系人电话"
        String[] title1 = {"合同号","客户姓名","性别","身份证","总欠款","客户手机","催收时间","催收记录"};
        String[] title2 = {"提线流水号","债务人姓名","催收拨打号码","催收日期","催收时间","催收方式","催收员","案件状态","催收详细情况"};
        if (type.equals("1")){
            title= Arrays.asList(title1);
        }else {
            title=Arrays.asList(title2);
        }
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
        Row row = sheet.createRow(0);
        for(int i = 0;i<title.size();i++){
            Cell cell = row.createCell(i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(title.get(i));
        }
        //内容
        for (int i = 0; i <list.size() ; i++) {
            Row crow = sheet.createRow(i+1);
            for (int j = 0; j <title.size() ; j++) {
                Cell cell = crow.createCell(j);
                cell.setCellType(CellType.STRING);
                if(title.get(j).equals("合同号"))cell.setCellValue((String) list.get(i).get("contractNum"));
                if(title.get(j).equals("客户姓名")|| title.get(j).equals("债务人姓名"))cell.setCellValue((String) list.get(i).get("cname"));
                if(title.get(j).equals("性别"))cell.setCellValue((String) list.get(i).get("sex"));
                if(title.get(j).equals("身份证"))cell.setCellValue((String) list.get(i).get("idCard"));
                if(title.get(j).equals("总欠款"))cell.setCellValue((String) list.get(i).get("sumArrears"));
                if(title.get(j).equals("客户手机"))cell.setCellValue((String) list.get(i).get("customerPhoneNumber"));
//                if(title.get(j).equals("客户办公电话"))cell.setCellValue((String) list.get(i).get("customerOfficePhone"));
//                if(title.get(j).equals("客户配偶姓名"))cell.setCellValue((String) list.get(i).get("customerSpouse"));
//                if(title.get(j).equals("客户配偶联系电话"))cell.setCellValue((String) list.get(i).get("customerSpousePhone"));
//                if(title.get(j).equals("客户亲戚姓名"))cell.setCellValue((String) list.get(i).get("customerRelativeName"));
//                if(title.get(j).equals("客户与亲戚关系"))cell.setCellValue((String) list.get(i).get("customerRelationship"));
//                if(title.get(j).equals("客户亲戚联系电话"))cell.setCellValue((String) list.get(i).get("customerRelativePhone"));
//                if(title.get(j).equals("其他联系人姓名"))cell.setCellValue((String) list.get(i).get("customerRelativeOther"));
//                if(title.get(j).equals("其他联系人关系"))cell.setCellValue((String) list.get(i).get("customerRelaOther"));
//                if(title.get(j).equals("其他联系人电话"))cell.setCellValue((String) list.get(i).get("customerOtherPhone"));
                if(title.get(j).equals("催收时间"))cell.setCellValue( UtilFun.DateToString((Date)list.get(i).get("createDate"),UtilFun.YMD));
                if(title.get(j).equals("催收日期"))cell.setCellValue( UtilFun.DateToString((Date)list.get(i).get("createDate"),UtilFun.YYYYMMDD));
                if(title.get(j).equals("催收记录"))cell.setCellValue((String) list.get(i).get("result"));
                if(title.get(j).equals("催收拨打号码"))cell.setCellValue((String) list.get(i).get("target"));
                if(title.get(j).equals("催收方式"))cell.setCellValue("电联");
                if(title.get(j).equals("案件状态"))cell.setCellValue((String) list.get(i).get("status"));
                if(title.get(j).equals("催收详细情况"))cell.setCellValue((String) list.get(i).get("rmarks"));
                if(title.get(j).equals("催收员"))cell.setCellValue((String) list.get(i).get("sname"));
//                if(title.get(j).equals("提线流水号"))cell.setCellValue((String) list.get(i).get("result"));
            }
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode( (type.equals("1")?(UtilFun.DateToString(new Date(),UtilFun.YMD)+"-佰仟.xlsx"):(UtilFun.DateToString(new Date(),UtilFun.YMD)+"-有贝.xlsx")),"UTF-8"));
        response.flushBuffer();
        w.write(response.getOutputStream());
    }




}
