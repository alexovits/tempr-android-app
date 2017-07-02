package edu.ubb.tempr.data.remote.user;

import edu.ubb.tempr.data.model.Thermostat;
import edu.ubb.tempr.data.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zsoltszabo on 6/16/17.
 */

public interface UserService {
    @GET("version/")
    Call<String> getVersion();

    @POST("user/login/")
    Call<User> loginUser();

    @GET("user/thermostat/")
    Call<Thermostat> fetchThermostat(@Query("userId") Long userId);

    @POST("user/register/")
    Call<User> signUpUser(@Body User user);
}
