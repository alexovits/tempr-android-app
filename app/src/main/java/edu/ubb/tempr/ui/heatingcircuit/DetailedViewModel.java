package edu.ubb.tempr.ui.heatingcircuit;

import android.databinding.Bindable;
import android.os.Handler;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import edu.ubb.tempr.component.TemprApplication;
import edu.ubb.tempr.data.model.HeatingCircuit;
import edu.ubb.tempr.data.model.SensorLog;
import edu.ubb.tempr.data.remote.user.HeatingCircuitService;
import edu.ubb.tempr.ui.base.viewmodel.BaseViewModel;
import edu.ubb.tempr.util.SessionHelper;
import me.angrybyte.circularslider.CircularSlider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by zsoltszabo on 7/3/17.
 */

public class DetailedViewModel extends BaseViewModel<DetailedView> implements CircularSlider.OnSliderMovedListener {

    private static final String TAG = DetailedViewModel.class.getName();
    private static final int REFRESH_TIME = 10000;

    private String desiredValue;
    private String sliderCaption;
    private DetailedViewInteraction detailedViewInteraction;
    private HeatingCircuitService heatingCircuitService;
    private HeatingCircuit heatingCircuit;
    private boolean aiSwitchValue;

    @Inject
    Retrofit retrofit;

    @Inject
    SessionHelper sessionHelper;

    public DetailedViewModel(DetailedViewInteraction detailedViewInteraction, HeatingCircuit heatingCircuit) {
        this.detailedViewInteraction = detailedViewInteraction;
        TemprApplication.getAppComponent().inject(this);
        heatingCircuitService = retrofit.create(HeatingCircuitService.class);
        this.heatingCircuit = heatingCircuit;
        fetchHistoryData();
        aiSwitchValue = heatingCircuit.isAiflag();
        if(!aiSwitchValue){
            sliderCaption = "Desired Temperature";
        }else{
            sliderCaption = "Suggested Temperature";
        }
    }

    public void refreshHeatingCircuit() {
        Call<Integer> call = heatingCircuitService.getDesiredTemp(heatingCircuit.getId());
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.i(TAG, "Response: "+response.code()+" "+response.body());
                if(response.code() == 200) {
                    desiredValue = response.body().toString();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.i(TAG, "Response: "+t.toString());
            }
        });
    }

    public void fetchHistoryData() {
        Call<List<SensorLog>> call = heatingCircuitService.getHistory(heatingCircuit.getId());
        call.enqueue(new Callback<List<SensorLog>>() {

            @Override
            public void onResponse(Call<List<SensorLog>> call, Response<List<SensorLog>> response) {
                int statusCode = response.code();
                List<SensorLog> heatingCircuitList = response.body();
                Log.i(TAG, "The response is: " + statusCode + " | And the message is: " + heatingCircuitList);
                detailedViewInteraction.setChartData(heatingCircuitList);
            }

            @Override
            public void onFailure(Call<List<SensorLog>> call, Throwable t) {
                Log.i(TAG, "Something went wrong during the /thermostat/heatinggvircuit/history. call: " + t.toString());
            }

        });
    }

    public void sendAiFlagToServer() {
        Call<Void> call = heatingCircuitService.postAiFlag(heatingCircuit.getId(), aiSwitchValue);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    Log.i(TAG, "Successfully updated aiFlag on server");
                } else {
                    Log.i(TAG, "AiFlag update was unsuccessful: " + response.message() + " " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i(TAG, "Something went wrong during the /thermostat/heatingcircuit/aiFlag call: " + t.toString());
            }

        });
    }

    public void sendDesiredTempToServer() {
        Call<Void> call = heatingCircuitService.postDesiredTemp(heatingCircuit.getId(), Integer.parseInt(desiredValue));
//        Call<Void> call = heatingCircuitService.postDesiredTemp(heatingCircuit.getId(), 15);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    Log.i(TAG, "Successfully updated desiredTemp on server");
                } else {
                    Log.i(TAG, "desiredTemp update was unsuccessful");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i(TAG, "Something went wrong during the /thermostat/heatingcircuit/desiredTemp call: " + t.toString());
            }

        });
    }

    public void setSlider(int temp) {
        Log.i(TAG, "Set slider to temp=" + temp);
        if (temp < 10) temp = 10;
        desiredValue = Integer.toString(temp);
        detailedViewInteraction.setSliderValue(temperatureToAngle(temp));
    }

    @Override
    public void onSliderMoved(double pos) {
        desiredValue = Integer.toString(angleToTemp(pos));
        notifyChange();
    }

    private void startDataObserverHandler() {
        final Handler dataHandler = new Handler();
        final String desiredValueTemp = desiredValue;
        boolean aiSwitchValueTemp = aiSwitchValue;
        dataHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!desiredValueTemp.equals(desiredValue)) {
                    // Refresh slider
                }
                Log.i(TAG, "Na akkor itt is vagyunk: ");
                dataHandler.postDelayed(this, REFRESH_TIME);
            }
        }, REFRESH_TIME);
    }

    private void setAiSwitch(boolean val) {
        aiSwitchValue = val;
    }

    private double temperatureToAngle(int temp) {
        // [17,32]
        if (temp >= 17 && temp <= 32) {
            Log.i(TAG, "[17,32]");
            return -((temp - 17) * (3.14)) / 15;
        }
        // (32,40]
        if (temp <= 40 && temp > 32) {
            Log.i(TAG, "(32,40]");
            return 3.14 - (temp - 32) * (1.57) / 7.5;
        }
        // [10,17.5]
        if (temp >= 10 && temp < 17) {
            Log.i(TAG, "[10,17]");
            return 1.57 - (((temp - 10) * (1.57f)) / 7.5);
        }
        return 0;
    }

    private int angleToTemp(double angle) {
        if (angle > 0) angle = 0.75 + (0.25 - angle);
        else angle = Math.abs(angle);
        return (int) (angle / 0.033) + 10;
    }

    /*
    * Data Binding specific setter methods
    * */
    @Bindable
    public String getDesiredValue() {
        return desiredValue;
    }

    @Bindable
    public void setDesiredValue(String desiredValue) {
        this.desiredValue = desiredValue;
        sendDesiredTempToServer();
    }

    @Bindable
    public boolean isAiSwitchValue() {
        return aiSwitchValue;
    }

    @Bindable
    public void setAiSwitchValue(boolean aiSwitchValue) {
        this.aiSwitchValue = aiSwitchValue;
        sendAiFlagToServer();
        detailedViewInteraction.setSliderEnabled(!aiSwitchValue);
        if(aiSwitchValue){
            sliderCaption = "Suggested Temperature";
            desiredValue = Integer.toString((int) heatingCircuit.getSuggestedTemperature());
        }else{
            sliderCaption = "Desired Temperature";
            desiredValue = Integer.toString(heatingCircuit.getDesiredTemperature());
        }
        notifyChange();
    }

    @Bindable
    public String getSliderCaption() {
        return sliderCaption;
    }

    @Bindable
    public void setSliderCaption(String sliderCaption) {
        this.sliderCaption = sliderCaption;
    }
}
