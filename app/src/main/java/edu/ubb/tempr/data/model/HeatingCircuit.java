package edu.ubb.tempr.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zsoltszabo on 6/14/17.
 */

public class HeatingCircuit {
    @SerializedName("heatingCircuitId") private long id;
    @SerializedName("currentTemperature") private int currentTemperature;
    @SerializedName("desiredTemperature") private int desiredTemperature;
    @SerializedName("suggestedTemperature") private int suggestedTemperature;
    @SerializedName("aiflag") private boolean aiflag;
    @SerializedName("heatingCircuitName") private String name;


    public HeatingCircuit() {
        //EMPTY
    }

    public int getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(int currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public int getDesiredTemperature() {
        return desiredTemperature;
    }

    public void setDesiredTemperature(int desiredTemperature) {
        this.desiredTemperature = desiredTemperature;
    }

    public int getSuggestedTemperature() {
        return suggestedTemperature;
    }

    public void setSuggestedTemperature(int suggestedTemperature) {
        this.suggestedTemperature = suggestedTemperature;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isAiflag() {
        return aiflag;
    }

    public void setAiflag(boolean aiflag) {
        this.aiflag = aiflag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "HeatingCircuit={token="+name+" currentTemp="+currentTemperature+" desiredTemp="+desiredTemperature+"}";
    }
}
