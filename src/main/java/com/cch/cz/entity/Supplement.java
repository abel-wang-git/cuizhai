package com.cch.cz.entity;

import com.cch.cz.base.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "t_supplement")
public class Supplement extends BaseEntity{
    public final static int NORMAL = 0;

    @Id
    /*合同号*/
    private Long contractNum;
    /*委外公司*/
    private String appointCompany;
    /*委派日期*/
    private String appointData;
    /*退案日期*/
    private String stopAppoint;
    /*客户id*/
    private String customerId;
     /*客户公司部门*/
    private String customerDepartment;
    /*客户住宅电话*/
    private String customerResidencePhone;
    /*客户住宅地址*/
    private String customerResidenceAddress;
    /*客户配偶姓名*/
    private String customerSpouse;
    /*客户配偶联系电话*/
    private String customerSpousePhone;
    /*客户亲戚姓名*/
    private String customerRelativeName;
    /*客户与亲戚关系*/
    private String customerRelationship;
    /*客户亲戚联系电话*/
    private String customerRelativePhone;
    /*其他联系人姓名*/
    private String customerRelativeOther;
    /*其他联系人关系*/
    private String customerRelaOther;
    /*其他联系人电话*/
    private String customerOtherPhone;


    public Long getContractNum() {
        return contractNum;
    }

    public void setContractNum(Long contractNum) {
        this.contractNum = contractNum;
    }

    public String getAppointCompany() {
        return appointCompany;
    }

    public void setAppointCompany(String appointCompany) {
        this.appointCompany = appointCompany;
    }

    public String getAppointData() {
        return appointData;
    }

    public void setAppointData(String appointData) {
        this.appointData = appointData;
    }

    public String getStopAppoint() {
        return stopAppoint;
    }

    public void setStopAppoint(String stopAppoint) {
        this.stopAppoint = stopAppoint;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    private Integer status;
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCustomerDepartment() {
        return customerDepartment;
    }

    public void setCustomerDepartment(String customerDepartment) {
        this.customerDepartment = customerDepartment;
    }

    public String getCustomerResidencePhone() {
        return customerResidencePhone;
    }

    public void setCustomerResidencePhone(String customerResidencePhone) {
        this.customerResidencePhone = customerResidencePhone;
    }

    public String getCustomerResidenceAddress() {
        return customerResidenceAddress;
    }

    public void setCustomerResidenceAddress(String customerResidenceAddress) {
        this.customerResidenceAddress = customerResidenceAddress;
    }

    public String getCustomerSpouse() {
        return customerSpouse;
    }

    public void setCustomerSpouse(String customerSpouse) {
        this.customerSpouse = customerSpouse;
    }

    public String getCustomerSpousePhone() {
        return customerSpousePhone;
    }

    public void setCustomerSpousePhone(String customerSpousePhone) {
        this.customerSpousePhone = customerSpousePhone;
    }

    public String getCustomerRelativeName() {
        return customerRelativeName;
    }

    public void setCustomerRelativeName(String customerRelativeName) {
        this.customerRelativeName = customerRelativeName;
    }

    public String getCustomerRelationship() {
        return customerRelationship;
    }

    public void setCustomerRelationship(String customerRelationship) {
        this.customerRelationship = customerRelationship;
    }

    public String getCustomerRelativePhone() {
        return customerRelativePhone;
    }

    public void setCustomerRelativePhone(String customerRelativePhone) {
        this.customerRelativePhone = customerRelativePhone;
    }

    public String getCustomerRelativeOther() {
        return customerRelativeOther;
    }

    public void setCustomerRelativeOther(String customerRelativeOther) {
        this.customerRelativeOther = customerRelativeOther;
    }

    public String getCustomerRelaOther() {
        return customerRelaOther;
    }

    public void setCustomerRelaOther(String customerRelaOther) {
        this.customerRelaOther = customerRelaOther;
    }

    public String getCustomerOtherPhone() {
        return customerOtherPhone;
    }

    public void setCustomerOtherPhone(String customerOtherPhone) {
        this.customerOtherPhone = customerOtherPhone;
    }


}
