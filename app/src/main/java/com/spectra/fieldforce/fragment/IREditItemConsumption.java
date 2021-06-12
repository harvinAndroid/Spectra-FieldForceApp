package com.spectra.fieldforce.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.spectra.fieldforce.databinding.WcrAddItemConsumptionBinding;
import com.spectra.fieldforce.model.gpon.request.AddItemConsumption;
import com.spectra.fieldforce.model.gpon.request.ItemConsumptionById;
import com.spectra.fieldforce.model.gpon.response.CommonClassResponse;
import com.spectra.fieldforce.model.gpon.response.GetItemConumptionByIdResponse;
import com.spectra.fieldforce.model.gpon.response.GetItemListResponse;
import com.spectra.fieldforce.model.gpon.response.GetSubItemListResponse;
import com.spectra.fieldforce.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IREditItemConsumption extends Fragment implements AdapterView.OnItemSelectedListener{
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
    private String strItemType,strItemTypeData,IrID;
    private  String strsubItemId,strFibre,ItemId,StrSubItem,ItemType,quantity,Serial,CanId,GuIID;;


    public IREditItemConsumption() {

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
        ItemId = requireArguments().getString("ItemId");
        IrID = requireArguments().getString("IrID");
        init();
    }

    private void init(){
        Type();
        getItemConsumptionDetailsById(ItemId);
        binding.btSubmit.setText(getResources().getString(R.string.update));
        binding.etType.setOnClickListener(v-> binding.spType.performClick());
        binding.spType.setOnItemSelectedListener(this);
        binding.etItem.setOnClickListener(v-> binding.spItem.performClick());
        binding.spItem.setOnItemSelectedListener(this);
        binding.etSubitem.setOnClickListener(v-> binding.spSubItem.performClick());
        binding.spSubItem.setOnItemSelectedListener(this);
        binding.btSubmit.setOnClickListener(v -> updateItemConsumption());
        binding.etItemType.setOnClickListener(v-> binding.spItemType.performClick());
        binding.spItemType.setOnItemSelectedListener(this);
        binding.etSubitem.setOnClickListener(v -> getSubItemList(ItemId));
    }

    private void Type() {
        itemType = new ArrayList<String>();
        itemType.add("Select Consumption Type");
        itemType.add("WCR");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, itemType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spType.setAdapter(adapter);

        consumptionItemType = new ArrayList<String>();
        itemTypeData = new ArrayList<String>();
        consumptionItemType.add("Select Type");
        consumptionItemType.add("Additional");
        consumptionItemType.add("Default");
        itemTypeData.add("111260001");
        itemTypeData.add("111260000");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, consumptionItemType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spItemType.setAdapter(adapter1);

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

                        subItem= response.body().getResponse().getData();
                        subItemName = new ArrayList<>();
                        subItemId = new ArrayList<>();
                        itemName.add("Select SubItem");
                        for (GetSubItemListResponse.Datum datum: subItem )
                            subItemName.add(datum.getSubItemName());
                        for (GetSubItemListResponse.Datum data : subItem)
                            subItemId.add(data.getSubItemId());
                        ArrayAdapter<String> adapter12 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, itemName);
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.spSubItem.setAdapter(adapter12);
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
          //  strItemType = itemId.get(position);
          /*  if(strItemType.equals("IR")){
                //getItemConsumptionDetails(strItemType);
            }else if(strItemType.equals("WCR")){
              //  getItemConsumptionDetails(strItemType);
            }*/
        }else if (parent.getId() == R.id.sp_item) {
            binding.etItem.setText(itemName.get(position));
            strItemType = itemName.get(position);
            if (position != 0) strItemType = "" + itemId.get(position - 1);
            else strItemType = " ";
            Toast.makeText(getContext(), strItemType, Toast.LENGTH_SHORT).show();
           // getSubItemList(strItemType);

        }else if(parent.getId() == R.id.sp_sub_item){
           binding.etSubitem.setText(subItemName.get(position));
            strsubItemId = subItemName.get(position);
            if (position != 0) strsubItemId = "" + subItemId.get(position - 1);
            else strsubItemId = " ";
        }else if(parent.getId() == R.id.sp_item_type){
            binding.etItemType.setText(consumptionItemType.get(position));
            strItemTypeData = consumptionItemType.get(position);
            if (position != 0) strItemTypeData = "" + itemTypeData.get(position - 1);
            else strItemTypeData = " ";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void getItemConsumptionDetailsById(String itemId) {
        ItemConsumptionById itemConsumptionById = new ItemConsumptionById();
        itemConsumptionById.setAuthkey(Constants.AUTH_KEY);
        itemConsumptionById.setAction(Constants.GET_ITEM_CONSUMPTION_BY_ID);
        itemConsumptionById.setId(itemId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GetItemConumptionByIdResponse> call = apiService.getItemDetailsById(itemConsumptionById);
        call.enqueue(new Callback<GetItemConumptionByIdResponse>() {
            @Override
            public void onResponse(Call<GetItemConumptionByIdResponse> call, Response<GetItemConumptionByIdResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    try {
                        binding.etItem.setText(response.body().response.item);
                        binding.etSubitem.setText(response.body().response.subItem);
                        binding.etItemType.setText(response.body().response.itemType);
                        binding.etSerialNumber.setText(response.body().response.serialNumber);
                        binding.etMacId.setText(response.body().response.macId);
                        binding.etQuantity.setText(response.body().response.quantity);
                        binding.etType.setText(response.body().response.consumptionType);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


            @Override
            public void onFailure(Call<GetItemConumptionByIdResponse> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }

    private void updateItemConsumption(){
        AddItemConsumption addItem_Consumption = new AddItemConsumption();
        addItem_Consumption.setAuthkey(Constants.AUTH_KEY);
        addItem_Consumption.setAction(Constants.EDIT_ITEM_CONSUMPTIONNAV);
        addItem_Consumption.setItem(strItemType);
        addItem_Consumption.setItemID(ItemId);
      //  addItem_Consumption.setSubItem(strsubItemId);
        addItem_Consumption.setItemType(strItemTypeData);
        addItem_Consumption.setConsumptionType("111260001");
        addItem_Consumption.setMacId(Objects.requireNonNull(binding.etMacId.getText()).toString());
        addItem_Consumption.setQuantity(Objects.requireNonNull(binding.etQuantity.getText()).toString());
        addItem_Consumption.setSerialNumber(Objects.requireNonNull(binding.etSerialNumber.getText()).toString());
        addItem_Consumption.setWCRguidId(IrID);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.addItemConsumption(addItem_Consumption);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    try {
                        if(response.body().getResponse().getStatusCode()==200){
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                            nextScreen();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
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
        t1.replace(R.id.frag_container, wcrFragment);
        t1.commit();
    }
}
