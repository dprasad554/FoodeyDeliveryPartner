
package com.geekhive.foodeydeliveryboy.beans.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeliveryBoy {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("firebase_id")
    @Expose
    private String firebaseId;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("login_status")
    @Expose
    private String loginStatus;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("id_proof")
    @Expose
    private String idProof;
    @SerializedName("driving_license")
    @Expose
    private String drivingLicense;
    @SerializedName("residance_proof")
    @Expose
    private String residanceProof;
    @SerializedName("profile")
    @Expose
    private String profile;
    @SerializedName("bike_number")
    @Expose
    private String bikeNumber;
    @SerializedName("woking_status")
    @Expose
    private String wokingStatus;
    @SerializedName("sub_admin_id")
    @Expose
    private String subAdminId;
    @SerializedName("city_id")
    @Expose
    private String cityId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getIdProof() {
        return idProof;
    }

    public void setIdProof(String idProof) {
        this.idProof = idProof;
    }

    public String getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public String getResidanceProof() {
        return residanceProof;
    }

    public void setResidanceProof(String residanceProof) {
        this.residanceProof = residanceProof;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getBikeNumber() {
        return bikeNumber;
    }

    public void setBikeNumber(String bikeNumber) {
        this.bikeNumber = bikeNumber;
    }

    public String getWokingStatus() {
        return wokingStatus;
    }

    public void setWokingStatus(String wokingStatus) {
        this.wokingStatus = wokingStatus;
    }

    public String getSubAdminId() {
        return subAdminId;
    }

    public void setSubAdminId(String subAdminId) {
        this.subAdminId = subAdminId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

}
