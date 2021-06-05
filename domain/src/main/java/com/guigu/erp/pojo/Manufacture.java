package com.guigu.erp.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Manufacture {
    private Integer id;

    private String manufactureId;

    private String productId;

    private String productName;

    private BigDecimal amount;

    private BigDecimal testedAmount;

    private String applyIdGroup;

    private String productDescribe;

    private BigDecimal moduleCostPriceSum;

    private BigDecimal realModuleCostPriceSum;

    private BigDecimal labourCostPriceSum;

    private BigDecimal realLabourCostPriceSum;

    private String designer;

    private String register;

    private Date registerTime;

    private String checker;

    private Date checkTime;

    private String remark;

    private String checkTag;

    private String manufactureProcedureTag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getManufactureId() {
        return manufactureId;
    }

    public void setManufactureId(String manufactureId) {
        this.manufactureId = manufactureId == null ? null : manufactureId.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTestedAmount() {
        return testedAmount;
    }

    public void setTestedAmount(BigDecimal testedAmount) {
        this.testedAmount = testedAmount;
    }

    public String getApplyIdGroup() {
        return applyIdGroup;
    }

    public void setApplyIdGroup(String applyIdGroup) {
        this.applyIdGroup = applyIdGroup == null ? null : applyIdGroup.trim();
    }

    public String getProductDescribe() {
        return productDescribe;
    }

    public void setProductDescribe(String productDescribe) {
        this.productDescribe = productDescribe == null ? null : productDescribe.trim();
    }

    public BigDecimal getModuleCostPriceSum() {
        return moduleCostPriceSum;
    }

    public void setModuleCostPriceSum(BigDecimal moduleCostPriceSum) {
        this.moduleCostPriceSum = moduleCostPriceSum;
    }

    public BigDecimal getRealModuleCostPriceSum() {
        return realModuleCostPriceSum;
    }

    public void setRealModuleCostPriceSum(BigDecimal realModuleCostPriceSum) {
        this.realModuleCostPriceSum = realModuleCostPriceSum;
    }

    public BigDecimal getLabourCostPriceSum() {
        return labourCostPriceSum;
    }

    public void setLabourCostPriceSum(BigDecimal labourCostPriceSum) {
        this.labourCostPriceSum = labourCostPriceSum;
    }

    public BigDecimal getRealLabourCostPriceSum() {
        return realLabourCostPriceSum;
    }

    public void setRealLabourCostPriceSum(BigDecimal realLabourCostPriceSum) {
        this.realLabourCostPriceSum = realLabourCostPriceSum;
    }

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer == null ? null : designer.trim();
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register == null ? null : register.trim();
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker == null ? null : checker.trim();
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCheckTag() {
        return checkTag;
    }

    public void setCheckTag(String checkTag) {
        this.checkTag = checkTag == null ? null : checkTag.trim();
    }

    public String getManufactureProcedureTag() {
        return manufactureProcedureTag;
    }

    public void setManufactureProcedureTag(String manufactureProcedureTag) {
        this.manufactureProcedureTag = manufactureProcedureTag == null ? null : manufactureProcedureTag.trim();
    }
}
