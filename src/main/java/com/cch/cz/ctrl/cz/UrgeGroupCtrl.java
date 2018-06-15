package com.cch.cz.ctrl.cz;

import com.alibaba.fastjson.JSON;
import com.cch.cz.base.AjaxReturn;
import com.cch.cz.base.Table;
import com.cch.cz.entity.Staff;
import com.cch.cz.entity.UrgeGroup;
import com.cch.cz.service.UrgeGroupService;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/group")
public class UrgeGroupCtrl {
    @Resource
    private UrgeGroupService urgeGroupService;


    @GetMapping(value = "/list")
    public String list(Model model){
        model.addAttribute(urgeGroupService.findAll());
        return "/cz/group/list";
    }
    @PostMapping(value = "/list")
    @ResponseBody
    public Table list(){
        Table table= new Table();
        table.setData(urgeGroupService.findAll());
        table.setCount(urgeGroupService.count(new UrgeGroup()).intValue());
        return table;
    }

    @PostMapping(value = "/del")
    public AjaxReturn del(@RequestParam Long id) {
        try {
            urgeGroupService.delete(id);
            return new AjaxReturn(0,"删除成功");
        }catch (Exception e){
            return new AjaxReturn(1,"删除失败");
        }
    }

    @GetMapping(value = "/add")
    public String add(){
        return "/cz/group/add";
    }



    @PostMapping(value = "/ass")
    @ResponseBody
    public AjaxReturn ass(@RequestBody UrgeGroup data){
        try{
            urgeGroupService.save(data);
            return new AjaxReturn(0,"添加成功");
        }catch (Exception e){
            e.printStackTrace();
            return new AjaxReturn(1,"添加失败");
        }
    }

    @PostMapping(value = "/update")
    @ResponseBody
    public AjaxReturn update(@RequestParam String data){
        try {
            urgeGroupService.update(JSON.parseObject(data,UrgeGroup.class));
            return new AjaxReturn(0,"修改成功");
        }catch (Exception e){
            e.printStackTrace();
            return new AjaxReturn(1,"修改失败");
        }
    }

}
