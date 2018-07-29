package com.cch.cz.base.ctrl;

import com.cch.cz.authority.entity.User;
import com.cch.cz.base.AjaxReturn;
import com.cch.cz.common.ExcelTool;
import com.cch.cz.common.Execl;
import com.cch.cz.common.UploadUtil;
import com.cch.cz.common.UtilFun;
import com.cch.cz.entity.Cases;
import com.cch.cz.entity.Staff;
import com.cch.cz.entity.Supplement;
import com.cch.cz.entity.UploadLog;
import com.cch.cz.exception.UploadException;
import com.cch.cz.service.CasesService;
import com.cch.cz.service.StaffService;
import com.cch.cz.service.UploadLogService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

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
    @Resource
    private UploadLogService uploadLogService;

    @RequestMapping(value = "/upload")
    @ResponseBody
    public AjaxReturn uploadImage (HttpServletRequest request) throws UploadException {
        List<String> title1 = new ArrayList<>();
        title1.add("合同号");
        title1.add("委案日期");
        title1.add("退案日期");
        title1.add("客户姓名");
        title1.add("性别");
        title1.add("身份证");
        title1.add("贷款类型");
        title1.add("贷款本金");
        title1.add("期款");
        title1.add("期数");
        title1.add("已还款金额");
        title1.add("最后还款日");
        title1.add("逾期天数");
        title1.add("未付期款");
        title1.add("委外催收费");
        title1.add("总欠款金额");
        title1.add("还款账号");
        title1.add("户名");
        title1.add("开户行");
        title1.add("客户户籍地址");
        title1.add("客户手机");
        AjaxReturn ajaxRetrun = new AjaxReturn();
        Map data = new HashMap();
        ajaxRetrun.setCode(1);
        String md5=null;
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
                for (int j =0 ;j<sheet.getRow(0).getLastCellNum();j++) {
                     Cell a=sheet.getRow(0).getCell(j);
                    if(null==sheet.getRow(0).getCell(j)||ExcelTool.getCellValue(sheet.getRow(0).getCell(j)).trim()==null){
                        throw new UploadException("表头存在空值");
                    } else {
                        Iterator<String> iterator = title1.iterator();
                        while (iterator.hasNext()) {
                            String t = iterator.next();
                            if (ExcelTool.getCellValue(sheet.getRow(0).getCell(j)).trim().equals(t))
                                iterator.remove();
                        }
                    }
                }
            }
            String quet = "";
            if (title1.size() > 0) {
                for (String t : title1) {
                    quet += (t + ",");
                }
                throw new UploadException("缺少" + quet + "信息");
            }
            md5= DigestUtils.md5Hex(IOUtils.toByteArray(new FileInputStream(name)));
            if(null!=uploadLogService.findOne(md5))throw new UploadException("上传过相同的案件");
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
            return ajaxRetrun;
        } catch (InvalidFormatException e) {
            ajaxRetrun.setMessage("上传失败");
            e.printStackTrace();
            return ajaxRetrun;
        }catch (UploadException u){
            ajaxRetrun.setMessage(u.getMessage());
            u.printStackTrace();
            return ajaxRetrun;
        } catch (Exception e){
            ajaxRetrun.setMessage("上传失败");
            e.printStackTrace();
            return ajaxRetrun;
        }
        UploadLog log = new UploadLog();
        log.setMd5(md5);
        log.setDate(new Date());
        User user= (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        log.setUser(user.getUserName());
        uploadLogService.save(log);
        ajaxRetrun.setData(data);
        ajaxRetrun.setCode(0);
        ajaxRetrun.setMessage("上传成功");
        return ajaxRetrun;
    }

    private  void setAttr(List result, Sheet sheet, List title,
                                int i, String caseName,String caseType) throws UploadException {
        Cases cases = new Cases();
        Row r = sheet.getRow(i);
        if(null==r)return;
        int cell_num = r.getLastCellNum();
        if(cell_num==-1)return;
        cases.setCaseName(caseName);
        cases.setType(caseType);
        cases.setStatus(Cases.NORMAL);
        cases.setCompanyId(-1L);
        for (int j = 0; j < title.size(); j++) {
            Cell curr = r.getCell(j);
            if (curr == null) continue;
            if (title.get(j).toString().trim().equals("催收员")) {
                Staff staff=staffService.findOne(ExcelTool.getCellValue(curr).trim());
                if(null==staff){
                    throw new UploadException("不存在催收员"+ExcelTool.getCellValue(curr).trim());
                }
                if(UtilFun.isEmptyString(ExcelTool.getCellValue(curr))){
                    cases.setStaffId(ExcelTool.getCellValue(curr));
                    cases.setCompanyId(staff.getCompanyId());
                }
            }
            if (title.get(j).toString().trim().equals("身份证")) {
                //相同身份证号码分配到同一催收员
                cases.setIdCard(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("合同号")) {
                //相同合同号分配到同一催收员
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
            if (title.get(j).toString().trim().equals("客户办公电话")) {
                cases.setCustomerOfficePhone(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("客户公司名称")) {
                cases.setCustomerCompany(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("客户公司地址")) {
                cases.setCustomerCompanyAddress(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("客户公司部门")) {
                cases.setCustomerDepartment(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("客户住宅电话")) {
                cases.setCustomerResidencePhone(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("客户住宅地址")) {
                cases.setCustomerResidenceAddress(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("客户配偶姓名")) {
                cases.setCustomerSpouse(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("客户配偶联系电话")) {
                cases.setCustomerSpousePhone(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("客户亲戚姓名")) {
                cases.setCustomerRelativeName(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("客户与亲戚关系")) {
                cases.setCustomerRelationship(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("客户亲戚联系电话")) {
                cases.setCustomerRelativePhone(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("其他联系人姓名")) {
                cases.setCustomerRelativeOther(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("其他联系人关系")) {
                cases.setCustomerRelaOther(ExcelTool.getCellValue(curr));
            }
            if (title.get(j).toString().trim().equals("其他联系人电话")) {
                cases.setCustomerOtherPhone(ExcelTool.getCellValue(curr));
            }
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
