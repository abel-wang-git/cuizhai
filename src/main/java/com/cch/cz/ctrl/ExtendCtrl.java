package com.cch.cz.ctrl;

import com.cch.cz.base.AjaxReturn;
import com.cch.cz.common.UtilFun;
import com.cch.cz.entity.Extend;
import com.cch.cz.service.ExtendService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/extends")
public class  ExtendCtrl {
    @Resource
    private ExtendService extendService;

    @PostMapping(value = "/bynum")
    @ResponseBody
    public AjaxReturn byNum(@RequestParam(defaultValue = "") String num){
        AjaxReturn ajaxReturn = new AjaxReturn();
        Extend extend = new Extend();
        extend.setContractNum(num);
        List<Extend> extendList= extendService.findByEntity(extend);
        if (UtilFun.isEmptyList(extendList)) {
            Map data = new HashMap();
            data.put("extend", extendList);
            ajaxReturn.setData(data);
            ajaxReturn.setCode(0);
        } else {
            ajaxReturn.setCode(1);
        }
        return ajaxReturn;
    }

}