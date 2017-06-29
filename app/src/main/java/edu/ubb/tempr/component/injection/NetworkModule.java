package edu.ubb.tempr.component.injection;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import edu.ubb.tempr.BuildConfig;
import edu.ubb.tempr.component.network.BasicAuthInterceptor;
import edu.ubb.tempr.util.SessionHelper;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zsoltszabo on 6/17/17.
 */

@Module
public class NetworkModule {

    @AppScope
    @Provides
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @AppScope
    @Provides
    SessionHelper provideSessionHelper(SharedPreferences sharedPreferences) {
        return new SessionHelper(sharedPreferences);
    }

    @AppScope
    @Provides
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @AppScope
    @Provides
    BasicAuthInterceptor provideBasicAuthInterceptor(SessionHelper sessionHelper) {
        return new BasicAuthInterceptor(sessionHelper);
    }

    @AppScope
    @Provides
    OkHttpClient provideOkHttpClient(BasicAuthInterceptor interceptor) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .cache(null)
                .build();
        return client;
    }

    @AppScope
    @Provides
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BuildConfig.BASE_API_URL)
                .client(okHttpClient)
                .build();
        return retrofit;
    }

}
