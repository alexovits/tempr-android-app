package edu.ubb.tempr.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zsoltszabo on 7/2/17.
 */

public class Thermostat {
    @SerializedName("id") private long id;
    @SerializedName("token") private String token;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Thermostat = {"+getId()+" ,"+getToken()+" }";
    }
}
