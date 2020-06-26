package com.spectra.fieldforce;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spectra.fieldforce.Model.AssignmentAdapter;
import com.spectra.fieldforce.Model.AssignmentRequest;
import com.spectra.fieldforce.Model.Order;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.Context;

import static android.content.Context.*;
import static android.content.Context.LOCATION_SERVICE;

public class WelcomeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    AppCompatActivity activity;
    private TextView engName;
    private TextView btnHome;
    private TextView btnLogout;
    private DrawerLayout drawerLayout;
    private String status;
    private JSONArray result;
    private String TAG;
    private String fcmToken;
    private String EmailID;
    public static PrefConfig prefConfig;
    public Button statTime;
    public Button endTime;
    private Button b;
    private TextView t;
    private LocationManager locationManager;
    private LocationListener listener;
    OnLogoutListener onLogoutListener;


    public WelcomeFragment() {

    }



    public interface OnLogoutListener {
        void performLogout();
    }

    AssignmentAdapter assignAdapter;
    private List<Order> orderList = new ArrayList<>();

    private void getAssignment() {
        String authKey = "ac7b51de9d888e1458dd53d8aJAN3ba6f";
        String action = "assignment";
        EmailID = MainActivity.prefConfig.readName();

        AssignmentRequest assignmentRequest = new AssignmentRequest();
        assignmentRequest.setAuthkey(authKey);
        assignmentRequest.setAction(action);
        assignmentRequest.setemailID(EmailID);

        AssignmentInterface apiService = ApiClient.getClient().create(AssignmentInterface.class);
        Call<JsonElement> call = apiService.performUserAssignment(assignmentRequest);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(retrofit2.Call<JsonElement> call, Response<JsonElement> response) {
                try {
                    if (response.isSuccessful()) {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        status = jsonObject.getString("Status");
                        if (status.equals("Failure")) {
                            Log.d("Failure", "error");
                        } else if (status.equals("Success")) {
                            try {
                                result = jsonObject.getJSONArray("response");
                                if (result != null) {
                                    for (int i = 0; i < result.length(); i++) {
                                        JSONObject jsonData = new JSONObject(String.valueOf(result.getString(i)));
                                        Log.d("APIResponse", jsonData.toString());
                                        Gson gson = new Gson();
                                        Order order = gson.fromJson(jsonData.toString(), Order.class);
                                        orderList.add(order);
                                    }
                                }

                                assignAdapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(retrofit2.Call<JsonElement> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }

    private void getLatLont(){

        t = (TextView) activity.findViewById(R.id.textViewGSP);
        b = (Button) activity.findViewById(R.id.startTime);
        Context myContext = null;
        locationManager = (LocationManager) myContext.getSystemService(Context.LOCATION_SERVICE);
        //locationManager=(LocationManager)  getSystemService(LOCATION_SERVICE);
        listener = new LocationListener() {
            @Override
            public void onLocationChanged ( Location location ) {
                t.append("\n " + location.getLongitude() + " " + location.getLatitude());
            }

            @Override
            public void onStatusChanged ( String s, int i, Bundle bundle ) {

            }

            @Override
            public void onProviderEnabled ( String s ) {

            }

            @Override
            public void onProviderDisabled ( String s ) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };


        if ((ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        b.setOnClickListener(new View.OnClickListener() {
            @SuppressLint ( "MissingPermission" )
            @Override
            public void onClick ( View view ) {


                //noinspection MissingPermission
                locationManager.requestLocationUpdates("gps", 5000, 0, listener);
            }
        });
        return;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,   String[] permissions,   int[] grantResults) {
        switch (requestCode){
            case 10:
               // configure_button();

                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                                , 10);
                    }
                    return;
                }
                // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
                b.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint ( "MissingPermission" )
                    @Override
                    public void onClick ( View view ) {


                        //noinspection MissingPermission
                        locationManager.requestLocationUpdates("gps", 5000, 0, listener);
                    }
                });




                break;
            default:
                break;
        }
    }


    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navigationDrawerSetup(view);
    }

    private void navigationDrawerSetup(View view) {
//        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        try {
            Toolbar toolbar = view.findViewById(R.id.toolbar);
            drawerLayout = view.findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    activity, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawerLayout.setDrawerListener(toggle);
            drawerLayout.setScrimColor(Color.TRANSPARENT);
            toggle.syncState();
            NavigationView navigationView = view.findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.setItemIconTintList(null);
            View rootview = navigationView.getHeaderView(0);
            engName = rootview.findViewById(R.id.menu_text);
            engName.setText("Hi, "+MainActivity.prefConfig.readUserName());
            btnHome= rootview.findViewById(R.id.nav_home);
            btnHome.setOnClickListener(v -> {
                MainActivity.prefConfig.writeName(MainActivity.prefConfig.readName());
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fregment_container, new WelcomeFragment()).commit();
            });
            btnLogout= rootview.findViewById(R.id.nav_logout);
            btnLogout.setOnClickListener(v -> {
                MainActivity.prefConfig.writeLoginStatus(false);
                MainActivity.prefConfig.writeName("User");
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fregment_container, new LoginFragment()).commit();
            });
            Menu nav_menu = navigationView.getMenu();
            if (nav_menu != null) {
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getAssignment();

        activity = (AppCompatActivity)getActivity();
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        assignAdapter = new AssignmentAdapter(orderList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(assignAdapter);

        getLatLont();

        return view;
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