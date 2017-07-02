package edu.ubb.tempr.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import edu.ubb.tempr.R;
import edu.ubb.tempr.component.TemprApplication;
import edu.ubb.tempr.data.model.HeatingCircuit;
import edu.ubb.tempr.data.model.User;
import edu.ubb.tempr.data.remote.user.UserService;
import edu.ubb.tempr.ui.dashboard.DashboardFragment;
import edu.ubb.tempr.ui.dashboard.HeatingCircuitAdapter;
import edu.ubb.tempr.util.NavigationIntentHelper;
import edu.ubb.tempr.util.SessionHelper;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity{

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private List<HeatingCircuit> heatingCircuitList;

    @Inject
    Retrofit retrofit;

    @Inject
    SessionHelper sessionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerToggle = setupDrawerToggle();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);

        switchFragment(new DashboardFragment(), "Dashboard");

        // Test
        //setupRecyclerView();

        TemprApplication.getAppComponent().inject(this);
        // getSupportActionBar().setTitle("Dashboard");
    }

    public void switchFragment(Fragment fragmentView, String viewTitle){
        getSupportFragmentManager().beginTransaction().replace(R.id.flContent, fragmentView).commit();
        setTitle(viewTitle);
    }

    private void setupRecyclerView(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvContacts);
        heatingCircuitList = new ArrayList<>();
        for(int i=19; i<23; i++){
            HeatingCircuit hc = new HeatingCircuit();
            hc.setCurrentTemperature(i);

            heatingCircuitList.add(hc);
        }

        HeatingCircuitAdapter heatingCircuitAdapter = new HeatingCircuitAdapter(this, heatingCircuitList);
        recyclerView.setAdapter(heatingCircuitAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        /*switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }*/

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    @Override
    public void onBackPressed() {
        // Right now do nothing since the last view chronologically is the login view
    }

    public void selectDrawerItem(MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                //fragmentClass = FirstFragment.class;
                Log.i("Main","Egyes");
                DashboardFragment dashboardFragment = new DashboardFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.flContent, dashboardFragment).commit();
            case R.id.nav_second_fragment:
                Log.i("Main","Kettes");
                break;
            case R.id.nav_third_fragment:
                Log.i("Main","Hármas");
                break;
            case R.id.nav_fourth_fragment:
                sessionHelper.clearSession();
                NavigationIntentHelper.startLoginView(this);
        }

//        try {
//            fragment = (Fragment) fragmentClass.newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // Insert the fragment by replacing any existing fragment
        /*FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);*/
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }
}
