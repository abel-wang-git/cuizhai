package com.cch.cz.base.ctrl;

import com.cch.cz.base.AjaxReturn;
import com.cch.cz.common.ExcelTool;
import com.cch.cz.common.Execl;
import com.cch.cz.common.UploadUtil;
import com.cch.cz.common.UtilFun;
import com.cch.cz.entity.Cases;
import com.cch.cz.entity.Staff;
import com.cch.cz.service.CasesService;
import com.cch.cz.service.StaffService;
import org.apache.commons.collections.IteratorUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/16.
 * 文件上传
 */
@Controller
public class Upload {

    @Resource
    private StaffService staffService;
    @Resource
    private CasesService casesService;

    @RequestMapping(value = "/upload")
    @ResponseBody
    public AjaxReturn uploadImage (HttpServletRequest request) {
        AjaxReturn ajaxRetrun = new AjaxReturn();
        Map data = new HashMap();
        ajaxRetrun.setCode(1);
        try {
            File name = UploadUtil.upload(request,"archcase/");
            Workbook workbook = WorkbookFactory.create(new FileInputStream(name));
            List<Cases> result = new ArrayList();
            String filename=name.getName().substring(0,name.getName().lastIndexOf("."));
            //1.案件名称，2.案件类型
            String casename[] =filename.split("-");
            List<Sheet> sheets = new ArrayList<>();
            if (casename.length<3){
                ajaxRetrun.setMessage("文件名格式错误");
                return ajaxRetrun;
            }

            //遍历sheet
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                sheets.add(sheet);

                List title = IteratorUtils.toList(sheet.getRow(0).cellIterator());
                for (Object t:title) {
                    if(!UtilFun.isEmptyString(t.toString()))ajaxRetrun.setMessage("标题栏存在空值");
                }
            }
            if(UtilFun.isEmptyString(ajaxRetrun.getMessage()))
                return ajaxRetrun;

            for (int i = 0; i <sheets.size() ; i++) {
                //获取标题行
                int row_num = sheets.get(i).getLastRowNum();
                List title = IteratorUtils.toList(sheets.get(i).getRow(0).cellIterator());
                for (int j = 1; j <= row_num; j++) {
                    setAttr(result, sheets.get(i), title, j,casename[1],casename[2]);
                }
            }

            data.put("path","http://localhost:8080/image/"+name);
            data.put("data",result);
        } catch (IOException e) {
            ajaxRetrun.setMessage("上传失败");
            e.printStackTrace ();
        } catch (InvalidFormatException e) {
            ajaxRetrun.setMessage("上传失败");
            e.printStackTrace();
        }
        ajaxRetrun.setData(data);
        ajaxRetrun.setCode(0);
        ajaxRetrun.setMessage("上传成功");
        return ajaxRetrun;
    }

    private  void setAttr(List result, Sheet sheet, List title,
                                int i, String caseName,String caseType) {
        Cases cases = new Cases();
        Row r = sheet.getRow(i);
        if(null==r)return;
        int cell_num = r.getLastCellNum();
        if(cell_num==-1)return;
        cases.setCaseName(caseName);
        cases.setType(caseType);
        cases.setStatus(Cases.NORMAL);
        //员工id设置为未分配
        cases.setCompanyId(-1L);
        for (int j = 0; j < title.size(); j++) {
            Cell curr = r.getCell(j);
            if (curr == null) continue;
            if (title.get(j).toString().trim().equals("催收员")) {
                Staff staff=staffService.findOne(ExcelTool.getCellValue(curr).trim());
                if(UtilFun.isEmptyString(ExcelTool.getCellValue(curr))){
                    cases.setStaffId(ExcelTool.getCellValue(curr));
                    cases.setCompanyId(staff.getCompanyId());
                }
            }
            if (title.get(j).toString().trim().equals("合同号")) {
                cases.setContractNum(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("委外公司")||title.get(j).toString().trim().equals("公司")) {
                cases.setAppointCompany(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("委派日期")||
                    title.get(j).toString().trim().equals("委案时间")||
                    title.get(j).toString().trim().equals("委案日期")) {
                cases.setAppointData(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("退案日期")||title.get(j).toString().trim().equals("退案时间")) {
                cases.setStopAppoint(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("城市")) {
                cases.setCity(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("CUSTOMERID")) {
                cases.setCUSTOMERID(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("客户姓名")) {
                cases.setName(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("性别")) {
                cases.setSex(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("合同数量")) {
                cases.setContractsNum(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("身份证")) {
                cases.setIdCard(ExcelTool.getCellValue(curr));
                Staff staff = casesService.findStaffByIdcard(ExcelTool.getCellValue(curr));
                if (UtilFun.isEmptyString(cases.getStaffId())) {
                    cases.setCompanyId(staff.getCompanyId());
                    cases.setStaffId(staff.getLoginName());
                }
            }
            if (title.get(j).toString().trim().equals("合同申请日")) {
                cases.setContractApplyDate(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("贷款类型")) {
                cases.setLoanType(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("贷款本金")) {
                cases.setLoanPrincipal(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("期款")) {
                cases.setInstallmentMoney(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("期数")) {
                cases.setInstallmentNum(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("已还款金额")) {
                cases.setRepaymentMoney(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("最后还款日")) {
                cases.setDeadline(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("逾期天数")) {
                cases.setOverrangingDay(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("取消分期时间")) {
                cases.setCancelInstalments(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("未付期款")) {
                cases.setNnpaidInstallment(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("未付滞纳金")) {
                cases.setLateFee(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("欠款金额")) {
                cases.setArrears(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("委外催收费")||title.get(j).toString().equals("催收费")) {
                cases.setServiceCharge(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("总欠款")||title.get(j).toString().equals("总欠款金额")) {
                cases.setSumArrears(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("还款账号")) {
                cases.setRepaymentAccount(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("户名")) {
                cases.setAccountMaster(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("开户行")) {
                cases.setBank(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("商品")) {
                cases.setCommodity(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("品牌")) {
                cases.setBrand(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("POS点")) {
                cases.setPosPlace(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("客户手机")) {
                cases.setCustomerPhoneNumber(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("客户户籍地址")) {
                cases.setCustomerAddress(ExcelTool.getCellValue(curr));
            }
//            if (title.get(j).toString().trim().equals("客户办公电话")) {
//                cases.setCustomerOfficePhone(ExcelTool.getCellValue(curr));
//            }
//            if (title.get(j).toString().trim().equals("客户公司名称")) {
//                cases.setCustomerCompany(ExcelTool.getCellValue(curr));
//            }
//            if (title.get(j).toString().trim().equals("客户公司地址")) {
//                cases.setCustomerCompanyAddress(ExcelTool.getCellValue(curr));
//            }
//            if (title.get(j).toString().trim().equals("客户公司部门")) {
//                cases.setCustomerDepartment(ExcelTool.getCellValue(curr));
//            }
//            if (title.get(j).toString().trim().equals("客户住宅电话")) {
//                cases.setCustomerResidencePhone(ExcelTool.getCellValue(curr));
//            }
//            if (title.get(j).toString().trim().equals("客户住宅地址")) {
//                cases.setCustomerResidenceAddress(ExcelTool.getCellValue(curr));
//            }
//            if (title.get(j).toString().trim().equals("客户配偶姓名")) {
//                cases.setCustomerSpouse(ExcelTool.getCellValue(curr));
//            }
//            if (title.get(j).toString().trim().equals("客户配偶联系电话")) {
//                cases.setCustomerSpousePhone(ExcelTool.getCellValue(curr));
//            }
//            if (title.get(j).toString().trim().equals("客户亲戚姓名")) {
//                cases.setCustomerRelativeName(ExcelTool.getCellValue(curr));
//            }
//            if (title.get(j).toString().trim().equals("客户与亲戚关系")) {
//                cases.setCustomerRelationship(ExcelTool.getCellValue(curr));
//            }
//            if (title.get(j).toString().trim().equals("客户亲戚联系电话")) {
//                cases.setCustomerRelativePhone(ExcelTool.getCellValue(curr));
//            }
//            if (title.get(j).toString().trim().equals("其他联系人姓名")) {
//                cases.setCustomerRelativeOther(ExcelTool.getCellValue(curr));
//            }
//            if (title.get(j).toString().trim().equals("其他联系人关系")) {
//                cases.setCustomerRelaOther(ExcelTool.getCellValue(curr));
//            }
//            if (title.get(j).toString().trim().equals("其他联系人电话")) {
//                cases.setCustomerOtherPhone(ExcelTool.getCellValue(curr));
//            }
            if (title.get(j).toString().trim().equals("客户邮箱")) {
                cases.setCustomerMail(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("代扣开户行")) {
                cases.setWithholding(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("代扣账号")) {
                cases.setWithholdingAccount(ExcelTool.getCellValue(curr));
            }

        }
        result.add(cases);
    }
}
