package com.example.fieldforceapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fieldforceapp.Model.AssignmentAdapter;
import com.example.fieldforceapp.Model.AssignmentRequest;
import com.example.fieldforceapp.Model.NotificationRequest;
import com.example.fieldforceapp.Model.Order;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    Activity activity;
    private TextView engName;
    private Button BnLogOut;
    private DrawerLayout drawerLayout;
    private String status;
    private JSONArray result;
    private String TAG;
    private String fcmToken;
    private String EmailID;

    OnLogoutListener logoutListener;

    public void onClick(View view) {
        logoutListener.logoutperformed();
    }

    public interface OnLogoutListener {
      public void logoutperformed();
    }

    public WelcomeFragment() {

        // Required empty public constructor
    }

    AssignmentAdapter assignAdapter;
    private List<Order> orderList = new ArrayList<>();

    private void getAssignment() {
        String authKey = "ac7b51de9d888e1458dd53d8aJAN3ba6f";
        String action = "assignment";
        EmailID = MainActivity.prefConfig.readName();


        /*Notification*/
        /*FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        fcmToken = task.getResult().getToken();
                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt);
                        Log.d("FCMToken", fcmToken);
                        /*send Notification*/
                   //     SendNotification();
                        //  Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    //}
                //});
        /*Notification*/

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getAssignment();

        View view = inflater.inflate(R.layout.header_main, container, false);
        engName = view.findViewById(R.id.menu_text);
        engName.setText(MainActivity.prefConfig.readName());
        BnLogOut = view.findViewById(R.id.btn_logout);
        BnLogOut.setOnClickListener((View.OnClickListener) logoutListener);
        view = inflater.inflate(R.layout.fragment_welcome, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        assignAdapter = new AssignmentAdapter(orderList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(assignAdapter);
        //navigationDrawerSetup();
        return view;
    }



   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
        logoutListener = (OnLogoutListener) activity;
    }*/

    private void navigationDrawerSetup() {
//        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
       /* try {
            Toolbar toolbar = view.findViewById(R.id.toolbar);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    activity, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            // drawerLayout.setDrawerListener(toggle);
            toggle.syncState();
            NavigationView navigationView = view.findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.setItemIconTintList(null);
            View headerView = navigationView.getHeaderView(0);
            TextView userNameTV = headerView.findViewById(R.id.menu_text);
            drawerLayout.setScrimColor(Color.TRANSPARENT);
            Menu nav_menu = navigationView.getMenu();
            if (nav_menu != null) {
                nav_menu.findItem(R.id.action_Admin).setVisible(true);
                nav_menu.findItem(R.id.nav_att_history).setVisible(true);
                nav_menu.findItem(R.id.nav_att_punch).setVisible(true);
                nav_menu.findItem(R.id.nav_logout).setVisible(true);
                userNameTV.setText("" + MainActivity.prefConfig.readName());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } */
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.nav_att_punch:
                // TODO: 2/13/2020 your task 1 here
                return true;
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


    private void SendNotification() {
        String authKey = "ac7b51de9d888e1458dd53d8aJAN3ba6f";
        String action = "notification";
        String senderId = "682138145858";
        String notiBody = "Hello";
        String notiTitle = "First Notification";
        String notiIcon = "";

        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setAuthKey(authKey);
        notificationRequest.setAction(action);
        notificationRequest.setSenderId(senderId);
        notificationRequest.setFcmToken(fcmToken);
        notificationRequest.setNotiBody(notiBody);
        notificationRequest.setNotiTitle(notiTitle);
        notificationRequest.setNotiIcon(notiIcon);

        NotificationInterface apiService = ApiClient.getClient().create(NotificationInterface.class);
        Call<JsonElement> call = apiService.sendNotification(notificationRequest);
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


}