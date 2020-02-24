package com.example.fieldforceapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import com.example.fieldforceapp.Model.AssignmentRequest;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonElement;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener{
    Activity activity;
    private TextView textView;
    private String StatusMess;
    private Button BnLogOut;
    private DrawerLayout drawerLayout;
    private String couponCodeString;
    private String message;

    OnLogoutListener logoutListener;
    public interface OnLogoutListener
    {
      public void logoutperformed();
    }

    public WelcomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

getAssinment();
        view= inflater.inflate(R.layout.fragment_welcome, container, false);
        textView = view.findViewById(R.id.text_name_info);
        textView. setText("Welcome "+MainActivity.prefConfig.readName());
        BnLogOut = view.findViewById(R.id.btn_logout);
        BnLogOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                logoutListener.logoutperformed();

            }
        });

        //
    //    navigationDrawerSetup();
        return view;
    }
View view;
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
         activity=(Activity) context;
        logoutListener =(OnLogoutListener) activity;
    }
    private void getAssinment(){
        String result;
        String useremail = "harpreet.kaur@spectra.co";
        AssignmentRequest assignmentRequest =new AssignmentRequest();
        assignmentRequest.setAction("assignment");
        assignmentRequest.setUser_email(useremail);
        assignmentRequest.setAuthkey("ac7b51de9d888e1458dd53d8aJAN3ba6f");
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call< JsonElement > call = apiService.performUserAssignment(assignmentRequest);
        call.enqueue(new Callback<JsonElement>() {

            @Override
            public void onResponse( Call<JsonElement> call, Response<JsonElement> response) {

                try
                {
                    //get your response....response.body()
                    if (response.isSuccessful()) {

                        // parse(String.valueOf(response.body()!=null));
                    }

                    //String JsonObj= String.valueOf(response.body());

                 JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));


                  //  couponCodeString = jsonObject.getString("Status");

                    // Log.d(TAG, "User Email ID: " + userEmailN);
                    if(couponCodeString.equals("Failure")){

                        message="Login Failed..Please try again...";
                        // MainActivity.prefConfig.dispalyToast("Login Failed..Please try again...");

                    }else if(couponCodeString.equals("Success")){
                       /* userEmail=jsonObject.getString("response");
                        JSONObject jsonObjectN = new JSONObject(String.valueOf(userEmail));
                        userEmailN=jsonObjectN.getString("name");

                        message="Welcome "+userEmailN;
                        MainActivity.prefConfig.writeLoginStatus(true);
                        loginFormActivityListener.performLogin(userEmailN);
                            */
                    }

                    //StatusMess.split(message);


                    //  Log.d(TAG, "RetroFit2 :RetroGetLogin: " + message);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                // if(response.body().getResponse().equals("ok")){
                //   MainActivity.prefConfig.writeLoginStatus(true);
                // loginFormActivityListener.performLogin(response.body().getName());

                // }
                //  else if(response.body().getResponse().equals("failled")){

                //    MainActivity.prefConfig.dispalyToast("Login Failed..Please try again...");
                // }


            }

            @Override
            public void onFailure(retrofit2.Call<JsonElement> call, Throwable t) {

            }
        });


    }
    private void navigationDrawerSetup() {
//        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        try {
            Toolbar toolbar = view.findViewById(R.id.toolbar);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    activity, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
           // drawerLayout.setDrawerListener(toggle);
            toggle.syncState();
            NavigationView navigationView = view.findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.setItemIconTintList(null);
            View headerView = navigationView.getHeaderView(0);
            TextView userNameTV = headerView.findViewById(R.id.userNameTV);
            drawerLayout.setScrimColor(Color.TRANSPARENT);
            Menu nav_menu = navigationView.getMenu();
            if (nav_menu != null) {
                nav_menu.findItem(R.id.action_Admin).setVisible(true);
                nav_menu.findItem(R.id.nav_att_history).setVisible(true);
                nav_menu.findItem(R.id.nav_att_punch).setVisible(true);
                nav_menu.findItem(R.id.nav_logout).setVisible(true);
                userNameTV.setText(""+MainActivity.prefConfig.readName());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
  //StatusMess.setText("");
}
