package com.cch.cz.ctrl.cz;

import com.cch.cz.base.AjaxReturn;
import com.cch.cz.base.Table;
import com.cch.cz.entity.Staff;
import com.cch.cz.entity.WhiteList;
import com.cch.cz.service.WhiteListService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/3/18.
 *
 */
@Controller
@RequestMapping(value = "/white")
public class WhiteListCtrl {

    @Resource
    private WhiteListService whiteListService;
    @GetMapping(value = "/add")
    public String add(){
        return "/cz/whitelist/add";
    }

    @PostMapping(value = "/add")
    @ResponseBody
    public AjaxReturn add(@RequestBody WhiteList data){
        try {
            whiteListService.save(data);
        }catch (Exception e){
            return new AjaxReturn(0,"添加失败");
        }
        return new AjaxReturn(0,"添加成功");
    }

    @GetMapping(value = "/list")
    public String list(){
        return "/cz/whitelist/list";
    }

    @PostMapping(value = "/list")
    @ResponseBody
    public Table list(@RequestParam int page , @RequestParam int limit){
        PageHelper.startPage(page,limit);
        Table table = new Table();
        table.setData(whiteListService.findAll());
        table.setCount(whiteListService.count(new WhiteList()).intValue());
        return table;
    }

}
