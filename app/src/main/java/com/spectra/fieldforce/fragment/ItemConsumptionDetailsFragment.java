package com.spectra.fieldforce.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.spectra.fieldforce.Model.CanIdRequest;
import com.spectra.fieldforce.Model.CanIdResponse;
import com.spectra.fieldforce.Model.ChangeBinRequest;
import com.spectra.fieldforce.Model.CommonResponse;
import com.spectra.fieldforce.Model.ItemConsumption.GetItemConsumption;
import com.spectra.fieldforce.Model.ItemConsumption.ItemConsumptionRequest;
import com.spectra.fieldforce.Model.ItemConsumption.ItemEquipment;
import com.spectra.fieldforce.Model.ItemConsumption.ItemStatus;
import com.spectra.fieldforce.Model.SrDetailsListResponse;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.activity.Activity_Resolve;
import com.spectra.fieldforce.adapter.ItemConsumptionDetailAdapter;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiClientRetrofit;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.utils.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemConsumptionDetailsFragment extends Fragment {
    private RecyclerView recyclerView;
    private TextView show_msg;
    private FloatingActionButton fab;
   // private Button next_button;
    private ArrayList<GetItemConsumption.Equipment> equipmentresponselist;
    private ArrayList<GetItemConsumption.Item> itemlist;
    private ArrayList<ItemEquipment> details;
    private GetItemConsumption.Response materialList;
    private List<ItemStatus.Response> itemStatus;
    private String canId,SrNumber,approvalStatus,frstStatus,statusMsg,strSlotType,StrSubSubType,customerNetworkTech;

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
        show_msg = view.findViewById(R.id.show_msg);
        fab = view.findViewById(R.id.fab);
      //  next_button = view.findViewById(R.id.next_button);
        canId= requireArguments().getString("CustomerId");
        SrNumber = requireArguments().getString("SrNumber");
        strSlotType = requireArguments().getString("SlotType");
        StrSubSubType = requireArguments().getString("SubSubType");
        customerNetworkTech = requireArguments().getString("customerNetworkTech");
        getStatus();
        getItemConsumptionDetails();
        fab.setOnClickListener(v -> AddMaterial());
     /*   next_button.setOnClickListener(v -> {
            NextScreen();
        });*/
    }

