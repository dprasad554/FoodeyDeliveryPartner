
package com.geekhive.foodeydeliveryboy.beans.orderHistory;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderHistory_ {

    @SerializedName("Cart")
    @Expose
    private Cart cart;
    @SerializedName("Resturant")
    @Expose
    private Resturant resturant;
    @SerializedName("User")
    @Expose
    private User user;
    @SerializedName("Address")
    @Expose
    private Address address;
    @SerializedName("CartDetail")
    @Expose
    private List<CartDetail> cartDetail = null;

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Resturant getResturant() {
        return resturant;
    }

    public void setResturant(Resturant resturant) {
        this.resturant = resturant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<CartDetail> getCartDetail() {
        return cartDetail;
    }

    public void setCartDetail(List<CartDetail> cartDetail) {
        this.cartDetail = cartDetail;
    }

}
