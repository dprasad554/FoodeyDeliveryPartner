
package com.geekhive.foodeydeliveryboy.beans.newOrderList;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("Resturant")
    @Expose
    private List<Resturant> resturant = null;
    @SerializedName("Cake")
    @Expose
    private List<Cake> cake = null;
    @SerializedName("Grocery")
    @Expose
    private List<Grocery> grocery = null;

    public List<Resturant> getResturant() {
        return resturant;
    }

    public void setResturant(List<Resturant> resturant) {
        this.resturant = resturant;
    }

    public List<Cake> getCake() {
        return cake;
    }

    public void setCake(List<Cake> cake) {
        this.cake = cake;
    }

    public List<Grocery> getGrocery() {
        return grocery;
    }

    public void setGrocery(List<Grocery> grocery) {
        this.grocery = grocery;
    }

}
