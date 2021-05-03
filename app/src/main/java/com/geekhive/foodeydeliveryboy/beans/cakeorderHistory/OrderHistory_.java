
package com.geekhive.foodeydeliveryboy.beans.cakeorderHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderHistory_ {

    @SerializedName("CakeCart")
    @Expose
    private CakeCart cakeCart;
    @SerializedName("CakeStore")
    @Expose
    private CakeStore cakeStore;
    @SerializedName("User")
    @Expose
    private User user;
    @SerializedName("Address")
    @Expose
    private Address address;
    @SerializedName("CakeCartDetail")
    @Expose
    private List<CakeCartDetail> cakeCartDetail = null;



    public CakeCart getCakeCart() {
        return cakeCart;
    }

    public void setCakeCart(CakeCart cakeCart) {
        this.cakeCart = cakeCart;
    }

    public CakeStore getCakeStore() {
        return cakeStore;
    }

    public void setCakeStore(CakeStore cakeStore) {
        this.cakeStore = cakeStore;
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

    public List<CakeCartDetail> getCakeCartDetail() {
        return cakeCartDetail;
    }

    public void setCakeCartDetail(List<CakeCartDetail> cakeCartDetail) {
        this.cakeCartDetail = cakeCartDetail;
    }

}
