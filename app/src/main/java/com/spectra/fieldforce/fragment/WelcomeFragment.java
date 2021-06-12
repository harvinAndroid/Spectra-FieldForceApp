package com.spectra.fieldforce.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.spectra.fieldforce.model.AssignmentRequest;
import com.spectra.fieldforce.model.Order;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.activity.MainActivity;
import com.spectra.fieldforce.adapter.AssignmentAdapter;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WelcomeFragment extends Fragment {
    AppCompatActivity activity;
    private String status;
    private JSONArray result;
    private String EmailID;
    private List<Order> orderList;
    private AssignmentAdapter assignAdapter;
    ConstraintLayout empty_cart;

    public WelcomeFragment() {
        orderList = new ArrayList<>();
    }

    SwipeRefreshLayout swipeRefreshLayout;

    public void getAssignment() {
        AssignmentRequest assignmentRequest = new AssignmentRequest();
        assignmentRequest.setAuthkey(Constants.AUTH_KEY);
        assignmentRequest.setAction(Constants.ASSIGNMENT);
        assignmentRequest.setemailID(MainActivity.prefConfig.readName());

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiService.performUserAssignment(assignmentRequest);
        call.enqueue(new Callback<JsonElement>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                try {
                    swipeRefreshLayout.setRefreshing(false);
                    if (response.isSuccessful()) {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        status = jsonObject.getString("Status");
                        if (status.equals("Failure")) {
                            Log.d("Failure", "error");
                            empty_cart.setVisibility(View.VISIBLE);
                        } else if (status.equals("Success")) {
                            try {
                                result = jsonObject.getJSONArray("response");
                                orderList.clear();
                                Toolbar mtoolbar = activity.findViewById(R.id.toolbar);
                                TextView txtHeader = mtoolbar.findViewById(R.id.txtHeader);
                                txtHeader.setText("Your Assignments");
                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject jsonData = new JSONObject(String.valueOf(result.getString(i)));
                                    Log.d("APIResponse", jsonData.toString());
                                    Gson gson = new Gson();
                                    Order order = gson.fromJson(jsonData.toString(), Order.class);
                                    orderList.add(order);
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
            public void onFailure(Call<JsonElement> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Log.e("RetroError", t.toString());
            }
        });
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (AppCompatActivity) getActivity();
        getAssignment();
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        empty_cart = view.findViewById(R.id.empty_cart);
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            getAssignment();
        });
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        assignAdapter = new AssignmentAdapter(activity, orderList);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(assignAdapter);
        //}

        return view;
    }
}