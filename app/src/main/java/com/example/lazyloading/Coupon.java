
package com.example.lazyloading;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

//TODO : STEP #3 สร้าง POJO class สำหรับรับ JSON จาก API
@SuppressWarnings("unused")
public class Coupon implements Serializable {

    private String btnImage;
    @JsonProperty("Code")
    private String code;
    @JsonProperty("CouponId")
    private String couponId;
    @JsonProperty("CouponTitle")
    private String couponTitle;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("EndDate")
    private String endDate;
    @JsonProperty("Go2Store")
    private String go2Store;
    @JsonProperty("Go2StoreCoupons")
    private String go2StoreCoupons;
    @JsonProperty("RetailerId")
    private String retailerId;
    @JsonProperty("TimeLeft")
    private String timeLeft;
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Visits")
    private String visits;

    public String getBtnImage() {
        return btnImage;
    }

    public void setBtnImage(String btnImage) {
        this.btnImage = btnImage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCouponTitle() {
        return couponTitle;
    }

    public void setCouponTitle(String couponTitle) {
        this.couponTitle = couponTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getGo2Store() {
        return go2Store;
    }

    public void setGo2Store(String go2Store) {
        this.go2Store = go2Store;
    }

    public String getGo2StoreCoupons() {
        return go2StoreCoupons;
    }

    public void setGo2StoreCoupons(String go2StoreCoupons) {
        this.go2StoreCoupons = go2StoreCoupons;
    }

    public String getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(String retailerId) {
        this.retailerId = retailerId;
    }

    public String getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(String timeLeft) {
        this.timeLeft = timeLeft;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVisits() {
        return visits;
    }

    public void setVisits(String visits) {
        this.visits = visits;
    }
}
