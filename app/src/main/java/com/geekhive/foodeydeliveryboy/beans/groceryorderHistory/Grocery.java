
package com.geekhive.foodeydeliveryboy.beans.groceryorderHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Grocery {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("product_category")
    @Expose
    private String productCategory;
    @SerializedName("product_sub_category")
    @Expose
    private String productSubCategory;
    @SerializedName("store_id")
    @Expose
    private String storeId;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("mrp")
    @Expose
    private String mrp;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("product_description")
    @Expose
    private String productDescription;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("cashback")
    @Expose
    private String cashback;
    @SerializedName("getextra")
    @Expose
    private String getextra;
    @SerializedName("quantity_detail")
    @Expose
    private String quantityDetail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductSubCategory() {
        return productSubCategory;
    }

    public void setProductSubCategory(String productSubCategory) {
        this.productSubCategory = productSubCategory;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCashback() {
        return cashback;
    }

    public void setCashback(String cashback) {
        this.cashback = cashback;
    }

    public String getGetextra() {
        return getextra;
    }

    public void setGetextra(String getextra) {
        this.getextra = getextra;
    }

    public String getQuantityDetail() {
        return quantityDetail;
    }

    public void setQuantityDetail(String quantityDetail) {
        this.quantityDetail = quantityDetail;
    }

}