/*
    private void NextScreen(){
        Intent i = new Intent(getActivity(), Activity_Resolve.class);
        i.putExtra("SrNumber", SrNumber);
        i.putExtra("CustomerId", canId);
        i.putExtra("SlotType",strSlotType);
        i.putExtra("SubSubType",StrSubSubType);
        i.putExtra("customerNetworkTech",customerNetworkTech);
        startActivity(i);
        requireActivity().finish();
    }
*/


    private void getItemConsumptionDetails() {
        ItemConsumptionRequest itemConsumptionRequest = new ItemConsumptionRequest(Constants.GET_CONSUMPTION_ITEM,Constants.AUTH_KEY,canId,SrNumber,"false");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GetItemConsumption> call = apiService.getItemConsumption(itemConsumptionRequest);
        call.enqueue(new Callback<GetItemConsumption>() {
            @Override
            public void onResponse(@NonNull Call<GetItemConsumption> call, @NonNull Response<GetItemConsumption> response) {
                int status = Objects.requireNonNull(response.body()).getStatus();
                if (status==1) {
                    itemlist = response.body().getResponse().getItem();
                    equipmentresponselist = response.body().getResponse().getEquipment();
                    materialList = response.body().getResponse();
                    if(itemlist!=null || equipmentresponselist!=null) {
                        details = new ArrayList<>();
                        ItemEquipment temequp;
                        fab.setVisibility(View.VISIBLE);
                       // next_button.setVisibility(View.VISIBLE);
                        if (itemlist != null) {
                            for (int i = 0; i < itemlist.size(); i++) {
                                temequp = new ItemEquipment();
                                temequp.setItemcode(itemlist.get(i).getItemcode());
                                temequp.setItemName(itemlist.get(i).getItemName());
                                temequp.setSubitemName(itemlist.get(i).getSubitemName());
                               // temequp.setSubitemGUID(itemlist.get(i).getSubitemGUID());
                                temequp.setSubitemName(itemlist.get(i).getSubitemName());
                                temequp.setItemconsumptionID(itemlist.get(i).getItemconsumptionID());
                                temequp.setDefaultKey("item");
                                details.add(temequp);
                            }
                        }
                        if (equipmentresponselist != null) {
                            for (int i = 0; i < equipmentresponselist.size(); i++) {
                                temequp = new ItemEquipment();
                                temequp.setItemcode(equipmentresponselist.get(i).getItemcode());
                                temequp.setItemName(equipmentresponselist.get(i).getItemName());
                                temequp.setSubitemName(equipmentresponselist.get(i).getSubitemName());
                                temequp.setSubitemName(equipmentresponselist.get(i).getSubitemName());
                                temequp.setItemconsumptionID(equipmentresponselist.get(i).getItemconsumptionID());
                                temequp.setDefaultKey("equipment");
                                details.add(temequp);
                            }
                        }
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(new ItemConsumptionDetailAdapter(getActivity(), details, canId, SrNumber, approvalStatus, frstStatus,statusMsg));
                    }else{
                        show_msg.setVisibility(View.VISIBLE);
                        fab.setVisibility(View.GONE);
                        //next_button.setVisibility(View.GONE);
                        show_msg.setText("NO DATA FOUND");
                        //Toast.makeText(getActivity(),str_material,Toast.LENGTH_LONG).show();
                    }

                }else if(status==0){
                    show_msg.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.GONE);
                   // next_button.setVisibility(View.GONE);
                    show_msg.setText("NO DATA FOUND");
                }

            }

            @Override
            public void onFailure(@NonNull Call<GetItemConsumption> call, @NonNull Throwable t) {
                //  handleVisOfNetworkAndProgress(View.GONE, View.GONE);
            }
        });
    }

    private void getStatus() {
        ChangeBinRequest checkStatus = new ChangeBinRequest();
        checkStatus.setAction(Constants.GET_CONSUMPTION_STATUS);
        checkStatus.setAuthkey(Constants.AUTH_KEY);
        checkStatus.setSrNumber(SrNumber);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ItemStatus> call = apiService.getMaterialStatus(checkStatus);
        call.enqueue(new Callback<ItemStatus>() {
            @Override
            public void onResponse(@NonNull Call<ItemStatus> call, @NonNull Response<ItemStatus> response) {
                int status = Objects.requireNonNull(response.body()).getStatus();
                if (status==1) {
                    frstStatus =response.body().getResponse().get(0).getFrstatus();
                    approvalStatus = response.body().getResponse().get(0).getApprovalstatus();
                    statusMsg = response.body().getMessage();
                }else {

                }
            }

            @Override
            public void onFailure(@NonNull Call<ItemStatus> call, @NonNull Throwable t) {
                //  handleVisOfNetworkAndProgress(View.GONE, View.GONE);
            }
        });
    }

    private void AddMaterial(){
        Bundle bundle_consumption =new Bundle();
        bundle_consumption.putString("SrNumber", SrNumber);
        bundle_consumption.putString("CustomerId", canId);
        bundle_consumption.putString("SlotType",strSlotType);
        bundle_consumption.putString("SubSubType",StrSubSubType);
        bundle_consumption.putString("customerNetworkTech",customerNetworkTech);
        @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t1= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
        ItemConsumptionFragment itemConsumptionFragment = new ItemConsumptionFragment();
        itemConsumptionFragment.setArguments(bundle_consumption);
        t1.replace(R.id.fregment_container, itemConsumptionFragment);
        t1.commit();
    }

}
