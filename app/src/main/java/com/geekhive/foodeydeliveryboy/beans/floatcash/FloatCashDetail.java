
package com.geekhive.foodeydeliveryboy.beans.floatcash;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FloatCashDetail {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("del_id")
    @Expose
    private String delId;
    @SerializedName("f_id")
    @Expose
    private String fId;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("txn_type")
    @Expose
    private String txnType;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("status")
    @Expose
    private String status;

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

    public String getFId() {
        return fId;
    }

    public void setFId(String fId) {
        this.fId = fId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

}
