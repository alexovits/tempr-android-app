package edu.ubb.tempr.ui.login;

import android.os.Bundle;
import android.support.transition.Visibility;
import android.view.View;
import android.widget.Toast;

import javax.inject.Inject;

import edu.ubb.tempr.R;
import edu.ubb.tempr.component.TemprApplication;
import edu.ubb.tempr.ui.base.view.BaseActivity;
import edu.ubb.tempr.util.NavigationIntentHelper;
import edu.ubb.tempr.util.SessionHelper;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity<LoginViewModel> implements LoginActivityInteraction{

    // UI references.
    private View mProgressView;

    @Inject
    SessionHelper sessionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TemprApplication.getAppComponent().inject(this);
        // If there's already a user logged in do not greet with login view
        if (sessionHelper.sessionExists()) {
            navigateToMainView();
//            sessionHelper.clearSession(); // For debugging
        }

        setContentView(R.layout.activity_login);

        mProgressView = findViewById(R.id.login_progress);
        getSupportActionBar().hide(); // Hiding the actionbar on login page, it has no use

        setAndBindContentView(R.layout.activity_login, new LoginViewModel(this));
    }



    @Override
    public void showErrorMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToMainView() {
        NavigationIntentHelper.startMainView(this);
    }

    @Override
    public void navigateToSingUpView() {
        NavigationIntentHelper.startSignupView(this);
    }

    @Override
    public void setProgress(boolean state) {
        if(state) {
            mProgressView.setVisibility(View.VISIBLE);
        }else{
            mProgressView.setVisibility(View.INVISIBLE);
        }
    }
}

