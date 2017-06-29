package edu.ubb.tempr.component.network;

import java.io.IOException;

import edu.ubb.tempr.util.SessionHelper;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zsoltszabo on 6/17/17.
 */

public class BasicAuthInterceptor implements Interceptor {

    SessionHelper sessionHelper;

    public BasicAuthInterceptor(SessionHelper sessionHelper){
        this.sessionHelper = sessionHelper;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request newRequest = chain.request().newBuilder().header("Authorization", "Basic " + sessionHelper.getAuthHeader()).build();
        return chain.proceed(newRequest);
    }
}
