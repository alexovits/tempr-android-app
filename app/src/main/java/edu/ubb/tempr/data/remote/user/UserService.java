package edu.ubb.tempr.data.remote.user;

import edu.ubb.tempr.data.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by zsoltszabo on 6/16/17.
 */

public interface UserService {
    @GET("user/login/")
    Call<User> loginUser();

    @POST("user/register/")
    Call<User> signUpUser(@Body User user);
}
