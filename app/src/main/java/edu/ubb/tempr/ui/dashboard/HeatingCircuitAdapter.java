package edu.ubb.tempr.ui.dashboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.ubb.tempr.R;
import edu.ubb.tempr.data.model.HeatingCircuit;
import edu.ubb.tempr.util.NavigationIntentHelper;

/**
 * Created by zsoltszabo on 6/14/17.
 */

public class HeatingCircuitAdapter extends RecyclerView.Adapter<HeatingCircuitAdapter.ViewHolder>{

    private List<HeatingCircuit> heatingCircuitList;
    private Context context;

    public HeatingCircuitAdapter(Context context, List<HeatingCircuit> heatingCircuits){
        this.context = context;
        this.heatingCircuitList = heatingCircuits;
    }

    public Context getContext(){
        return context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_heatingcircuit, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(HeatingCircuitAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        HeatingCircuit heatingCircuit = heatingCircuitList.get(position);

        // Set item views based on your views and data model
        viewHolder.nameTextView.setText(heatingCircuit.getName());
        viewHolder.currentTemp.setText(Integer.toString(heatingCircuit.getCurrentTemperature()));
        viewHolder.nameTextView.setWidth(0);
        //Button button = viewHolder.messageButton;
        //button.setText("Message");
    }

    @Override
    public int getItemCount() {
        return heatingCircuitList.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView, currentTemp;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.hc_name);
            currentTemp = (TextView) itemView.findViewById(R.id.current_temp);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            HeatingCircuit hc = heatingCircuitList.get(position);
            Log.i("ViewHolder","Show the hc with the id="+hc.getId());
            NavigationIntentHelper.startDetailedView(getContext(), hc.getId(), hc.getName(), hc.getDesiredTemperature(), hc.getSuggestedTemperature(), hc.isAiflag());
        }
    }

}
