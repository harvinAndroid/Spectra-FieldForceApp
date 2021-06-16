package com.spectra.fieldforce.activity;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.fragment.DashboardFragment;
import com.spectra.fieldforce.fragment.WelcomeFragment;
import com.spectra.fieldforce.utils.AppConstants;
import com.spectra.fieldforce.utils.Constants;
import com.spectra.fieldforce.utils.PrefConfig;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static PrefConfig prefConfig;
    private static final String TAG = "MainActivity";
    private DrawerLayout drawerLayout;
    private static final int REQ_CODE_VERSION_UPDATE = 530;
    private AppUpdateManager appUpdateManager;
    private InstallStateUpdatedListener installStateUpdatedListener;

    AppCompatActivity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.main_activity);
        checkForAppUpdate();
        prefConfig = new PrefConfig(activity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("MyNotifications", "MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
        if (findViewById(R.id.fregment_container) != null) {
          /*  if (prefConfig.readLoginStatus()) {
                navigationDrawerSetup();
                getSupportFragmentManager().beginTransaction().add(R.id.fregment_container, new WelcomeFragment(), WelcomeFragment.class.getSimpleName()).addToBackStack(null).commit();
            }else*/ if (prefConfig.LoginStatus()){
                navigationDrawerSetup();
              /* Intent i = new Intent(this,DashBoardActivity.class);
               startActivity(i);
               finish();*/
                getSupportFragmentManager().beginTransaction().add(R.id.fregment_container, new DashboardFragment(), DashboardFragment.class.getSimpleName()).addToBackStack(null).commit();
            }
            else {
                finish();
                startActivity(new Intent(activity, LoginActivity.class));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkNewAppVersionState();
    }

    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQ_CODE_VERSION_UPDATE) {
            if (resultCode != RESULT_OK) {
                Log.d(TAG, "Update flow failed! Result code: " + resultCode);
                unregisterInstallStateUpdListener();
            }
        }
    }

    @Override
    protected void onDestroy() {
        unregisterInstallStateUpdListener();
        super.onDestroy();
    }

    private void checkForAppUpdate() {
        appUpdateManager = AppUpdateManagerFactory.create(activity);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        installStateUpdatedListener = installState -> {
            if (installState.installStatus() == InstallStatus.DOWNLOADED)
                popupSnackbarForCompleteUpdateAndUnregister();
        };

        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                startAppUpdateImmediate(appUpdateInfo);
            }
        });
    }

    private void startAppUpdateImmediate(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.IMMEDIATE,
                    this,
                    MainActivity.REQ_CODE_VERSION_UPDATE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    private void popupSnackbarForCompleteUpdateAndUnregister() {
        Snackbar snackbar =
                Snackbar.make(drawerLayout, getString(R.string.update_downloaded), Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.restart, view -> appUpdateManager.completeUpdate());
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();
        unregisterInstallStateUpdListener();
    }

    private void checkNewAppVersionState() {
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {
                            //FLEXIBLE:
                            // If the update is downloaded but not installed,
                            // notify the user to complete the update.
                            if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                                popupSnackbarForCompleteUpdateAndUnregister();
                            }
                            //IMMEDIATE:
                            if (appUpdateInfo.updateAvailability()
                                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                // If an in-app update is already running, resume the update.
                                startAppUpdateImmediate(appUpdateInfo);
                            }
                        });
    }

    /**
     * Needed only for FLEXIBLE update
     */
    private void unregisterInstallStateUpdListener() {
        if (appUpdateManager != null && installStateUpdatedListener != null)
            appUpdateManager.unregisterListener(installStateUpdatedListener);
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 1) {
            finish();
        } else {
            Fragment f = getVisibleFragment();
            if (f instanceof WelcomeFragment)
                ((WelcomeFragment) f).getAssignment();
            getSupportFragmentManager().popBackStack();
        }
    }

    public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }

    /*private void checkUpdate() {
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        Log.d(TAG, "Checking for updates");
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                Log.d(TAG, "Update available");
                try {
                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, this, 300);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d(TAG, "No Update available");
            }
        });
    }*/

    @SuppressLint("SetTextI18n")
    private void navigationDrawerSetup() {
        try {
            Toolbar toolbar = findViewById(R.id.toolbar);
            drawerLayout = findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    activity, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawerLayout.setDrawerListener(toggle);
            drawerLayout.setScrimColor(Color.TRANSPARENT);
            toggle.syncState();
            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.setItemIconTintList(null);
            View dView = navigationView.getHeaderView(0);
            TextView engName = dView.findViewById(R.id.menu_text);
            engName.setText("Hi, " + prefConfig.readUserName());
            TextView btnHome = dView.findViewById(R.id.nav_home);
            btnHome.setOnClickListener(v -> {
                drawerLayout.closeDrawers();
                MainActivity.prefConfig.writeName(MainActivity.prefConfig.readName());
                getSupportFragmentManager().beginTransaction().add(R.id.fregment_container, new DashboardFragment(), DashboardFragment.class.getSimpleName()).addToBackStack(null).commit();
                //     activity.getSupportFragmentManager().beginTransaction().replace(R.id.fregment_container, new WelcomeFragment()).commit();
            });

            TextView btnWiFi = dView.findViewById(R.id.nav_Wifi);
            btnWiFi.setOnClickListener(v -> {
                drawerLayout.closeDrawers();
                final String appPackageName = Constants.PACKAGE_NAME;
                Intent launchIntent = activity.getPackageManager().getLaunchIntentForPackage(appPackageName);
                if (launchIntent != null) {
                    startActivity(launchIntent);
                } else {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                }
            });

            TextView btnSpeed = dView.findViewById(R.id.nav_Speed);
            btnSpeed.setOnClickListener(v -> {
                drawerLayout.closeDrawers();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(Constants.URL));
                startActivity(i);
            });
            TextView btnGpon = dView.findViewById(R.id.nav_gpon);
            btnGpon.setOnClickListener(v -> {
                drawerLayout.closeDrawers();
                Intent i = new Intent(MainActivity.this,BucketTabActivity.class);
                startActivity(i);
                finish();
               /* final String appPackageName = "valuelabs.spectra.com";
                Intent launchIntent = activity.getPackageManager().getLaunchIntentForPackage(appPackageName);
                if (launchIntent != null) {
                    startActivity(launchIntent);
                } else {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                }*/
            });
            TextView btnLogout = dView.findViewById(R.id.nav_logout);
            btnLogout.setOnClickListener(v -> {
                drawerLayout.closeDrawers();
                MainActivity.prefConfig.writeLoginStatus(false);
                MainActivity.prefConfig.LoginStatus(false);
                MainActivity.prefConfig.writeName("User");
                try{
                    SpectraFfaActivity.prefConfig.writeLoginStatus(false);
                    SpectraFfaActivity.prefConfig.LoginStatus(false);
                    SpectraFfaActivity.prefConfig.writeName("User");
                }catch (Exception ex){
                    ex.getMessage();
                }

                startActivity(new Intent(activity, LoginActivity.class));
                finish();
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fregment_container, new LoginFragment()).commit();
            });
            TextView image = dView.findViewById(R.id.image);
            image.setOnClickListener(v -> {
                Intent i = new Intent(MainActivity.this,Activity_Resolve.class);
                startActivity(i);
            });
            Menu nav_menu = navigationView.getMenu();
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d("Click", "Text");
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.nav_att_history:
                // TODO: 2/13/2020 your task 2 here
                return true;
            case R.id.nav_logout:
                // TODO: 2/13/2020 logoout
                return true;
            case R.id.nav_inventory:
                return true;
        }
        return true;
    }
}