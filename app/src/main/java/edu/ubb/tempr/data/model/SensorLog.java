package edu.ubb.tempr.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zsoltszabo on 6/16/17.
 */

public class SensorLog {
    @SerializedName("heatingCircuitId") private long heatingCircuitId;
    @SerializedName("temperature") private int temperature;
    @SerializedName("logTimeStamp") private LogTimeStamp timeStamp;

    public long getHeatingCircuitId() {
        return heatingCircuitId;
    }

    public void setHeatingCircuitId(long heatingCircuitId) {
        this.heatingCircuitId = heatingCircuitId;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public LogTimeStamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LogTimeStamp timeStamp) {
        this.timeStamp = timeStamp;
    }
}
