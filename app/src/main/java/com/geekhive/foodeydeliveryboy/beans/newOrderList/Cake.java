
package com.geekhive.foodeydeliveryboy.beans.newOrderList;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cake {

    @SerializedName("CakeCart")
    @Expose
    private CakeCart cakeCart;
    @SerializedName("CakeStore")
    @Expose
    private CakeStore cakeStore;
    @SerializedName("User")
    @Expose
    private User_ user;
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

    public User_ getUser() {
        return user;
    }

    public void setUser(User_ user) {
        this.user = user;
    }

    public List<CakeCartDetail> getCakeCartDetail() {
        return cakeCartDetail;
    }

    public void setCakeCartDetail(List<CakeCartDetail> cakeCartDetail) {
        this.cakeCartDetail = cakeCartDetail;
    }

}
