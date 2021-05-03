
package com.geekhive.foodeydeliveryboy.beans.citylist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityList {

    @SerializedName("City")
    @Expose
    private List<City> city = null;

    public List<City> getCity() {
        return city;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }

}
