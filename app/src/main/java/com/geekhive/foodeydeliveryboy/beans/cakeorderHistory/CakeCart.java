
package com.geekhive.foodeydeliveryboy.beans.cakeorderHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CakeCart {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("del_id")
    @Expose
    private String delId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("delivery")
    @Expose
    private String delivery;
    @SerializedName("totel")
    @Expose
    private String totel;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("address_id")
    @Expose
    private String addressId;
    @SerializedName("store_id")
    @Expose
    private String storeId;
    @SerializedName("order_type")
    @Expose
    private String orderType;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("cancel_by")
    @Expose
    private String cancelBy;
    @SerializedName("tax")
    @Expose
    private String tax;
    @SerializedName("grand_total")
    @Expose
    private String grandTotal;
    @SerializedName("packing_charge")
    @Expose
    private String packingCharge;

    @SerializedName("delivery_boy_charge")
    @Expose
    private String deliveryBoyCharge;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDelId() {
        return delId;
    }

    public void setDelId(String delId) {
        this.delId = delId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getTotel() {
        return totel;
    }

    public void setTotel(String totel) {
        this.totel = totel;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCancelBy() {
        return cancelBy;
    }

    public void setCancelBy(String cancelBy) {
        this.cancelBy = cancelBy;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getPackingCharge() {
        return packingCharge;
    }

    public void setPackingCharge(String packingCharge) {
        this.packingCharge = packingCharge;
    }

    public String getDeliveryBoyCharge() {
        return deliveryBoyCharge;
    }

    public void setDeliveryBoyCharge(String deliveryBoyCharge) {
        this.deliveryBoyCharge = deliveryBoyCharge;
    }

}
