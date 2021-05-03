
package com.geekhive.foodeydeliveryboy.beans.groceryorderHistory;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GrocOrderHistory {

    @SerializedName("OrderHistory")
    @Expose
    private List<OrderHistory> orderHistory = null;

    public List<OrderHistory> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<OrderHistory> orderHistory) {
        this.orderHistory = orderHistory;
    }

}
