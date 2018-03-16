package com.cch.cz.base.ctrl;

import com.cch.cz.base.AjaxReturn;
import com.cch.cz.common.ImageUploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/16.
 * 文件上传
 */
@Controller
public class Upload {

    @RequestMapping(value = "/upload")
    @ResponseBody
    public AjaxReturn uploadImage (HttpServletRequest request, HttpServletResponse response) {
        AjaxReturn ajaxRetrun = new AjaxReturn();
        Map data = new HashMap();
        try {
            String name = ImageUploadUtil.upload(request,"image/");
            data.put("path","http://localhost:8080/image/"+name);
        } catch (IOException e) {
            ajaxRetrun.setMessage("上传失败");
            e.printStackTrace ();
        }
        ajaxRetrun.setData(data);
        ajaxRetrun.setMessage("上传成功");
        return ajaxRetrun;
    }
}
