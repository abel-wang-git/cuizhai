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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
                      @RequestParam String where){
        PageHelper.startPage(page, limit);
        Map c= JSON.parseObject(where,Map.class);
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

    @PostMapping(value = "/exp")
    @ResponseBody
    public ResponseEntity<byte[]> list(@RequestParam String where, @RequestParam String type) throws IOException {
        Map c= JSON.parseObject(where,Map.class);
        List<Map> list = urgeRecordService.manager(c);
        File outputFile = new File("C:\\Users\\Administrator\\Pictures\\1.xlsx");
        FileOutputStream fOut = new FileOutputStream(outputFile);
        Workbook w = new XSSFWorkbook();
        Sheet sheet = w.createSheet("sheetone");
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("姓名");

        w.write(fOut);
        HttpHeaders headers = new HttpHeaders();
        //下载显示的文件名，解决中文名称乱码问题
        String downloadFielName = new String(outputFile.getName().getBytes("UTF-8"),"iso-8859-1");
        //通知浏览器以attachment（下载方式）打开图片
        headers.setContentDispositionFormData("attachment", downloadFielName);
        //application/octet-stream ： 二进制流数据（最常见的文件下载）。
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(outputFile),
                headers, HttpStatus.CREATED);
    }




}
