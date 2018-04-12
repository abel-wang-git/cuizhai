package com.cch.cz.ctrl.cz;

import com.alibaba.fastjson.JSON;
import com.cch.cz.base.AjaxReturn;
import com.cch.cz.base.Table;
import com.cch.cz.common.UtilFun;
import com.cch.cz.entity.Cases;
import com.cch.cz.entity.UrgeRecord;
import com.cch.cz.service.CasesService;
import com.cch.cz.service.UrgeRecordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
    @GetMapping(value = "/list")
    public  String list(){
        return "/cz/urge/list";
    }

    @GetMapping(value = "/add")
    public  String add(Model model,@RequestParam("cases")String casesId){
        Cases cases = casesService.findOne(Long.parseLong(casesId));
        model.addAttribute(cases);

        return "/cz/urge/add";
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


}
