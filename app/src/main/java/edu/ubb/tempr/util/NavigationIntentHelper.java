package edu.ubb.tempr.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import edu.ubb.tempr.ui.MainActivity;
import edu.ubb.tempr.ui.heatingcircuit.DetailedView;
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

    public static void startDetailedView(Context context, long heatingCircuitId, String name, int desiredTemp, double suggestedTemp, boolean aiFlag){
        Intent intent = new Intent(context, DetailedView.class);
        Log.i("Navig","Na most: " + desiredTemp);
        intent.putExtra("HeatingCircuit-id",heatingCircuitId);
        intent.putExtra("HeatingCircuit-name", name);
        intent.putExtra("HeatingCircuit-aiflag", aiFlag);
        intent.putExtra("HeatingCircuit-desiredTemp", desiredTemp);
        intent.putExtra("HeatingCircuit-suggestedTemp", (int) suggestedTemp);
        context.startActivity(intent);
    }
}
