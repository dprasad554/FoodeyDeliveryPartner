
package com.geekhive.foodeydeliveryboy.beans.newOrderList;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Resturant {

    @SerializedName("Cart")
    @Expose
    private Cart cart;
    @SerializedName("Resturant")
    @Expose
    private Resturant_ resturant;
    @SerializedName("User")
    @Expose
    private User user;
    @SerializedName("CartDetail")
    @Expose
    private List<CartDetail> cartDetail = null;

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Resturant_ getResturant() {
        return resturant;
    }

    public void setResturant(Resturant_ resturant) {
        this.resturant = resturant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartDetail> getCartDetail() {
        return cartDetail;
    }

    public void setCartDetail(List<CartDetail> cartDetail) {
        this.cartDetail = cartDetail;
    }

}
