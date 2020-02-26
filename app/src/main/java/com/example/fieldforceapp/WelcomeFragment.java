package com.example.fieldforceapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fieldforceapp.Model.AssignmentRequest;
import com.example.fieldforceapp.Model.Movies;
import com.example.fieldforceapp.Model.MoviesAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
public class WelcomeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener{
    Activity activity;
    private TextView textView;
    private TextView engName;
    private String StatusMess;
    private Button BnLogOut;
    private DrawerLayout drawerLayout;
    private String status;
    private String message;
    private JSONArray result;

    OnLogoutListener logoutListener;
    public interface OnLogoutListener
    {
      void logoutperformed();
    }

    public WelcomeFragment() {
        // Required empty public constructor
    }

    private JSONArray getAssignment(){
        String authKey = "ac7b51de9d888e1458dd53d8aJAN3ba6f";
        String action = "assignment";
        String emailID = "harpreet.kaur@spectra.co";// MainActivity.prefConfig.readName();

        AssignmentRequest assignmentRequest =new AssignmentRequest();
        assignmentRequest.setAuthkey(authKey);
        assignmentRequest.setAction(action);
        assignmentRequest.setemailID(emailID);

        AssignmentInterface apiService = ApiClient.getClient().create(AssignmentInterface.class);
        Call< JsonElement > call = apiService.performUserAssignment(assignmentRequest);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(retrofit2.Call<JsonElement> call, Response<JsonElement> response) {
                try
                {
                    if (response.isSuccessful()) {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        status = jsonObject.getString("Status");
                        if(status.equals("Failure")){
                            message="No Assignment";
                            message = "{\"Status\":\"Success\",\"ErrorCode\":0,\"response\":[{\"assignmentId\":\"1\",\"customerID\":\"9055635\",\"customerName\":\"1 Share Office.Com\",\"customerAddress\":\"12 sant nagar east of kailash 2nd floor above vodafone store,Other,,,,Other,Delhi,Delhi110065\",\"customerCityId\":\"100005\",\"customerMobile\":\"9319196848\",\"customerEmailId\":\"satyveer@outlook.com\",\"customerPrefDate\":\"2020-02-26 00:02:00\",\"case_remarks\":\"Testing\",\"powerLevelINAS\":\"\",\"customerNetworkTech\":\"\",\"slotId\":\"1\",\"pengId\":\"10981\",\"sengId\":\"\",\"sloteBookedDate\":\"0000-00-00 00:00:00\",\"srNumber\":\"SR20000000001\",\"portId\":\"\",\"roasterId\":\"1\",\"srStatus\":null,\"createdOn\":\"2020-02-19 16:02:01\",\"status\":\"1\",\"fromtime\":\"10:00\",\"totime\":\"12:00\",\"engId\":\"3\",\"roasterDate\":\"2020-02-26 00:02:00\",\"roasterFromTime\":null,\"roasterToTime\":null,\"createdBy\":\"Auto Assignment\",\"createdIP\":\"10.158.116.9\",\"modifiedBy\":null,\"modifiedOn\":\"0000-00-00 00:00:00\",\"modifiedIP\":null,\"userId\":\"10981\",\"name\":\"Harpreet Kaur\",\"skills\":\"111260001\",\"type\":\"111260000\",\"Domain\":\"INharpreet.kaur\",\"mobileNo\":\"9876543210\",\"emailId\":\"harpreet.kaur@spectra.co\",\"technology\":\"111260000\",\"reportingManager\":\"INaakriti\",\"cityId\":\"100028\",\"weekOff\":null,\"networkTech\":null},{\"assignmentId\":\"3\",\"customerID\":\"9055635\",\"customerName\":\"1 Share Office.Com\",\"customerAddress\":\"12 sant nagar east of kailash 2nd floor above vodafone store,Other,,,,Other,Delhi,Delhi110065\",\"customerCityId\":\"100005\",\"customerMobile\":\"9319196848\",\"customerEmailId\":\"satyveer@outlook.com\",\"customerPrefDate\":\"2020-02-26 00:02:00\",\"case_remarks\":\"Testing\",\"powerLevelINAS\":\"\",\"customerNetworkTech\":\"\",\"slotId\":\"1\",\"pengId\":\"10981\",\"sengId\":\"\",\"sloteBookedDate\":\"0000-00-00 00:00:00\",\"srNumber\":\"SR20000000002\",\"portId\":\"\",\"roasterId\":\"3\",\"srStatus\":null,\"createdOn\":\"2020-02-19 16:02:01\",\"status\":\"1\",\"fromtime\":\"10:00\",\"totime\":\"12:00\",\"engId\":\"3\",\"roasterDate\":\"2020-02-26 00:02:00\",\"roasterFromTime\":null,\"roasterToTime\":null,\"createdBy\":\"Auto Assignment\",\"createdIP\":\"10.158.116.9\",\"modifiedBy\":null,\"modifiedOn\":\"0000-00-00 00:00:00\",\"modifiedIP\":null,\"userId\":\"10981\",\"name\":\"Harpreet Kaur\",\"skills\":\"111260001\",\"type\":\"111260000\",\"Domain\":\"INharpreet.kaur\",\"mobileNo\":\"9876543210\",\"emailId\":\"harpreet.kaur@spectra.co\",\"technology\":\"111260000\",\"reportingManager\":\"INaakriti\",\"cityId\":\"100028\",\"weekOff\":null,\"networkTech\":null}]}";
                            try{
//                                JsonObject object = new JsonObject();
                                prepareMovieData();
                            } catch (Exception e){
                                e.printStackTrace();
                            }

//
//                            engName = view.findViewById(R.id.userNameTV);
//                            engName.setText(MainActivity.prefConfig.readName());
                        }
                        else if(status.equals("Success")){
                            result = jsonObject.getJSONArray("response");
                            Log.d("API", result.toString());
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<JsonElement> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
        return result;
    }
    MoviesAdapter mAdapter;
    private List<Movies> moviesList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        JSONArray res = getAssignment();
        view= inflater.inflate(R.layout.fragment_welcome, container, false);
        textView = view.findViewById(R.id.text_name_info);
        textView. setText("Welcome "+MainActivity.prefConfig.readName());

        //Log.d("found", engName.toString());
        BnLogOut = view.findViewById(R.id.btn_logout);
        BnLogOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                logoutListener.logoutperformed();

            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        mAdapter = new MoviesAdapter(moviesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        //navigationDrawerSetup();
        return view;
    }
View view;
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
         activity=(Activity) context;
        logoutListener =(OnLogoutListener) activity;
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
  private void prepareMovieData() {
      Movies movie = new Movies("Mad Max: Fury Road", "Action & Adventure", "2015");
      moviesList.add(movie);

      movie = new Movies("Inside Out", "Animation, Kids & Family", "2015");
      moviesList.add(movie);

      movie = new Movies("Star Wars: Episode VII - The Force Awakens", "Action", "2015");
      moviesList.add(movie);

      movie = new Movies("Shaun the Sheep", "Animation", "2015");
      moviesList.add(movie);

      movie = new Movies("The Martian", "Science Fiction & Fantasy", "2015");
      moviesList.add(movie);

      movie = new Movies("Mission: Impossible Rogue Nation", "Action", "2015");
      moviesList.add(movie);

      movie = new Movies("Up", "Animation", "2009");
      moviesList.add(movie);

      movie = new Movies("Star Trek", "Science Fiction", "2009");
      moviesList.add(movie);

      movie = new Movies("The LEGO Movie", "Animation", "2014");
      moviesList.add(movie);

      movie = new Movies("Iron Man", "Action & Adventure", "2008");
      moviesList.add(movie);


      movie = new Movies("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014");
      moviesList.add(movie);

      mAdapter.notifyDataSetChanged();
  }
}
