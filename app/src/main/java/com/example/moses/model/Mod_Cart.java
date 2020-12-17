package com.example.moses.model;

public class Mod_Cart {

    String pUid, pId, pTime;

    public Mod_Cart() {

    }

    public Mod_Cart(String pUid, String pId, String pTime) {
        this.pUid = pUid;
        this.pId = pId;
        this.pTime = pTime;
    }

    public String getpUid() {
        return pUid;
    }

    public void setpUid(String pUid) {
        this.pUid = pUid;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpTime() {
        return pTime;
    }

    public void setpTime(String pTime) {
        this.pTime = pTime;
    }
}
