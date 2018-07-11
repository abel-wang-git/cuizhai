package com.cch.cz.ctrl.cz;

import com.alibaba.fastjson.JSON;
import com.cch.cz.base.AjaxReturn;
import com.cch.cz.base.Table;
import com.cch.cz.entity.Cases;
import com.cch.cz.entity.Supplement;
import com.cch.cz.entity.UrgeGroup;
import com.cch.cz.service.SupplementService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/supplement")
public class SupplementCtrl {
    @Resource
    private SupplementService supplementService;

    @PostMapping(value = "/supplement")
    @ResponseBody
    public AjaxReturn expSupplement(@RequestBody List<Supplement> data) {
        try {
            supplementService.expSupplement(data);
        } catch (Exception e) {
            return new AjaxReturn(1, "导入失败");
        }

        return new AjaxReturn(0, "导入成功");
    }

    @PostMapping(value = "/list")
    @ResponseBody
    public Table list(){
        Table table= new Table();
        table.setData(supplementService.findAll());
        table.setCount(supplementService.count(new Supplement()).intValue());
        return table;
    }
}
