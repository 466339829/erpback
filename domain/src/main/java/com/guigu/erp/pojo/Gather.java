package com.guigu.erp.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Gather {
    private Integer id;

    private String gatherId;

    private String storer;

    private String reason;

    private String reasonexact;

    private BigDecimal amountSum;

    private BigDecimal costPriceSum;

    private BigDecimal gatheredAmountSum;

    private String remark;

    private String register;

    private Date registerTime;

    private String checker;

    private Date checkTime;

    private String checkTag;

    private String attemper;

    private Date attemperTime;

    private String gatherTag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGatherId() {
        return gatherId;
    }

    public void setGatherId(String gatherId) {
        this.gatherId = gatherId == null ? null : gatherId.trim();
    }

    public String getStorer() {
        return storer;
    }

    public void setStorer(String storer) {
        this.storer = storer == null ? null : storer.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getReasonexact() {
        return reasonexact;
    }

    public void setReasonexact(String reasonexact) {
        this.reasonexact = reasonexact == null ? null : reasonexact.trim();
    }

    public BigDecimal getAmountSum() {
        return amountSum;
    }

    public void setAmountSum(BigDecimal amountSum) {
        this.amountSum = amountSum;
    }

    public BigDecimal getCostPriceSum() {
        return costPriceSum;
    }

    public void setCostPriceSum(BigDecimal costPriceSum) {
        this.costPriceSum = costPriceSum;
    }

    public BigDecimal getGatheredAmountSum() {
        return gatheredAmountSum;
    }

    public void setGatheredAmountSum(BigDecimal gatheredAmountSum) {
        this.gatheredAmountSum = gatheredAmountSum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public String getCheckTag() {
        return checkTag;
    }

    public void setCheckTag(String checkTag) {
        this.checkTag = checkTag == null ? null : checkTag.trim();
    }

    public String getAttemper() {
        return attemper;
    }

    public void setAttemper(String attemper) {
        this.attemper = attemper == null ? null : attemper.trim();
    }

    public Date getAttemperTime() {
        return attemperTime;
    }

    public void setAttemperTime(Date attemperTime) {
        this.attemperTime = attemperTime;
    }

    public String getGatherTag() {
        return gatherTag;
    }

    public void setGatherTag(String gatherTag) {
        this.gatherTag = gatherTag == null ? null : gatherTag.trim();
    }
}
