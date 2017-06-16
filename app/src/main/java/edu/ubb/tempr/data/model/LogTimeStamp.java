package edu.ubb.tempr.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zsoltszabo on 6/16/17.
 */

public class LogTimeStamp {
    @SerializedName("dayOfWeek") private String day;
    @SerializedName("hour") private int hour;
    @SerializedName("minute") private int minute;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
