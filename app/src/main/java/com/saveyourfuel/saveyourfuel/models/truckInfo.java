package com.saveyourfuel.saveyourfuel.models;

public class truckInfo {

    public String permitType, permitTill, permitFrom, insuranceTill, insuranceFrom, insuranceAmount, insuranceCompany, truckNo;
    public truckInfo(String permitType,String permitTill,String permitFrom,String insuranceTill,String insuranceFrom,String insuranceAmount,String insuranceCompany, String truckNo){
        this.permitType = permitType;
        this.permitTill = permitTill;
        this.permitFrom = permitFrom;
        this.insuranceTill = insuranceTill;
        this.insuranceFrom = insuranceFrom;
        this.insuranceAmount = insuranceAmount;
        this.insuranceCompany = insuranceCompany;
        this.truckNo = truckNo;


    }
}
