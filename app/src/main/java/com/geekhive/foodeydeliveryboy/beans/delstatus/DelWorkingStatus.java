
package com.geekhive.foodeydeliveryboy.beans.delstatus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DelWorkingStatus {

    @SerializedName("DeliveryBoy")
    @Expose
    private DeliveryBoy deliveryBoy;

    public DeliveryBoy getDeliveryBoy() {
        return deliveryBoy;
    }

    public void setDeliveryBoy(DeliveryBoy deliveryBoy) {
        this.deliveryBoy = deliveryBoy;
    }

}
