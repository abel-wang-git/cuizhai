package com.cch.cz.entity;

import com.cch.cz.base.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "t_supplement")
public class Supplement extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /*合同号*/
    private String contractNum;
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
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
