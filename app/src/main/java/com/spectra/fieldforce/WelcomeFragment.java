package com.spectra.fieldforce;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.spectra.fieldforce.Model.AssignmentAdapter;
import com.spectra.fieldforce.Model.AssignmentRequest;
import com.spectra.fieldforce.Model.Order;

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
    AssignmentAdapter assignAdapter;

    public WelcomeFragment() {
        orderList = new ArrayList<>();
    }

    SwipeRefreshLayout swipeRefreshLayout;

    public void getAssignment() {
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
                    swipeRefreshLayout.setRefreshing(false);
                    if (response.isSuccessful()) {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        status = jsonObject.getString("Status");
                        if (status.equals("Failure")) {
                            Log.d("Failure", "error");
                        } else if (status.equals("Success")) {
                            try {
                                result = jsonObject.getJSONArray("response");
                                if (result != null) {
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
        return view;
    }
}