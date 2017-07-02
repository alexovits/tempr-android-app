package edu.ubb.tempr.data.remote.user;

import java.util.List;

import edu.ubb.tempr.data.model.HeatingCircuit;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zsoltszabo on 7/2/17.
 */

public interface HeatingCircuitService {
    @GET("thermostat/temperatures/")
    Call<List<HeatingCircuit>> getHeatingCircuitList(@Query("token") String token);
}
