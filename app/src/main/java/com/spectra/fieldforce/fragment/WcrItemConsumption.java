package com.spectra.fieldforce.fragment;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.spectra.fieldforce.R;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.databinding.ProvisiongScreenFragmentBinding;
import com.spectra.fieldforce.databinding.WcrAddItemConsumptionBinding;
import com.spectra.fieldforce.model.CommonResponse;
import com.spectra.fieldforce.model.gpon.request.AccountInfoRequest;
import com.spectra.fieldforce.model.gpon.request.AddItemConsumption;
import com.spectra.fieldforce.model.gpon.request.AssociatedResquest;
import com.spectra.fieldforce.model.gpon.request.EditItemConsumptionNavRequest;
import com.spectra.fieldforce.model.gpon.request.GetMaxCap;
import com.spectra.fieldforce.model.gpon.request.ItemConsumptionById;
import com.spectra.fieldforce.model.gpon.response.CommonClassResponse;
import com.spectra.fieldforce.model.gpon.response.GetFmsListResponse;
import com.spectra.fieldforce.model.gpon.response.GetItemConumptionByIdResponse;
import com.spectra.fieldforce.model.gpon.response.GetItemListResponse;
import com.spectra.fieldforce.model.gpon.response.GetMaxCapResponse;
import com.spectra.fieldforce.model.gpon.response.GetSubItemListResponse;
import com.spectra.fieldforce.utils.AppConstants;
import com.spectra.fieldforce.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WcrItemConsumption extends Fragment implements AdapterView.OnItemSelectedListener,View.OnClickListener{

    private WcrAddItemConsumptionBinding binding;
    private ArrayList<String> itemType;
    private ArrayList<String> itemTypeData;
    private List<GetItemListResponse.Datum> itemList;
    private ArrayList<String> itemName;
    private ArrayList<String> itemId;
    private ArrayList<String> consumptionItemType;
    private List<GetSubItemListResponse.Datum> subItem;
    private ArrayList<String> subItemName;
    private ArrayList<String> subItemId;
    private String strItemType,strItemTypeData,strItemname;
    private String strsubItemId,strGuIId,strCanId,OrderId,StatusOfReport,maxCap,StrSubItemName;
    private Boolean strWcrStatus;

    public WcrItemConsumption() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = WcrAddItemConsumptionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        strGuIId = requireArguments().getString("strGuuId");
        strCanId = requireArguments().getString("canId");
        StatusOfReport = requireArguments().getString("StatusofReport");
        OrderId = requireArguments().getString("OrderId");
        strWcrStatus = requireArguments().getBoolean("WcrStatus");

        binding.searchtoolbar.rlBack.setOnClickListener(this);
        binding.searchtoolbar.tvLang.setText(AppConstants.ITEM_CONSUMPTION);
        init();
        binding.etQuantity.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                System.out.println("Check string :" + binding.etQuantity.getText().toString());

                if (!binding.etQuantity.getText().toString().isEmpty()) {
                    try {
                        int test = Integer.parseInt(binding.etQuantity.getText().toString());
                        if (test <= Integer.parseInt(maxCap)) {
                            System.out.println("Check string :allow ");
                        } else {
                            Toast.makeText(getActivity(), "Quantity Cannot be exceeded more than MAX Cap", Toast.LENGTH_LONG).show();
                            binding.etQuantity.setText("");
                        }
                    }catch (NumberFormatException ex){
                        ex.getMessage();
                    }

                }


            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                System.out.println("Check111 string :"+binding.etQuantity.getText().toString());
            }
        });
    }


    private void init(){
        Type();
        getItemConsumptionDetails();
        binding.etType.setOnClickListener(v-> binding.spType.performClick());
        binding.spType.setOnItemSelectedListener(this);
        binding.etItem.setOnClickListener(v-> binding.spItem.performClick());
        binding.spItem.setOnItemSelectedListener(this);
        binding.etSubitem.setOnClickListener(v-> binding.spSubItem.performClick());
        binding.spSubItem.setOnItemSelectedListener(this);
        binding.btSubmit.setOnClickListener(v ->
                updateItemConsumption());
        binding.etItemType.setOnClickListener(v-> binding.spItemType.performClick());
        binding.spItemType.setOnItemSelectedListener(this);
    }



    private void Type() {
        itemType = new ArrayList<String>();
        itemType.add("Select Consumption Type");
        itemType.add("WCR");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, itemType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        binding.spType.setAdapter(adapter);

        consumptionItemType = new ArrayList<String>();
        itemTypeData = new ArrayList<String>();
        consumptionItemType.add("Select Type");
        consumptionItemType.add("Additional");
        itemTypeData.add("111260001");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, consumptionItemType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        binding.spItemType.setAdapter(adapter1);
    }


    private void getMaxCap(String strSubItemName,String ItemNa){
        GetMaxCap getMaxCap = new GetMaxCap();
        getMaxCap.setAuthkey(Constants.AUTH_KEY);
        getMaxCap.setAction(Constants.GET_MAX_CAP);
        getMaxCap.setCanId(strCanId);
        getMaxCap.setItemName(ItemNa);
        getMaxCap.setSubItemName(strSubItemName);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GetMaxCapResponse> call = apiService.getMaxCapValue(getMaxCap);
        call.enqueue(new Callback<GetMaxCapResponse>() {
            @Override
            public void onResponse(Call<GetMaxCapResponse> call, Response<GetMaxCapResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    try {
                        if(response.body().status.equals("Success")){
                            maxCap = response.body().response.data.maxCap;
                            Toast.makeText(getContext(),"Quantity default max cap limit is: "+ maxCap,Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getContext(),response.body().response.message,Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<GetMaxCapResponse> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });

    }

    public void getSubItemList(String strItemType) {
        ItemConsumptionById itemConsumptionById = new ItemConsumptionById();
        itemConsumptionById.setAuthkey(Constants.AUTH_KEY);
        itemConsumptionById.setAction(Constants.GET_SUBITEM_LIST);
        itemConsumptionById.setItemId(strItemType);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GetSubItemListResponse> call = apiService.getSubItem(itemConsumptionById);
        call.enqueue(new Callback<GetSubItemListResponse>() {
            @Override
            public void onResponse(Call<GetSubItemListResponse> call, Response<GetSubItemListResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    try {
                        if(response.body().getStatus().equals("Success")) {

                            subItem = response.body().getResponse().getData();
                            subItemName = new ArrayList<>();
                            subItemId = new ArrayList<>();
                            subItemName.add("Select SubItem");
                            for (GetSubItemListResponse.Datum datum : subItem)
                                subItemName.add(datum.getSubItemName());
                            for (GetSubItemListResponse.Datum data : subItem)
                                subItemId.add(data.getSubItemId());
                            ArrayAdapter<String> adapter12 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, subItemName);
                            adapter12.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            binding.spSubItem.setAdapter(adapter12);
                        }else{
                           // Toast.makeText(getActivity(),"No Data",Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<GetSubItemListResponse> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.sp_type) {
            binding.etType.setText(itemType.get(position));

        }else if (parent.getId() == R.id.sp_item) {
            binding.etItem.setText(itemName.get(position));
            strItemType = itemName.get(position);
            if (position != 0) strItemType = "" + itemId.get(position - 1);
            else strItemType = " ";
            strItemname = itemName.get(position);
            getSubItemList(strItemType);
        }else if(parent.getId() == R.id.sp_sub_item){
            binding.etSubitem.setText(subItemName.get(position));
            strsubItemId = subItemName.get(position);
            if (position != 0) strsubItemId = "" + subItemId.get(position - 1);
            StrSubItemName = (subItemName.get(position));
        }else if(parent.getId() == R.id.sp_item_type){
            binding.etItemType.setText(consumptionItemType.get(position));
            strItemTypeData = consumptionItemType.get(position);
            if (position != 0) strItemTypeData = "" + itemTypeData.get(position - 1);
            else strItemTypeData = " ";
          //  String type = consumptionItemType.get(position);
            getMaxCap(StrSubItemName,strItemname);
          /*  if(type.equals("Default")){

            }*/
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void getItemConsumptionDetails() {
        AccountInfoRequest accountInfoRequest = new AccountInfoRequest();
        accountInfoRequest.setAuthkey(Constants.AUTH_KEY);
        accountInfoRequest.setAction(Constants.GET_ITEM_LIST);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GetItemListResponse> call = apiService.getItemListName(accountInfoRequest);
        call.enqueue(new Callback<GetItemListResponse>() {
            @Override
            public void onResponse(Call<GetItemListResponse> call, Response<GetItemListResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    try {
                        itemList = response.body().getResponse().getItemList().getData();
                        itemName = new ArrayList<>();
                        itemId = new ArrayList<>();
                        itemName.add("Select Item");

                        for (GetItemListResponse.Datum datum :itemList )
                            itemName.add(datum.getItemName());
                        for (GetItemListResponse.Datum data : itemList)
                            itemId.add(data.getItemId());
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, itemName);
                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        binding.spItem.setAdapter(adapter1);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


            @Override
            public void onFailure(Call<GetItemListResponse> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }

    private void updateItemConsumption(){

        AddItemConsumption addItem_Consumption = new AddItemConsumption();
        addItem_Consumption.setAuthkey(Constants.AUTH_KEY);
        addItem_Consumption.setAction(Constants.POST_ITEM_CONSUMPTIONS);
        addItem_Consumption.setItem(strItemType);
        addItem_Consumption.setSubItem(strsubItemId);
        addItem_Consumption.setItemType(strItemTypeData);
        addItem_Consumption.setConsumptionType("111260000");
        addItem_Consumption.setMacId(Objects.requireNonNull(binding.etMacId.getText()).toString());
        addItem_Consumption.setQuantity(Objects.requireNonNull(binding.etQuantity.getText()).toString());
        addItem_Consumption.setSerialNumber(Objects.requireNonNull(binding.etSerialNumber.getText()).toString());
        addItem_Consumption.setWCRguidId(strGuIId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.addItemConsumption(addItem_Consumption);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful() && response.body()!=null) {
                    if (response.body().getStatus().equals("Success")) {
                        try {
                            Toast.makeText(getContext(),"Item Updated Successfully", Toast.LENGTH_LONG).show();
                            nextScreen();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(getContext(), response.body().getResponse().getMessage(), Toast.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(Call<CommonClassResponse> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }

    private void nextScreen(){
        @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t1= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
        WcrFragment wcrFragment = new WcrFragment();
        Bundle accountinfo = new Bundle();
        accountinfo.putString("canId", strCanId);
        accountinfo.putString("StatusofReport", StatusOfReport);
        accountinfo.putString("OrderId", OrderId);
        accountinfo.putBoolean("WcrStatus", strWcrStatus);

        t1.replace(R.id.frag_container, wcrFragment);
        wcrFragment.setArguments(accountinfo);
        t1.commit();
    }

    @Override
    public void onClick(View v) {
        nextScreen();
    }

}
