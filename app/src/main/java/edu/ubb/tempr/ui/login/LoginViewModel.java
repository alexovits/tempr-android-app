package edu.ubb.tempr.ui.login;

import android.databinding.Bindable;
import android.util.Log;
import android.view.View;

import edu.ubb.tempr.ui.base.viewmodel.BaseViewModel;

/**
 * Created by zsoltszabo on 6/16/17.
 */

public class LoginViewModel extends BaseViewModel<LoginActivity> {
    private final LoginActivityInteraction loginActivityInteraction;

    private String username = "asdf";
    private String password;

    public LoginViewModel(LoginActivityInteraction loginActivityInteraction){
        this.loginActivityInteraction = loginActivityInteraction;
    }

    public void onButtonClick(View v) {
        Log.i("TAG", "FAsZOM");
        loginActivityInteraction.showErrorMessage(username);
    }

    @Bindable
    public String getUsername(){
        return username;
    }

    @Bindable
    public void setUsername(String username){
        this.username = username;
    }
}
