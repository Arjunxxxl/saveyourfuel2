package com.saveyourfuel.saveyourfuel.models;

public class truckInfo {

    String permitType, permitTill, permitFrom, insuranceTill, insuranceFrom, insuranceAmount, insuranceCompany;
    public truckInfo(String permitType,String permitTill,String permitFrom,String insuranceTill,String insuranceFrom,String insuranceAmount,String insuranceCompany){
        this.permitType = permitType;
        this.permitTill = permitTill;
        this.permitFrom = permitFrom;
        this.insuranceTill = insuranceTill;
        this.insuranceFrom = insuranceFrom;
        this.insuranceAmount = insuranceAmount;
        this.insuranceCompany = insuranceCompany;


    }
}
