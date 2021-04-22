package com.spectra.fieldforce.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.spectra.fieldforce.Model.ItemConsumption.DeleteItemConsumption;
import com.spectra.fieldforce.Model.ItemConsumption.GetItemConsumption;
import com.spectra.fieldforce.Model.ItemConsumption.ItemConsumptionDetails;
import com.spectra.fieldforce.Model.ItemConsumption.ItemConsumptionRequest;
import com.spectra.fieldforce.Model.ItemConsumption.ItemResponse;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.activity.MainActivity;
import com.spectra.fieldforce.adapter.SpinnerAdapter;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.utils.Constants;
import java.util.ArrayList;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditItemConsumptionFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private String canId,SrNumber;
    private EditText et_consumption_type,et_item,et_subitem,et_item_type,et_quantity,et_serial_number,et_mac_id,et_type,et_nrgp_serialnum,et_nrgp,et_nrgp_item;
    private AppCompatSpinner sp_consumption_type,sp_item,sp_sub_item,sp_item_type,sp_type,sp_nrgp_item,sp_nrgp_sub_item;
    private AppCompatButton bt_submit,bt_delete;
    ArrayList<GetItemConsumption.Equipment> equipmentresponselist;
    private ArrayList<GetItemConsumption.Item> itemlist;
    private ArrayList<String> itemConsuptionList;
    ArrayList<String> itemConsuptionsubtype;
    private ArrayList<String> item;
    private ArrayList<String> subItem;
    private ArrayList<String> itemType;
    private ArrayList<String> macId;
    private ArrayList<String> serialNumber;
    private ArrayList<String> quantity;
    private ArrayList<String> itemId;
    private ArrayList<String> subItemId;
    private ArrayList<String> itemConsumptionId;
    private ArrayList<String> NRGP;
    private ArrayList<String> NRGPItemId;
    private ArrayList<String> NRGPSerialNum;
    private ArrayList<String> MaxCap;
    private ArrayList<String> SubItemName;
    String strConsumption,strItem,strSubItemType,strType,strNrgp,strNrgpSerial,strNrgpItem;
    ArrayList<String> type;
    private TextView txtHeader;
    ArrayList<String> itemListType;
    String strMacId, strQuantity,strSerialNumber,maxCap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edititem_consumption, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        canId= requireArguments().getString("CustomerId");
        SrNumber = requireArguments().getString("SrNumber");
        Toolbar mtoolbar = getActivity().findViewById(R.id.toolbar);
        txtHeader = mtoolbar.findViewById(R.id.txtHeader);
        txtHeader.setText("Edit Item Consumption");
        et_consumption_type = view.findViewById(R.id.et_consumption_type);
        et_item = view.findViewById(R.id.et_item);
        et_subitem = view.findViewById(R.id.et_subitem);
        et_item_type = view.findViewById(R.id.et_item_type);
        et_quantity = view.findViewById(R.id.et_quantity);
        et_serial_number = view.findViewById(R.id.et_serial_number);
        et_mac_id = view.findViewById(R.id.et_mac_id);
        sp_consumption_type = view.findViewById(R.id.sp_consumption_type);
        sp_item = view.findViewById(R.id.sp_item);
        sp_sub_item = view.findViewById(R.id.sp_sub_item);
        sp_item_type = view.findViewById(R.id.sp_item_type);
        bt_submit = view.findViewById(R.id.bt_submit);
        et_type = view.findViewById(R.id.et_type);
        sp_type = view.findViewById(R.id.sp_type);
        bt_delete = view.findViewById(R.id.bt_delete);
        et_nrgp_serialnum = view.findViewById(R.id.et_nrgp_serialnum);
        et_nrgp = view.findViewById(R.id.et_nrgp);
        et_nrgp_item = view.findViewById(R.id.et_nrgp_item);
        sp_nrgp_item = view.findViewById(R.id.sp_nrgp_item);
        sp_nrgp_sub_item = view.findViewById(R.id.sp_nrgp_sub_item);
        et_consumption_type.setOnClickListener(v -> sp_consumption_type.performClick());
        et_item.setOnClickListener(v -> sp_item.performClick());
        et_subitem.setOnClickListener(v-> sp_sub_item.performClick() );
        et_item_type.setOnClickListener(v-> sp_item_type.performClick());
        et_type.setOnClickListener(v -> sp_type.performClick());
        sp_type.setOnItemSelectedListener(this);
        sp_consumption_type.setOnItemSelectedListener(this);
        Listener();
        Type();
        // getEquipmentItem();
    }

    private void Type() {
        type = new ArrayList<String>();
        type.add("Select Consumption Type");
        type.add("Item");
        type.add("Equipment");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_type.setAdapter(adapter);
    }

    private void Listener() {
        bt_submit.setOnClickListener(v -> {
                    if (sp_type.getSelectedItem().toString().equals("Select Consumption Type"))
                        Toast.makeText(getActivity(), "Please Select Consumption Type", Toast.LENGTH_LONG).show();
                    else if (sp_item.getSelectedItem().toString().equals("Select Item"))
                        Toast.makeText(getActivity(), "Please Select Item", Toast.LENGTH_LONG).show();
                    else if (et_quantity.getText().toString().trim().isEmpty())
                        Toast.makeText(getActivity(), "Please enter Quantity", Toast.LENGTH_LONG).show();
                    else if (et_quantity.getText().toString().equals("0"))
                        Toast.makeText(getActivity(),"Please enter Quantity Max Than 0",Toast.LENGTH_LONG).show();
                    else if (et_serial_number.getText().toString().trim().isEmpty())
                        Toast.makeText(getActivity(), "Please enter Serial Number", Toast.LENGTH_LONG).show();
                    else if (et_mac_id.getText().toString().trim().isEmpty())
                        Toast.makeText(getActivity(), "Please enter Mac Id", Toast.LENGTH_LONG).show();
                    else
                        editItemConsumption(strType);
                }
        );

        et_quantity.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                System.out.println("Check string :" + et_quantity.getText().toString());
                if (!et_quantity.getText().toString().isEmpty()) {
                    int test = Integer.parseInt(et_quantity.getText().toString());
                    try {
                        if (test > Integer.parseInt(maxCap)) {
                            System.out.println("Check string :allow ");
                            et_item_type.setText("Additional");
                            strSubItemType = "Additional";
                        } else {
                            et_item_type.setText("Default");
                            strSubItemType = "Default";
                            //  Toast.makeText(getActivity(), "Quantity Cannot be exceeded more than MAX Cap", Toast.LENGTH_LONG).show();
                        }
                    }catch (NumberFormatException nm){
                        nm.getMessage();
                    }
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                System.out.println("Check111 string :"+et_quantity.getText().toString());


            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_type:
                et_type.setText(type.get(position));
                strType = type.get(position);
                if(strType.equals("Item")){
                    getConsumptionType(strType);
                }else{
                    getConsumptionType(strType);
                }
                break;
            case R.id.sp_consumption_type:
                et_consumption_type.setText(itemConsuptionList.get(position));
                strConsumption = itemConsuptionList.get(position);
                break;
            case R.id.sp_item:
                et_item.setText(item.get(position));
                if (position != 0) strItem = "" + itemConsumptionId.get(position-1);
                else strItem = " ";
                et_subitem.setText(subItem.get(position));
                et_item_type.setText(itemType.get(position));
                if (position != 0) strNrgp = "" + NRGP.get(position - 1);
                else strNrgp = " ";
                et_nrgp.setText(strNrgp);
                if (position != 0) strNrgpItem = "" + NRGPItemId.get(position - 1);
                else strNrgpItem = " ";
                et_nrgp_item.setText(SubItemName.get(position));
                if (position != 0) strNrgpSerial = "" + NRGPSerialNum.get(position-1);
                else strNrgpSerial = " ";
                et_nrgp_serialnum.setText(strNrgpSerial);
                if (position != 0) maxCap = "" + MaxCap.get(position - 1);
                else maxCap = " ";

              if(macId!=null){
                  if (position != 0) strMacId = "" + macId.get(position - 1);
                  else strMacId = " ";
                  et_mac_id.setText(strMacId);
              }
              if(serialNumber!=null){
                  if (position != 0) strSerialNumber = "" + serialNumber.get(position - 1);
                  else strSerialNumber = " ";
                  et_serial_number.setText(strSerialNumber);
              }
                break;

            case R.id.sp_item_type:
                et_item_type.setText(itemListType.get(position));
                strSubItemType = itemListType.get(position);
               if (Integer.parseInt(et_quantity.getText().toString()) > Integer.parseInt(maxCap)) {
                    System.out.println("Check string :allow ");
                    strSubItemType = "Additional";
                    et_item_type.setText("Additional");
                } else {
                    strSubItemType = "Default";
                    et_item_type.setText("Default");
                    //  Toast.makeText(getActivity(), "Quantity Cannot be exceeded more than MAX Cap", Toast.LENGTH_LONG).show();
                }
                if(strSubItemType.equals("Additional")){
                    strSubItemType = "111260001";
                }else{
                    strSubItemType = "111260000";
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getConsumptionType(String type) {

        sp_item.setOnItemSelectedListener(this);
        sp_sub_item.setOnItemSelectedListener(this);
        sp_item_type.setOnItemSelectedListener(this);
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
                    itemConsuptionList = new ArrayList<>();
                    itemConsuptionsubtype = new ArrayList<>();
                    item = new ArrayList<>();
                    subItem = new ArrayList<>();
                    itemType = new ArrayList<>();
                    serialNumber = new ArrayList<>();
                    quantity = new ArrayList<>();
                    macId = new ArrayList<>();
                    itemConsumptionId = new ArrayList<>();
                    itemId = new  ArrayList<>() ;
                    subItemId = new  ArrayList<>() ;
                    NRGP = new ArrayList<>();
                    NRGPItemId = new ArrayList<>();
                    NRGPSerialNum =  new ArrayList<>();
                    MaxCap = new ArrayList<>();
                    SubItemName = new ArrayList<>();
                    SubItemName.add("Select NRGP SubItem");
                    itemConsuptionList.add("Consumption Type");
                    item.add("Select Item");
                    subItem.add("Select Sub Item");
                    itemType.add("Select Item Type");

                    if(type.equals("Item")){
                        if(itemlist!=null) {
                            for (GetItemConsumption.Item i : itemlist)
                                itemConsuptionList.add(i.getConsumptionType());
                            for (GetItemConsumption.Item itemId : itemlist)
                                item.add(itemId.getItemName());
                            for (GetItemConsumption.Item subitem : itemlist)
                                subItem.add(subitem.getSubitemName());
                            for (GetItemConsumption.Item itemtype : itemlist)
                                itemType.add(itemtype.getItemType());
                            for (GetItemConsumption.Item itemId : itemlist)
                                serialNumber.add(itemId.getSerialNumber());
                            for (GetItemConsumption.Item macid : itemlist)
                                macId.add(macid.getMacId());
                            for (GetItemConsumption.Item Quantity : itemlist)
                                quantity.add(Quantity.getQuantity());
                            for (GetItemConsumption.Item ConsumptionId : itemlist)
                                itemConsumptionId.add(ConsumptionId.getItemconsumptionID());
                            for (GetItemConsumption.Item nrgp : itemlist)
                                NRGP.add(nrgp.getNRGP());
                            for(GetItemConsumption.Item nrgpItem : itemlist)
                                NRGPItemId.add(nrgpItem.getNRGPItemID());
                            for(GetItemConsumption.Item nrgpItem : itemlist)
                                NRGPSerialNum.add(nrgpItem.getNRGPSerialNumber());
                            for (GetItemConsumption.Item maxacapp : itemlist)
                                MaxCap.add(maxacapp.getMAXCAP());
                            for(GetItemConsumption.Item itemname : itemlist)
                                SubItemName.add(itemname.getSubitemName());
                        }

                    }else{
                        if(equipmentresponselist!=null){
                            for (GetItemConsumption.Equipment i : equipmentresponselist)
                                itemConsuptionList.add(i.getConsumptionType());
                            for (GetItemConsumption.Equipment itemId : equipmentresponselist)
                                item.add(itemId.getItemName());
                            for (GetItemConsumption.Equipment subitem : equipmentresponselist)
                                subItem.add(subitem.getSubitemName());
                            for (GetItemConsumption.Equipment itemtype : equipmentresponselist)
                                itemType.add(itemtype.getItemType());
                            for (GetItemConsumption.Equipment itemId : equipmentresponselist)
                                serialNumber.add(itemId.getSerialNumber());
                            for (GetItemConsumption.Equipment macid : equipmentresponselist)
                                macId.add(macid.getMacId());
                            for (GetItemConsumption.Equipment Quantity : equipmentresponselist)
                                quantity.add(Quantity.getQuantity());
                            for(GetItemConsumption.Equipment ConsumptionId:equipmentresponselist)
                                itemConsumptionId.add(ConsumptionId.getItemconsumptionID());
                            for (GetItemConsumption.Equipment itemIDD : equipmentresponselist)
                                itemId.add(itemIDD.getItemGUID());
                            for (GetItemConsumption.Equipment nrgp : equipmentresponselist)
                                NRGP.add(nrgp.getNRGP());
                            for(GetItemConsumption.Equipment nrgpItem : equipmentresponselist)
                                NRGPItemId.add(nrgpItem.getNRGPItemID());
                            for(GetItemConsumption.Equipment nrgpItem : equipmentresponselist)
                                NRGPSerialNum.add(nrgpItem.getNRGPSerialNumber());
                            for (GetItemConsumption.Equipment maxacapp : equipmentresponselist)
                                MaxCap.add(maxacapp.getMAXCAP());
                            for(GetItemConsumption.Equipment itemname : equipmentresponselist)
                                SubItemName.add(itemname.getSubitemName());
                        }

                    }

                    if(getActivity()!=null) {
                        SpinnerAdapter spinnerAdapter1 = new SpinnerAdapter(requireActivity(), R.layout.spinner_item, R.id.tv_item_name, item);
                        sp_item.setAdapter(spinnerAdapter1);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetItemConsumption> call, @NonNull Throwable t) {
                //  handleVisOfNetworkAndProgress(View.GONE, View.GONE);
            }
        });
    }


    private void editItemConsumption(String Arayname) {
        strMacId = et_mac_id.getText().toString();
        strQuantity = et_quantity.getText().toString();
        strSerialNumber = et_serial_number.getText().toString();
        strNrgp = et_nrgp.getText().toString();
        strNrgpSerial = et_nrgp_serialnum.getText().toString();

        JsonObject test = new JsonObject();
        JsonArray item = new JsonArray();
        JsonArray equipment = new JsonArray();
        JsonObject subitem = new JsonObject();
        JsonObject subequipment = new JsonObject();
        test.addProperty("Authkey", Constants.AUTH_KEY);
        test.addProperty("Action", Constants.EDIT_ITEM_CONSUMPTION);
        test.addProperty("SrNumber", SrNumber);
        if(Arayname.equals("Item"))
        {
            subitem.addProperty("SerialNumber", strSerialNumber);
            subitem.addProperty("Quantity",strQuantity );
            subitem.addProperty("ItemID",strItem);
            subitem.addProperty("ItemType",strSubItemType);
            subitem.addProperty("MacId", strMacId);
            subitem.addProperty("NRGPSerialNumber",strNrgpSerial);
            subitem.addProperty("NRGP",strNrgp);
            subitem.addProperty("NRGPItem",strNrgpItem);
            item.add(subitem);
        }else{
            subequipment.addProperty("SerialNumber", strSerialNumber);
            subequipment.addProperty("Quantity",strQuantity);
            subequipment.addProperty("ItemID",strItem);
            subequipment.addProperty("ItemType",strSubItemType);
            subequipment.addProperty("MacId", strMacId);
            subequipment.addProperty("NRGPSerialNumber",strNrgpSerial);
            subequipment.addProperty("NRGP",strNrgp);
            subequipment.addProperty("NRGPItem",strNrgpItem);
            equipment.add(subequipment);
        }
        test.add("Itemconsumption",item);
        test.add("ItemEquipment",equipment);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ItemResponse> call = apiService.editItemConsumption(test);
        call.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(@NonNull Call<ItemResponse> call, @NonNull Response<ItemResponse> response) {
                int status = Objects.requireNonNull(response.body()).getStatus();
                if (status==1) {
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    Toast.makeText(getActivity(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                    startActivity(i);
                    requireActivity().finish();
                }else{
                    Toast.makeText(getActivity(),"Something went wrong...",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ItemResponse> call, @NonNull Throwable t) {
                //  handleVisOfNetworkAndProgress(View.GONE, View.GONE);
            }
        });
    }
}
