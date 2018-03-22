package com.cch.cz.common;

import com.cch.cz.entity.Cases;
import org.apache.commons.collections.IteratorUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2018/3/16.
 */
public class ExcelTool {

    public static void main(String[] args) {
        try {
            read(null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    public static List<Cases> read(File excel) throws IOException, InvalidFormatException {

        // 创建对Excel工作簿文件的引用
        Workbook workbook = WorkbookFactory.create(new FileInputStream(excel));
        List<Cases> result = new ArrayList();
        // 创建对工作表的引用。
        Sheet sheet = workbook.getSheetAt(0);
        workbook.getActiveSheetIndex();
        workbook.getAllNames();
        workbook.getNumberOfSheets();
        // 便利所有单元格，读取单元格
        List title= IteratorUtils.toList(sheet.getRow(0).cellIterator());

        int row_num = sheet.getLastRowNum();
        for (int i = 0; i <=row_num; i++) {
            setAttr(result, sheet, title, i);
        }
        /*UtilFun.prinrObject(result);*/
        return result;
    }

    private static void setAttr(List result, Sheet sheet, List title, int i) {
        Cases cases= new Cases();
        Row r = sheet.getRow(i);
        int cell_num = r.getLastCellNum();
        for (int j = 0; j < cell_num; j++) {
           Object o =title.get(j).toString();
            if(title.get(j).toString().equals("合同号")){
                cases.setContractNum(r.getCell(j).getStringCellValue());
            }
            if(title.get(j).toString().equals("委外公司")){
                cases.setAppointCompany(r.getCell(j).getStringCellValue());
            }
            if(title.get(j).toString().equals("委派日期")){
                cases.setAppointData(r.getCell(j).getStringCellValue());
            }
            if(title.get(j).toString().equals("退案日期")){
                cases.setStopAppoint(r.getCell(j).getStringCellValue());
            }
            if(title.get(j).toString().equals("城市")){
                cases.setCity(r.getCell(j).getStringCellValue());
            }
            if(title.get(j).toString().equals("地区")){
                cases.setRegion(r.getCell(j).getStringCellValue());
            }
            if(title.get(j).toString().equals("CUSTOMERID")){
                cases.setCUSTOMERID(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("客户姓名")){
                cases.setName(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("性别")){
                cases.setSex(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("合同数量")){
                cases.setContractsNum(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("身份证")){
                cases.setIdCard(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("合同申请日")){
                cases.setContractApplyDate(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("贷款类型")){
                cases.setLoanType(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("贷款本金")){
                cases.setLoanPrincipal(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("期款")){
                cases.setInstallmentMoney(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("期数")){
                cases.setInstallmentNum(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("已还款金额")){
                cases.setRepaymentMoney(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("最后还款日")){
                cases.setDeadline(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("逾期天数")){
                cases.setOverrangingDay(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("取消分期时间")){
                cases.setCancelInstalments(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("未付期款")){
                cases.setNnpaidInstallment(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("未付滞纳金")){
                cases.setLateFee(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("欠款金额")){
                cases.setArrears(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("委外催收费")){
                cases.setServiceCharge(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("总欠款")){
                cases.setSumArrears(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("还款账号")){
                cases.setRepaymentAccount(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("户名")){
                cases.setAccountMaster(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("开户行")){
                cases.setBank(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("商品")){
                cases.setCommodity(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("品牌")){
                cases.setBrand(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("POS点")){
                cases.setPosPlace(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("客户手机")){
                cases.setCustomerPhoneNumber(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("客户户籍地址")){
                cases.setCustomerAddress(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("客户办公电话")){
                cases.setCustomerOfficePhone(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("客户公司名称")){
                cases.setCustomerCompany(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("客户公司地址")){
                cases.setCustomerCompanyAddress(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("客户公司部门")){
                cases.setCustomerDepartment(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("客户住宅电话")){
                cases.setCustomerResidencePhone(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("客户住宅地址")){
                cases.setCustomerResidenceAddress(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("客户配偶姓名")){
                cases.setCustomerSpouse(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("客户配偶联系电话")){
                cases.setCustomerSpousePhone(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("客户亲戚姓名")){
                cases.setCustomerRelativeName(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("客户与亲戚关系")){
                cases.setCustomerRelationship(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("客户亲戚联系电话")){
                cases.setCustomerRelativePhone(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("其他联系人姓名")){
                cases.setCustomerRelativeOther(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("其他联系人关系")){
                cases.setCustomerRelaOther(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("其他联系人电话")){
                cases.setCustomerOtherPhone(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("客户邮箱")){
                cases.setCustomerMail(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("代扣开户行")){
                cases.setWithholding(r.getCell(j).getStringCellValue());
            }if(title.get(j).toString().equals("代扣账号")){
                cases.setWithholdingAccount(r.getCell(j).getStringCellValue());
            }

        }
        result.add(cases);
    }


    public static void wirte() throws IOException, InvalidFormatException {

        File outputFile = new File("C:\\Users\\Administrator\\Pictures\\1.xlsx");
        FileOutputStream fOut = new FileOutputStream(outputFile);
        Workbook w = new XSSFWorkbook();
        Sheet sheet= w.createSheet("sheetone");
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("姓名");

        w.write(fOut);

    }


}
