package edu.ubb.tempr.ui.heatingcircuit;

import java.util.List;

import edu.ubb.tempr.data.model.SensorLog;

/**
 * Created by zsoltszabo on 7/3/17.
 */

public interface DetailedViewInteraction {
    void setChartData(List<SensorLog> historyList);
    void setSliderValue(double angle);
    void setSliderEnabled(boolean enabled);
}
