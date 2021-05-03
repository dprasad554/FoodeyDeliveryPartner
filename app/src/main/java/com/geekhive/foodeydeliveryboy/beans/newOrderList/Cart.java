
package com.geekhive.foodeydeliveryboy.beans.newOrderList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cart {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("res_id")
    @Expose
    private String resId;
    @SerializedName("del_id")
    @Expose
    private String delId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("delivery")
    @Expose
    private String delivery;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("totel")
    @Expose
    private String totel;
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
    @SerializedName("reward_discount")
    @Expose
    private Object rewardDiscount;
    @SerializedName("offer_id")
    @Expose
    private String offerId;
    @SerializedName("promo_id")
    @Expose
    private String promoId;
    @SerializedName("offer_discount")
    @Expose
    private String offerDiscount;
    @SerializedName("total_discount")
    @Expose
    private String totalDiscount;
    @SerializedName("instructions")
    @Expose
    private String instructions;
    @SerializedName("take_away")
    @Expose
    private String takeAway;
    @SerializedName("payment_mode")
    @Expose
    private String paymentMode;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("city_id")
    @Expose
    private String cityId;

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

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
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

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTotel() {
        return totel;
    }

    public void setTotel(String totel) {
        this.totel = totel;
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

    public Object getRewardDiscount() {
        return rewardDiscount;
    }

    public void setRewardDiscount(Object rewardDiscount) {
        this.rewardDiscount = rewardDiscount;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getPromoId() {
        return promoId;
    }

    public void setPromoId(String promoId) {
        this.promoId = promoId;
    }

    public String getOfferDiscount() {
        return offerDiscount;
    }

    public void setOfferDiscount(String offerDiscount) {
        this.offerDiscount = offerDiscount;
    }

    public String getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(String totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getTakeAway() {
        return takeAway;
    }

    public void setTakeAway(String takeAway) {
        this.takeAway = takeAway;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

}
