package com.spectra.fieldforce.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.spectra.fieldforce.model.ChangeBinRequest;
import com.spectra.fieldforce.model.SrDetailsListResponse;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.adapter.SrDetailsListAdapter;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SrDetailsListFragment extends Fragment {

    private RecyclerView recyclerView;
    private String str_SrNumber;
    private ArrayList<SrDetailsListResponse.Note> SrDetailsList;
    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_srdetails_list,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerview);
        str_SrNumber = requireArguments().getString("SrNumber");
        fab = view.findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        getSrDetailsList();
    }

    public void getSrDetailsList() {
        String authKey = Constants.AUTH_KEY;
        String action = Constants.GETNOTES_BYSRNUMBER;

        ChangeBinRequest changeBinRequest = new ChangeBinRequest();
        changeBinRequest.setAuthkey(authKey);
        changeBinRequest.setAction(action);
        changeBinRequest.setSrNumber(str_SrNumber);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<SrDetailsListResponse> call = apiService.getSrDetailsList(changeBinRequest);
        call.enqueue(new Callback<SrDetailsListResponse>() {
            @Override
            public void onResponse(@NotNull Call<SrDetailsListResponse> call, @NotNull Response<SrDetailsListResponse> response) {
                if (response.isSuccessful() && response.body()!=null) {
                    try {
                        SrDetailsList = response.body().getNotes();
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(new SrDetailsListAdapter(getActivity(),SrDetailsList));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SrDetailsListResponse> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }
}