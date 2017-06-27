package edu.ubb.tempr.util;

import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by zsoltszabo on 6/16/17.
 */

public class SessionHelper {
    SharedPreferences userSession;
    SharedPreferences.Editor prefEditor;

    public SessionHelper(SharedPreferences sharedPreferences) {
        userSession = sharedPreferences;
        prefEditor = userSession.edit();
    }

    public void storeAuthHeader(String headerValue) {
        Log.i("SessionHelper","Storing session data for user");
        prefEditor.putString("Authentication-Header", headerValue);
        prefEditor.commit();
    }

    public String getAuthHeader() {
        return userSession.getString("Authentication-Header", "Szia licensz.");
    }
}
