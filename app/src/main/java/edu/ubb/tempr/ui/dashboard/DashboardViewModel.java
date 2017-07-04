package edu.ubb.tempr.ui.dashboard;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import edu.ubb.tempr.R;
import edu.ubb.tempr.component.TemprApplication;
import edu.ubb.tempr.data.model.HeatingCircuit;
import edu.ubb.tempr.data.remote.user.HeatingCircuitService;
import edu.ubb.tempr.util.SessionHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by zsoltszabo on 7/2/17.
 */

public class DashboardViewModel{

    private final static String TAG = DashboardViewModel.class.getName();

    private View view;
    private Context context;
    private HeatingCircuitService heatingCircuitService;

    @Inject
    protected Retrofit retrofit;
    @Inject
    protected SessionHelper sessionHelper;

    public DashboardViewModel(View view, Context context){
        this.view = view;
        this.context = context;
        TemprApplication.getAppComponent().inject(this);
        heatingCircuitService = retrofit.create(HeatingCircuitService.class);
    }

    public void refreshRecyclerView(){

        Call<List<HeatingCircuit>> call = heatingCircuitService.getHeatingCircuitList(sessionHelper.getToken());
        call.enqueue(new Callback<List<HeatingCircuit>>() {

            @Override
            public void onResponse(Call<List<HeatingCircuit>> call, Response<List<HeatingCircuit>> response) {
                int statusCode = response.code();
                List<HeatingCircuit> heatingCircuitList = response.body();
                Log.i(TAG, "The response is: " + statusCode + " | And the message is: " + heatingCircuitList);
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvContacts);
                HeatingCircuitAdapter heatingCircuitAdapter = new HeatingCircuitAdapter(context, heatingCircuitList);
                recyclerView.setAdapter(heatingCircuitAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
                recyclerView.invalidate();
            }

            @Override
            public void onFailure(Call<List<HeatingCircuit>> call, Throwable t) {
                Log.i(TAG, "Something went wrong during the /version/ call: " + t.toString());
            }

        });

    }


}
