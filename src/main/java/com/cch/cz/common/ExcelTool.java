package com.cch.cz.common;

import com.cch.cz.entity.Cases;
import org.apache.commons.collections.IteratorUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/16.
 */
public class ExcelTool {

    public static List<Cases> read(File excel, int index) throws IOException, InvalidFormatException {
        // 创建对Excel工作簿文件的引用
        Workbook workbook = WorkbookFactory.create(new FileInputStream(excel));
        List<Cases> result = new ArrayList();
        String caseName =excel.getName().split("-")[1];
        String caseType =excel.getName().split("-")[2];
        //遍历sheet
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            //获取标题行
            List title = IteratorUtils.toList(sheet.getRow(0).cellIterator());
            int row_num = sheet.getLastRowNum();
            // 遍历内容行
            for (int j = index; j <= row_num; j++) {
                setAttr(result, sheet, title, j,caseName,caseType);
            }
        }
        /*UtilFun.prinrObject(result);*/
        return result;
    }

    private static void setAttr(List result, Sheet sheet, List title, int i, String caseName,String caseType) {
        Cases cases = new Cases();
        Row r = sheet.getRow(i);
        int cell_num = r.getLastCellNum();

        if(null==r||cell_num==-1)return;
        cases.setCaseName(caseName);
        cases.setType(caseType);
        cases.setStatus(Cases.NORMAL);
        //员工id设置为未分配
        cases.setCompanyId(-1L);
        for (int j = 0; j < title.size(); j++) {
            Cell curr = r.getCell(j);
            if (curr == null) continue;
            if (title.get(j).toString().equals("催收员")) {
                cases.setStaffId(getCellValue(curr));
            }
            if (title.get(j).toString().equals("合同号")) {
                cases.setContractNum(getCellValue(curr));
            }
            if (title.get(j).toString().equals("委外公司")||title.get(j).toString().equals("公司")) {
                cases.setAppointCompany(getCellValue(curr));
            }
            if (title.get(j).toString().equals("委派日期")||
                    title.get(j).toString().equals("委案时间")||
                    title.get(j).toString().equals("委案日期")) {
                cases.setAppointData(getCellValue(curr));
            }
            if (title.get(j).toString().equals("退案日期")||title.get(j).toString().equals("退案时间")) {
                cases.setStopAppoint(getCellValue(curr));
            }
            if (title.get(j).toString().equals("城市")) {
                cases.setCity(getCellValue(curr));
            }
            if (title.get(j).toString().equals("CUSTOMERID")) {
                cases.setCUSTOMERID(getCellValue(curr));
            }
            if (title.get(j).toString().equals("客户姓名")) {
                cases.setName(getCellValue(curr));
            }
            if (title.get(j).toString().equals("性别")) {
                cases.setSex(getCellValue(curr));
            }
            if (title.get(j).toString().equals("合同数量")) {
                cases.setContractsNum(getCellValue(curr));
            }
            if (title.get(j).toString().equals("身份证")) {
                cases.setIdCard(getCellValue(curr));
            }
            if (title.get(j).toString().equals("合同申请日")) {
                cases.setContractApplyDate(getCellValue(curr));
            }
            if (title.get(j).toString().equals("贷款类型")) {
                cases.setLoanType(getCellValue(curr));
            }
            if (title.get(j).toString().equals("贷款本金")) {
                cases.setLoanPrincipal(getCellValue(curr));
            }
            if (title.get(j).toString().equals("期款")) {
                cases.setInstallmentMoney(getCellValue(curr));
            }
            if (title.get(j).toString().equals("期数")) {
                cases.setInstallmentNum(getCellValue(curr));
            }
            if (title.get(j).toString().equals("已还款金额")) {
                cases.setRepaymentMoney(getCellValue(curr));
            }
            if (title.get(j).toString().equals("最后还款日")) {
                cases.setDeadline(getCellValue(curr));
            }
            if (title.get(j).toString().equals("逾期天数")) {
                cases.setOverrangingDay(getCellValue(curr));
            }
            if (title.get(j).toString().equals("取消分期时间")) {
                cases.setCancelInstalments(getCellValue(curr));
            }
            if (title.get(j).toString().equals("未付期款")) {
                cases.setNnpaidInstallment(getCellValue(curr));
            }
            if (title.get(j).toString().equals("未付滞纳金")) {
                cases.setLateFee(getCellValue(curr));
            }
            if (title.get(j).toString().equals("欠款金额")) {
                cases.setArrears(getCellValue(curr));
            }
            if (title.get(j).toString().equals("委外催收费")||title.get(j).toString().equals("催收费")) {
                cases.setServiceCharge(getCellValue(curr));
            }
            if (title.get(j).toString().equals("总欠款")||title.get(j).toString().equals("总欠款金额")) {
                cases.setSumArrears(getCellValue(curr));
            }
            if (title.get(j).toString().equals("还款账号")) {
                cases.setRepaymentAccount(getCellValue(curr));
            }
            if (title.get(j).toString().equals("户名")) {
                cases.setAccountMaster(getCellValue(curr));
            }
            if (title.get(j).toString().equals("开户行")) {
                cases.setBank(getCellValue(curr));
            }
            if (title.get(j).toString().equals("商品")) {
                cases.setCommodity(getCellValue(curr));
            }
            if (title.get(j).toString().equals("品牌")) {
                cases.setBrand(getCellValue(curr));
            }
            if (title.get(j).toString().equals("POS点")) {
                cases.setPosPlace(getCellValue(curr));
            }
            if (title.get(j).toString().equals("客户手机")) {
                cases.setCustomerPhoneNumber(getCellValue(curr));
            }
            if (title.get(j).toString().equals("客户户籍地址")) {
                cases.setCustomerAddress(getCellValue(curr));
            }
            if (title.get(j).toString().equals("客户办公电话")) {
                cases.setCustomerOfficePhone(getCellValue(curr));
            }
            if (title.get(j).toString().equals("客户公司名称")) {
                cases.setCustomerCompany(getCellValue(curr));
            }
            if (title.get(j).toString().equals("客户公司地址")) {
                cases.setCustomerCompanyAddress(getCellValue(curr));
            }
            if (title.get(j).toString().equals("客户公司部门")) {
                cases.setCustomerDepartment(getCellValue(curr));
            }
            if (title.get(j).toString().equals("客户住宅电话")) {
                cases.setCustomerResidencePhone(getCellValue(curr));
            }
            if (title.get(j).toString().equals("客户住宅地址")) {
                cases.setCustomerResidenceAddress(getCellValue(curr));
            }
            if (title.get(j).toString().equals("客户配偶姓名")) {
                cases.setCustomerSpouse(getCellValue(curr));
            }
            if (title.get(j).toString().equals("客户配偶联系电话")) {
                cases.setCustomerSpousePhone(getCellValue(curr));
            }
            if (title.get(j).toString().equals("客户亲戚姓名")) {
                cases.setCustomerRelativeName(getCellValue(curr));
            }
            if (title.get(j).toString().equals("客户与亲戚关系")) {
                cases.setCustomerRelationship(getCellValue(curr));
            }
            if (title.get(j).toString().equals("客户亲戚联系电话")) {
                cases.setCustomerRelativePhone(getCellValue(curr));
            }
            if (title.get(j).toString().equals("其他联系人姓名")) {
                cases.setCustomerRelativeOther(getCellValue(curr));
            }
            if (title.get(j).toString().equals("其他联系人关系")) {
                cases.setCustomerRelaOther(getCellValue(curr));
            }
            if (title.get(j).toString().equals("其他联系人电话")) {
                cases.setCustomerOtherPhone(getCellValue(curr));
            }
            if (title.get(j).toString().equals("客户邮箱")) {
                cases.setCustomerMail(getCellValue(curr));
            }
            if (title.get(j).toString().equals("代扣开户行")) {
                cases.setWithholding(getCellValue(curr));
            }
            if (title.get(j).toString().equals("代扣账号")) {
                cases.setWithholdingAccount(getCellValue(curr));
            }

        }
        result.add(cases);
    }

    private static String getCellValue(Cell cell){
        switch (cell.getCellTypeEnum()){
            case NUMERIC:
                if(HSSFDateUtil.isCellDateFormatted(cell)){
                    return UtilFun.DateToString(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()),UtilFun.YYYYMMDD);
                }else {
                    cell.setCellType(CellType.STRING);
                    return cell.getStringCellValue();
                }
            default:
                return cell.getStringCellValue();
        }
    }


    public static void wirte() throws IOException, InvalidFormatException {

        File outputFile = new File("C:\\Users\\Administrator\\Pictures\\1.xlsx");
        FileOutputStream fOut = new FileOutputStream(outputFile);
        Workbook w = new XSSFWorkbook();
        Sheet sheet = w.createSheet("sheetone");
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("姓名");

        w.write(fOut);

    }


}
