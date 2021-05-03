
package com.geekhive.foodeydeliveryboy.beans.superadminlist;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SuperAdminList {

    @SerializedName("SuperUser")
    @Expose
    private List<SuperUser> superUser = null;

    public List<SuperUser> getSuperUser() {
        return superUser;
    }

    public void setSuperUser(List<SuperUser> superUser) {
        this.superUser = superUser;
    }

}
