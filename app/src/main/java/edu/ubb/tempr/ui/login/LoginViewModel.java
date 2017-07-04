package edu.ubb.tempr.ui.login;

import android.databinding.Bindable;
import android.util.Log;
import android.view.View;

import javax.inject.Inject;

import edu.ubb.tempr.component.TemprApplication;
import edu.ubb.tempr.data.model.Thermostat;
import edu.ubb.tempr.data.model.User;
import edu.ubb.tempr.data.remote.user.UserService;
import edu.ubb.tempr.ui.base.viewmodel.BaseViewModel;
import edu.ubb.tempr.util.SessionHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by zsoltszabo on 6/16/17.
 */

public class LoginViewModel extends BaseViewModel<LoginActivity> {

    private static final String TAG = LoginViewModel.class.getName();

    private final LoginActivityInteraction loginActivityInteraction;

    private String username = "";
    private String password = "";
    private UserService userService;

    @Inject
    protected SessionHelper sessionHelper;
    @Inject
    protected Retrofit retrofit;


    public LoginViewModel(LoginActivityInteraction loginActivityInteraction){
        this.loginActivityInteraction = loginActivityInteraction;
        TemprApplication.getAppComponent().inject(this);
        userService = retrofit.create(UserService.class);
    }

    public void loginTapHandler(View v) {
        sessionHelper.storeCredentials(username,password);
        Log.i(TAG, "The stored Authentication-Header: " + sessionHelper.getAuthHeader());
        authenticateUser();
    }

    public void signupTapHandler(View v) {
        loginActivityInteraction.navigateToSingUpView();
    }

    private void authenticateUser(){
        loginActivityInteraction.setProgress(true);
        Call<User> call = userService.loginUser();
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int statusCode = response.code();
                User user = response.body();
                Log.i(TAG, "The response on /login/ is: " + statusCode + " | And the message is: " + user);
                if(statusCode == 200) {
                    fetchAndStoreToken(user.getId());
                }else {
                    loginActivityInteraction.showErrorMessage("Nah.. try again!");
                    sessionHelper.clearSession();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i(TAG, "Something went wrong during the /version/ call: " + t.toString());
                loginActivityInteraction.showErrorMessage("Network problems! Check your connection!");
                sessionHelper.clearSession();
            }

        });
    }

    private void fetchAndStoreToken(final Long userId){
        Call<Thermostat> call = userService.fetchThermostat(userId);
        call.enqueue(new Callback<Thermostat>() {

            @Override
            public void onResponse(Call<Thermostat> call, Response<Thermostat> response) {
                int statusCode = response.code();
                Thermostat thermostat = response.body();
                Log.i(TAG, "The response on /thermostat/ is: " + statusCode + " | And the message is: " + thermostat);
                if(statusCode == 200) {
                    Log.i(TAG,"Successful login with userId="+userId+" and token="+thermostat.getToken());
                    sessionHelper.storeToken(thermostat.getToken());
                    loginActivityInteraction.setProgress(false);
                    loginActivityInteraction.navigateToMainView();
                }else {
                    loginActivityInteraction.showErrorMessage("Nah.. try again!");
                    sessionHelper.clearSession();
                    loginActivityInteraction.setProgress(false);
                }
            }

            @Override
            public void onFailure(Call<Thermostat> call, Throwable t) {
                Log.i(TAG, "Something went wrong during the /version/ call: " + t.toString());
                sessionHelper.clearSession();
                loginActivityInteraction.setProgress(false);
            }

        });
    }

    @Bindable
    public String getUsername(){
        return username;
    }

    @Bindable
    public void setUsername(String username){
        this.username = username;
    }

    @Bindable
    public String getPassword() { return password; }

    @Bindable
    public void setPassword(String password) { this.password = password; }
}
