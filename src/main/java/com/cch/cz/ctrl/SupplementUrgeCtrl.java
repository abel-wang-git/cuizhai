package com.cch.cz.ctrl;

import com.cch.cz.base.AjaxReturn;
import com.cch.cz.common.UtilFun;
import com.cch.cz.entity.Supplement;
import com.cch.cz.entity.SupplementUrge;
import com.cch.cz.entity.UrgeRecord;
import com.cch.cz.mapper.SupplementUrgeMapper;
import com.cch.cz.service.SupplementUrgeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@Controller
@RequestMapping(value = "/surge")
public class SupplementUrgeCtrl {
    @Resource
    private SupplementUrgeService supplementUrgeServicer;

    @PostMapping(value = "/add")
    @ResponseBody
    public AjaxReturn add(@RequestBody SupplementUrge urgeRecord) {
        urgeRecord.setCreateDate(UtilFun.DateToString(new Date(), UtilFun.YYYYMMDD));
        supplementUrgeServicer.save(urgeRecord);
        return new AjaxReturn(0, "添加成功");
    }


}