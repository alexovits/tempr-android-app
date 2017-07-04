package edu.ubb.tempr.ui;

import android.content.res.Configuration;
import android.nfc.Tag;
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
import edu.ubb.tempr.component.injection.AppComponent;
import edu.ubb.tempr.data.model.HeatingCircuit;
import edu.ubb.tempr.data.model.User;
import edu.ubb.tempr.data.remote.user.UserService;
import edu.ubb.tempr.ui.base.view.BaseActivity;
import edu.ubb.tempr.ui.dashboard.DashboardFragment;
import edu.ubb.tempr.ui.dashboard.DashboardViewModel;
import edu.ubb.tempr.ui.dashboard.HeatingCircuitAdapter;
import edu.ubb.tempr.ui.login.LoginViewModel;
import edu.ubb.tempr.ui.settings.SettingsFragment;
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

    @Inject
    SessionHelper sessionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find the drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        mDrawer.addDrawerListener(drawerToggle);
        // Find Navigation drawer
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);
        TemprApplication.getAppComponent().inject(this);

        // Switch to default dashboard fragment
        switchFragment(new DashboardFragment(), "Dashboard");

    }

    public void switchFragment(Fragment fragmentView, String viewTitle){
        getSupportFragmentManager().beginTransaction().replace(R.id.flContent, fragmentView).commit();
        setTitle(viewTitle);
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
                switchFragment(new DashboardFragment(), "Dashboard");
                break;
            case R.id.nav_second_fragment:
                Log.i("Main", "Settings");
                switchFragment(new SettingsFragment(), "Dashboard");
                break;
            case R.id.nav_fourth_fragment:
                sessionHelper.clearSession();
                NavigationIntentHelper.startLoginView(this);
                break;
        }
        mDrawer.closeDrawers();
    }
}
