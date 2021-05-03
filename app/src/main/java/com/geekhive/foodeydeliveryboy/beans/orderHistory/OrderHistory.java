
package com.geekhive.foodeydeliveryboy.beans.orderHistory;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderHistory {

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
