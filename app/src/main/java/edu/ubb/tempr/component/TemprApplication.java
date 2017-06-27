package edu.ubb.tempr.component;

import android.app.Application;
import android.net.Network;

import edu.ubb.tempr.component.injection.AppComponent;
import edu.ubb.tempr.component.injection.AppModule;
import edu.ubb.tempr.component.injection.DaggerAppComponent;
import edu.ubb.tempr.component.injection.NetworkModule;

/**
 * Created by zsoltszabo on 6/17/17.
 */

public class TemprApplication extends Application {
    private static AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();
    }

    public static AppComponent getAppComponent(){
        return mAppComponent;
    }
}
