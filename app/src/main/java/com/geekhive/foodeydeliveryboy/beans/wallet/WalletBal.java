
package com.geekhive.foodeydeliveryboy.beans.wallet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletBal {

    @SerializedName("Balance")
    @Expose
    private Balance balance;

    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

}
