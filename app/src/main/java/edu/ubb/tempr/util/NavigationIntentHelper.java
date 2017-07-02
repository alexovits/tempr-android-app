package edu.ubb.tempr.util;

import android.content.Context;
import android.content.Intent;

import edu.ubb.tempr.ui.MainActivity;

/**
 * Created by zsoltszabo on 7/2/17.
 */

public class NavigationIntentHelper {

    public static void startMainView(Context context){
        Intent intent =  new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
