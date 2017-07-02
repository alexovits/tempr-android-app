package edu.ubb.tempr.util;

import android.content.Context;
import android.content.Intent;

import edu.ubb.tempr.data.model.HeatingCircuit;
import edu.ubb.tempr.ui.MainActivity;
import edu.ubb.tempr.ui.heatingcircuit.HeatingCircuitDetailedView;
import edu.ubb.tempr.ui.login.LoginActivity;

/**
 * Created by zsoltszabo on 7/2/17.
 */

public class NavigationIntentHelper {

    public static void startMainView(Context context){
        Intent intent =  new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void startLoginView(Context context){
        Intent intent =  new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void startDetailedView(Context context, int heatingCircuitId){
        Intent intent = new Intent(context, HeatingCircuitDetailedView.class);

    }
}
