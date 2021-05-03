
package com.geekhive.foodeydeliveryboy.beans.cakeorderHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CakeOrderHistory {

    @SerializedName("OrderHistory")
    @Expose
    private List<OrderHistory_> orderHistory = null;

    public List<OrderHistory_> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<OrderHistory_> orderHistory) {
        this.orderHistory = orderHistory;
    }

}
