
package com.geekhive.foodeydeliveryboy.beans.floatcash;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Balance {

    @SerializedName("FloatCash")
    @Expose
    private FloatCash floatCash;
    @SerializedName("FloatCashDetail")
    @Expose
    private List<FloatCashDetail> floatCashDetail = null;

    public FloatCash getFloatCash() {
        return floatCash;
    }

    public void setFloatCash(FloatCash floatCash) {
        this.floatCash = floatCash;
    }

    public List<FloatCashDetail> getFloatCashDetail() {
        return floatCashDetail;
    }

    public void setFloatCashDetail(List<FloatCashDetail> floatCashDetail) {
        this.floatCashDetail = floatCashDetail;
    }

}
