package com.spectra.fieldforce.fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.spectra.fieldforce.adapter.MyBucketListAdapter;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.databinding.FragmentMyBucketListBinding;
import com.spectra.fieldforce.model.gpon.request.BucketListRequest;
import com.spectra.fieldforce.model.gpon.response.GetMyBucketList;
import com.spectra.fieldforce.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBucketList extends Fragment {
    FragmentMyBucketListBinding binding;
    private List<GetMyBucketList.Response> getBucketList;
    private AlphaAnimation inAnimation;
    private AlphaAnimation outAnimation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyBucketListBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getallBucketList();
        init();
    }

    private void init(){
        binding.swipeRefreshLayout.setEnabled(true);
        binding.swipeRefreshLayout.setRefreshing(true);
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            binding.swipeRefreshLayout.setRefreshing(true);
           getallBucketList();
        });
    }

    public void getallBucketList() {
        SharedPreferences sp1=getActivity().getSharedPreferences("Login",0);
        String enggId = sp1.getString("EnggId", null);
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        BucketListRequest bucketListRequest = new BucketListRequest();
        bucketListRequest.setAuthkey(Constants.AUTH_KEY);
        bucketListRequest.setAction(Constants.ENGINEER_BUCKET);
        bucketListRequest.setEngineerID(enggId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GetMyBucketList> call = apiService.getMyBucketList(bucketListRequest);
        call.enqueue(new Callback<GetMyBucketList>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<GetMyBucketList> call, Response<GetMyBucketList> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    try {
                        getBucketList = response.body().getResponse();
                        binding.swipeRefreshLayout.setRefreshing(false);
                        outAnimation = new AlphaAnimation(1f, 0f);
                        outAnimation.setDuration(200);
                        binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                        binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                        if(response.body().getStatus().equals("Success")){
                            binding.rvMybucket.setHasFixedSize(true);
                            binding.rvMybucket.setLayoutManager(new LinearLayoutManager(getActivity()));
                            binding.rvMybucket.setAdapter(new MyBucketListAdapter(getActivity(), getBucketList));
                        }else if(response.body().getStatus().equals("Failure")){
                            Toast.makeText(getContext(),"No Data Available.",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getContext(),"Something went wrong..",Toast.LENGTH_LONG).show();
                        }


                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<GetMyBucketList> call, Throwable t) {
                binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                binding.swipeRefreshLayout.setRefreshing(false);
                Log.e("RetroError", t.toString());
            }
        });
    }


}
