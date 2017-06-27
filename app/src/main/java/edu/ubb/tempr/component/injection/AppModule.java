package edu.ubb.tempr.component.injection;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zsoltszabo on 6/17/17.
 */
@AppScope
@Module
public class AppModule {

    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @AppScope
    @Provides
    Application providesApplication() {
        return mApplication;
    }
}