package edu.ubb.tempr.component.injection;

import dagger.Component;
import edu.ubb.tempr.ui.MainActivity;
import edu.ubb.tempr.ui.dashboard.DashboardViewModel;
import edu.ubb.tempr.ui.login.LoginActivity;
import edu.ubb.tempr.ui.login.LoginViewModel;

/**
 * Created by zsoltszabo on 6/17/17.
 */
@AppScope
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
    void inject(LoginActivity loginActivity);
    void inject(LoginViewModel loginViewModel);
    void inject(MainActivity mainActivity);
    void inject(DashboardViewModel dashboardViewModel);
}
