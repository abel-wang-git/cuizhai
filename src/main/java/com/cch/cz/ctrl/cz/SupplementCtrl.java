package com.cch.cz.ctrl.cz;

import com.cch.cz.base.AjaxReturn;
import com.cch.cz.common.UtilFun;
import com.cch.cz.entity.Supplement;
import com.cch.cz.service.SupplementService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping(value = "/bynum")
    @ResponseBody
    public AjaxReturn add(@RequestParam(defaultValue = "") String num) {
        AjaxReturn ajaxReturn = new AjaxReturn();
        Supplement where = new Supplement();
        where.setContractNum(num);
        List<Supplement> supplements = supplementService.findByEntity(where);
        if (UtilFun.isEmptyList(supplements)) {
            Map data = new HashMap();
            data.put("supplement", supplements.get(0));
            ajaxReturn.setData(data);
            ajaxReturn.setCode(0);
        } else {
            ajaxReturn.setCode(1);
        }
        return ajaxReturn;
    }
}
