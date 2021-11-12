package com.spectra.fieldforce.fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.spectra.fieldforce.R;
import com.spectra.fieldforce.adapter.AllBucketListAdapter;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.databinding.ActivityAllBucketListBinding;
import com.spectra.fieldforce.databinding.IrFragmentBinding;
import com.spectra.fieldforce.model.gpon.request.BucketListRequest;
import com.spectra.fieldforce.model.gpon.response.GetAllBucketList;
import com.spectra.fieldforce.model.gpon.response.GetSubItemListResponse;
import com.spectra.fieldforce.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAllBucketListFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    ActivityAllBucketListBinding binding;
    private List<GetAllBucketList.Response> getBucketList;
    private List<GetAllBucketList.Response> finalgetBucketList;
    private AlphaAnimation inAnimation;
    private AlphaAnimation outAnimation;
    AllBucketListAdapter allBucketListAdapter;
   // private ArrayList<String> statusType;
    ArrayAdapter<String> adapter;
    ArrayAdapter aa;
    String[] statusType = {"Select Status Type", "Installation Pending","hold", "Assigned", "UnAssigned","Consumption Pending","Consumption Approved","Installation Completed"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ActivityAllBucketListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

  /*  private void init(){
        binding.swipeRefreshLayout.setEnabled(true);
        binding.swipeRefreshLayout.setRefreshing(true);
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            binding.swipeRefreshLayout.setRefreshing(true);
            getallBucketList();
        });
    }*/



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getallBucketList();
        Type();

        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Search(String.valueOf(editable));
            }
        });
    }

    private void Type() {
       // binding.etSearch.setOnClickListener(v-> binding.spSearch.performClick());
        binding.spSearch.setOnItemSelectedListener(this);
         aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,statusType);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_item);
        //Setting the ArrayAdapter data on the Spinner
        binding.spSearch.setAdapter(aa);
    }


        public void Search(String search){
        try{
        List<GetAllBucketList.Response> getBucketListItem;
        getBucketListItem = new ArrayList<>();
        for(GetAllBucketList.Response obj:this.getBucketList) {

            if (obj.getCanId().toLowerCase().contains(search)) {
                getBucketListItem.add(obj);
            } else if (obj.getID().toLowerCase().contains(search.toLowerCase())) {
                getBucketListItem.add(obj);
            } else if (obj.getStatus().contains(search)) {
                getBucketListItem.add(obj);
            }
            else if (search.contains("Consumption Pending")) {
            if (obj.getConsumptionStatus().equals("Waiting for approval")||obj.getConsumptionStatus().equals("Material not Available")||obj.getConsumptionStatus().equals("Pending")) {
                getBucketListItem.add(obj);
            }
            } else if (search.contains("Consumption Approved")) {
            if (obj.getConsumptionStatus().equals("Approved")) {
                    getBucketListItem.add(obj);
            }
            }
            else if (obj.getCustomerName().toLowerCase().contains(search.toLowerCase())) {
                getBucketListItem.add(obj);
            } else if (search.contains("UnAssigned")) {
                if (obj.getEngineerName() == null || obj.getEngineerName().isEmpty()) {
                    getBucketListItem.add(obj);
                }
            } else if (search.contains("Assigned")) {
                if (obj.getEngineerName() != null) {
                    String name = obj.getEngineerName();
                    if (name.isEmpty()) {

                    } else {
                        getBucketListItem.add(obj);
                    }
                }
            }
        }


            binding.tvCount.setVisibility(View.VISIBLE);
            String size = String.valueOf(getBucketListItem.size());
            binding.tvCount.setText(size);
            this.allBucketListAdapter.Filter(getBucketListItem);
            this.allBucketListAdapter.notifyDataSetChanged();
        }catch (Exception ex){
            ex.getMessage();
        }
    }

    public void getallBucketList() {
        SharedPreferences sp1=getActivity().getSharedPreferences("Login",0);
        String vendor =sp1.getString("VenderCode", null);
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        BucketListRequest bucketListRequest = new BucketListRequest();
        bucketListRequest.setAuthkey(Constants.AUTH_KEY);
        bucketListRequest.setAction(Constants.Get_All_BUCKET_LIST);
        bucketListRequest.setVendorCode(vendor);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GetAllBucketList> call = apiService.getAllBucketList(bucketListRequest);
        call.enqueue(new Callback<GetAllBucketList>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<GetAllBucketList> call, Response<GetAllBucketList> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    try {
                        finalgetBucketList = response.body().getResponse();
                        outAnimation = new AlphaAnimation(1f, 0f);
                        outAnimation.setDuration(200);
                        binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                        binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                        if(response.body().getStatus().equals("Success")){
                            binding.rvAllBucketList.setHasFixedSize(true);
                            binding.rvAllBucketList.setLayoutManager(new LinearLayoutManager(getContext()));
                            binding.rvAllBucketList.setNestedScrollingEnabled(false);
                            getBucketList = new ArrayList<>();
                            for (GetAllBucketList.Response item : finalgetBucketList)
                            {
                                if(item.getEngineerName().isEmpty()){
                                    item.setAddAssign("Add");
                                }else{
                                    item.setAddAssign("Assigned");
                                }
                                getBucketList.add(item);
                            }

                            allBucketListAdapter = new AllBucketListAdapter(getActivity(),getBucketList);
                            binding.tvCount.setVisibility(View.VISIBLE);
                            String size = String.valueOf(getBucketList.size());
                            binding.tvCount.setText(size);
                            allBucketListAdapter.notifyDataSetChanged();
                            binding.rvAllBucketList.setAdapter(allBucketListAdapter);

                        }else if(response.body().getStatus().equals("Failure")){
                            Toast.makeText(getContext(),"No Data Available.",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getContext(),"Something went wrong...",Toast.LENGTH_LONG).show();
                        }

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<GetAllBucketList> call, Throwable t) {
                binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                Log.e("RetroError", t.toString());
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.sp_search) {
          //  binding.etSearch.setText(statusType.get(position));
            String status = statusType[position] ;
            if(status.equals("Select Status Type")){

            }else{
                Search(status);
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
