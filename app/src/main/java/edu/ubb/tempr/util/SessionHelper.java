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

    public void storeCredentials(String username, String password) {
        String encodedConcatString = Base64.encodeToString((username + ":" + password).getBytes(), 0);
        setAuthHeader(encodedConcatString.trim()); // Must get rid of new line characters
    }

    public String getAuthHeader() {
        return userSession.getString("Authentication-Header", ""); // Default value is empty string
    }

    public void clearSession() {
        if(sessionExists()){
            Log.i(TAG, "Removing credentials from session");
            prefEditor.remove("Authentication-Header");
            prefEditor.commit();
        }
    }

    public boolean sessionExists() {
        return userSession.contains("Authentication-Header");
    }

    private void setAuthHeader(String headerValue) {
        Log.i("SessionHelper","Storing session data for user");
        prefEditor.putString("Authentication-Header", headerValue);
        prefEditor.commit();
    }


}
