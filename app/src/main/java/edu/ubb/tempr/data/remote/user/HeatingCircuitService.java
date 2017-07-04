package edu.ubb.tempr.data.remote.user;

import java.util.List;

import edu.ubb.tempr.data.model.HeatingCircuit;
import edu.ubb.tempr.data.model.LogTimeStamp;
import edu.ubb.tempr.data.model.SensorLog;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zsoltszabo on 7/2/17.
 */

public interface HeatingCircuitService {
    @GET("thermostat/temperatures/")
    Call<List<HeatingCircuit>> getHeatingCircuitList(@Query("token") String token);

    @GET("thermostat/heatingcircuit/history/")
    Call<List<SensorLog>> getHistory(@Query("heatingCircuitId") Long heatingCircuitId);

    @GET("thermostat/heatingcircuit/aiflag/")
    Call<Boolean> getAiFlag(@Query("heatingCircuitId") Long heatingCircuitId);

    @GET("thermostat/heatingcircuit/desiredtemperature/")
    Call<Integer> getDesiredTemp(@Query("heatingCircuitId") Long heatingCircuitId);

    @FormUrlEncoded
    @POST("thermostat/heatingcircuit/aiflag/")
    Call<Void> postAiFlag(@Field("heatingCircuitId") Long heatingCircuitId, @Field("aiFlag") boolean aiFlag);

    @FormUrlEncoded
    @POST("thermostat/heatingcircuit/desiredtemperature/")
    Call<Void> postDesiredTemp(@Field("heatingCircuitId") Long heatingCircuitId, @Field("desiredTemperature") Integer desiredTemperature);
}
