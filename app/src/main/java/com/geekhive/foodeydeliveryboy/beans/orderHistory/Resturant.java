
package com.geekhive.foodeydeliveryboy.beans.orderHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Resturant {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("sub_admin_id")
    @Expose
    private String subAdminId;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("long")
    @Expose
    private String _long;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("open_status")
    @Expose
    private String openStatus;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("features")
    @Expose
    private String features;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("timing")
    @Expose
    private String timing;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("firebase_id")
    @Expose
    private String firebaseId;
    @SerializedName("table_booking")
    @Expose
    private String tableBooking;
    @SerializedName("take_away")
    @Expose
    private String takeAway;
    @SerializedName("delivery_time")
    @Expose
    private String deliveryTime;
    @SerializedName("sleep_mode")
    @Expose
    private String sleepMode;
    @SerializedName("approx_amount")
    @Expose
    private String approxAmount;
    @SerializedName("bar_code")
    @Expose
    private String barCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubAdminId() {
        return subAdminId;
    }

    public void setSubAdminId(String subAdminId) {
        this.subAdminId = subAdminId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(String openStatus) {
        this.openStatus = openStatus;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    public String getTableBooking() {
        return tableBooking;
    }

    public void setTableBooking(String tableBooking) {
        this.tableBooking = tableBooking;
    }

    public String getTakeAway() {
        return takeAway;
    }

    public void setTakeAway(String takeAway) {
        this.takeAway = takeAway;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getSleepMode() {
        return sleepMode;
    }

    public void setSleepMode(String sleepMode) {
        this.sleepMode = sleepMode;
    }

    public String getApproxAmount() {
        return approxAmount;
    }

    public void setApproxAmount(String approxAmount) {
        this.approxAmount = approxAmount;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

}
