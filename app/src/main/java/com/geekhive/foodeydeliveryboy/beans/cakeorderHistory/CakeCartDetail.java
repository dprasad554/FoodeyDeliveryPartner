
package com.geekhive.foodeydeliveryboy.beans.cakeorderHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CakeCartDetail {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("cart_id")
    @Expose
    private String cartId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("mrp")
    @Expose
    private String mrp;
    @SerializedName("Cake")
    @Expose
    private Cake cake;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public Cake getCake() {
        return cake;
    }

    public void setCake(Cake cake) {
        this.cake = cake;
    }

}
