
package com.geekhive.foodeydeliveryboy.beans.floatcash;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FloatCash {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("del_id")
    @Expose
    private String delId;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("cr_request")
    @Expose
    private String crRequest;
    @SerializedName("cr_amount")
    @Expose
    private String crAmount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDelId() {
        return delId;
    }

    public void setDelId(String delId) {
        this.delId = delId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCrRequest() {
        return crRequest;
    }

    public void setCrRequest(String crRequest) {
        this.crRequest = crRequest;
    }

    public String getCrAmount() {
        return crAmount;
    }

    public void setCrAmount(String crAmount) {
        this.crAmount = crAmount;
    }

}
