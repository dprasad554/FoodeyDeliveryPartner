
package com.geekhive.foodeydeliveryboy.beans.newOrderList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartDetail {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("cart_id")
    @Expose
    private String cartId;
    @SerializedName("food_id")
    @Expose
    private String foodId;
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
    @SerializedName("Food")
    @Expose
    private Food food;

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

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
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

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

}
