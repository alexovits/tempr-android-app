package edu.ubb.tempr.data;

/**
 * Created by zsoltszabo on 6/14/17.
 */

public class HeatingCircuit {
    private int currentTemperature =1, desiredTemperature=2, suggestedTemperature=3;

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
}
