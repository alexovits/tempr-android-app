package edu.ubb.tempr.util;

import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

/**
 * Created by zsoltszabo on 6/16/17.
 */

public class SessionHelper {
    private static final String TAG = SessionHelper.class.getName();

    private SharedPreferences userSession;
    private SharedPreferences.Editor prefEditor;

    public SessionHelper(SharedPreferences sharedPreferences) {
        userSession = sharedPreferences;
        prefEditor = userSession.edit();
    }

    public void storeAuthHeader(String username, String password) {
        String encodedConcatString = Base64.encodeToString((username + ":" + password).getBytes(), 0);
        Log.i(TAG, "The encoded output of "+username+":"+password+" -> "+encodedConcatString);
        storeAuthHeader(encodedConcatString);
    }

    private void storeAuthHeader(String headerValue) {
        Log.i("SessionHelper","Storing session data for user");
        prefEditor.putString("Authentication-Header", headerValue);
        prefEditor.commit();
    }

    public String getAuthHeader() {
        return userSession.getString("Authentication-Header", ""); // Default value is empty string
    }
}
