package com.example.moses.model;

public class Mod_Cakes {

    String pType, pPrice,pDesc,pDate,pImage,pId, pDeliver, pWeight;

    public Mod_Cakes() {

    }

    public Mod_Cakes(String pType, String pPrice, String pDesc, String pDate, String pImage, String pId, String pDeliver, String pWeight) {
        this.pType = pType;
        this.pPrice = pPrice;
        this.pDesc = pDesc;
        this.pDate = pDate;
        this.pImage = pImage;
        this.pId = pId;
        this.pDeliver = pDeliver;
        this.pWeight = pWeight;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public String getpPrice() {
        return pPrice;
    }

    public void setpPrice(String pPrice) {
        this.pPrice = pPrice;
    }

    public String getpDesc() {
        return pDesc;
    }

    public void setpDesc(String pDesc) {
        this.pDesc = pDesc;
    }

    public String getpDate() {
        return pDate;
    }

    public void setpDate(String pDate) {
        this.pDate = pDate;
    }

    public String getpImage() {
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpDeliver() {
        return pDeliver;
    }

    public void setpDeliver(String pDeliver) {
        this.pDeliver = pDeliver;
    }

    public String getpWeight() {
        return pWeight;
    }

    public void setpWeight(String pWeight) {
        this.pWeight = pWeight;
    }
}
