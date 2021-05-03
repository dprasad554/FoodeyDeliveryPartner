
package com.geekhive.foodeydeliveryboy.beans.wallet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Balance {

    @SerializedName("DelWallet")
    @Expose
    private DelWallet delWallet;

    public DelWallet getDelWallet() {
        return delWallet;
    }

    public void setDelWallet(DelWallet delWallet) {
        this.delWallet = delWallet;
    }

}
