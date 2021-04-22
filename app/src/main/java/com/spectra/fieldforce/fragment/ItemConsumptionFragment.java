package com.spectra.fieldforce.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import androidx.fragment.app.FragmentTransaction;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.spectra.fieldforce.Model.AssignmentRequest;
import com.spectra.fieldforce.Model.ItemConsumption.GetItemConsumptionRequest;
import com.spectra.fieldforce.Model.ItemConsumption.ItemConsumptionDetails;
import com.spectra.fieldforce.Model.ItemConsumption.ItemResponse;
import com.spectra.fieldforce.Model.ItemConsumption.NrgpDetails;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.adapter.SpinnerAdapter;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.utils.Constants;
import java.util.ArrayList;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemConsumptionFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    private String canId,SrNumber,strSlotType,StrSubSubType;
    private EditText et_consumption_type,et_item,et_subitem,et_item_type,et_quantity,et_serial_number,et_mac_id,et_type,et_nrgp_serialnum,et_nrgp,et_nrgp_item,et_nrgp_sub_item;
    private AppCompatSpinner sp_consumption_type,sp_item,sp_sub_item,sp_item_type,sp_type,sp_nrgp_item,sp_nrgp_sub_item;
    private AppCompatButton bt_submit,bt_next;
    private TextView txtHeader;
  //  private FloatingActionButton fab;
    private ArrayList<ItemConsumptionDetails.Item> itemlist;
    private ArrayList<ItemConsumptionDetails.Equipment> equipmentresponselist;
    private ArrayList<String> itemConsuptionList;
    private ArrayList<String> itemConsuptionsubtype;
    private ArrayList<String> item;
    private ArrayList<String> subItem;
    private ArrayList<String> ItemID;
    private ArrayList<String> SubItemID;
    private ArrayList<String> ItemType;
    private ArrayList<String> NRGP;
    private ArrayList<String> MaxCap;
    private ArrayList<String> NrgpDetailsItem;
    private ArrayList<String> NrgpDetailsSubItem;
    private ArrayList<String> NrgpSubbItemName;
    private ArrayList<NrgpDetails.Response> NrgpDetailsList;
    private ArrayList<String> TemplateId;
    private String strConsumption,strItem,strSubItem,strTemplateId,strType,str_itemType,strNrgp,strNrgpSerial,strNrgpItem;
    ArrayList<String> consumptionType;
    ArrayList<String> itemListType;
  //  private BaseActivity baseActivity;
    private String  macId,quantity,serialNumber,maxCap,strNrgpItemType,customerNetworkTech;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.ite_consumption_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar mtoolbar = getActivity().findViewById(R.id.toolbar);
        txtHeader = mtoolbar.findViewById(R.id.txtHeader);
        txtHeader.setText("Item Consumption");
       // baseActivity = (BaseActivity) getActivity();
        canId= requireArguments().getString("CustomerId");
        SrNumber = requireArguments().getString("SrNumber");
        strSlotType = requireArguments().getString("SlotType");
        StrSubSubType = requireArguments().getString("SubSubType");
        customerNetworkTech = requireArguments().getString("customerNetworkTech");

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
       // fab = view.findViewById(R.id.fab);
        et_nrgp_sub_item = view.findViewById(R.id.et_nrgp_sub_item);
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
        et_nrgp_item.setOnClickListener(v-> sp_nrgp_item.performClick());
        et_nrgp_sub_item.setOnClickListener(v-> sp_nrgp_sub_item.performClick());
        sp_type.setOnItemSelectedListener(this);
        sp_nrgp_item.setOnItemSelectedListener(this);
        sp_nrgp_sub_item.setOnItemSelectedListener(this);
        Listener();
        Type();
    }

    private void Type() {
        consumptionType = new ArrayList<String>();
        consumptionType.add("Select Consumption Type");
        consumptionType.add("Item");
        consumptionType.add("Equipment");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, consumptionType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_type.setAdapter(adapter);
    }

    private void Listener(){
        bt_submit.setOnClickListener(v -> {
            if (sp_type.getSelectedItem().toString().equals("Select Consumption Type"))
                Toast.makeText(getActivity(),"Please Select Consumption Type",Toast.LENGTH_LONG).show();
            else if (sp_item.getSelectedItem().toString().equals("Select Item"))
                Toast.makeText(getActivity(),"Please Select Item",Toast.LENGTH_LONG).show();
            else if (et_quantity.getText().toString().trim().isEmpty())
                Toast.makeText(getActivity(),"Please enter Quantity",Toast.LENGTH_LONG).show();
            else if (et_quantity.getText().toString().equals("0"))
                Toast.makeText(getActivity(),"Please enter Quantity Max Than 0",Toast.LENGTH_LONG).show();
            else if (et_serial_number.getText().toString().trim().isEmpty())
                Toast.makeText(getActivity(),"Please enter Serial Number",Toast.LENGTH_LONG).show();
            else if (et_mac_id.getText().toString().trim().isEmpty())
                Toast.makeText(getActivity(),"Please enter Mac Id",Toast.LENGTH_LONG).show();
            else
                    saveItemConsumption(strType);
                }
        );

        et_quantity.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                System.out.println("Check string :" + et_quantity.getText().toString());

                if (!et_quantity.getText().toString().isEmpty()) {
                    int test = Integer.parseInt(et_quantity.getText().toString());

                        if (test <= Integer.parseInt(maxCap)) {
                            System.out.println("Check string :allow ");
                        } else {
                            Toast.makeText(getActivity(), "Quantity Cannot be exceeded more than MAX Cap", Toast.LENGTH_LONG).show();
                            et_quantity.setText("");
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

       /* bt_next.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(),Activity_Resolve.class);
            i.putExtra("SrNumber", SrNumber);
            i.putExtra("CustomerId", canId);
            i.putExtra("SlotType",strSlotType);
            i.putExtra("SubSubType",StrSubSubType);
            i.putExtra("customerNetworkTech",customerNetworkTech);
            startActivity(i);
            requireActivity().finish();
        });*/

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_type:
                et_type.setText(consumptionType.get(position));
                strType = consumptionType.get(position);
                if(strType.equals("Item")){
                    getItemConsumptionDetails(strType);
                }else if(strType.equals("Equipment")){
                    getItemConsumptionDetails(strType);
                }
                break;
            case R.id.sp_consumption_type:
               et_consumption_type.setText(itemConsuptionList.get(position));
               strConsumption=itemConsuptionList.get(position);
                break;
            case R.id.sp_item:
                et_item.setText(item.get(position));
                if (position != 0) strItem = "" + ItemID.get(position - 1);
                else strItem = " ";
                et_subitem.setText(subItem.get(position));
                if(strItem!=null){
                    if (position != 0) strSubItem = "" + SubItemID.get(position - 1);
                    else strSubItem = " ";
                    et_subitem.setText(subItem.get(position));
                    if (position != 0) maxCap = "" + MaxCap.get(position - 1);
                    else maxCap = " ";
                    et_item_type.setText(ItemType.get(position));
                    if (position != 0) str_itemType = "" + ItemType.get(position);
                    else str_itemType = " ";
                    et_nrgp.setText(NRGP.get(position));
                    strNrgp = NRGP.get(position);
                    if (position != 0) strTemplateId = "" + TemplateId.get(position - 1);
                    else strTemplateId = " ";
                }
                break;
            case R.id.sp_nrgp_item:
                et_nrgp_item.setText(NrgpDetailsItem.get(position));
                if (position != 0) strNrgpItemType = "" + NrgpDetailsItem.get(position - 1);
                else strNrgpItemType = " ";
                et_nrgp_sub_item.setText(NrgpSubbItemName.get(position));
                if (position != 0) strNrgpItem = "" + NrgpDetailsSubItem.get(position - 1);
                else strNrgpItem = " ";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void saveItemConsumption(String Arayname) {
         macId = et_mac_id.getText().toString();
         quantity = et_quantity.getText().toString();
         serialNumber = et_serial_number.getText().toString();
         strNrgp = et_nrgp.getText().toString();
         strNrgpItem = et_nrgp_item.getText().toString();
        if(strNrgpItem.equals("Select NRGP Item")){
            strNrgpItem="";
        }
        strNrgpSerial = et_serial_number.getText().toString();

        JsonObject test=new JsonObject();
        JsonArray item =new JsonArray();
        JsonArray equipment = new JsonArray();
        JsonObject subitem = new JsonObject();
        JsonObject subequipment = new JsonObject();
        test.addProperty("Authkey", Constants.AUTH_KEY);
        test.addProperty("Action", Constants.CREATE_ITEM_EQUIPMENT);
        test.addProperty("srNumber", SrNumber);
        test.addProperty("canId", canId);
        if(Arayname.equals("Item"))
        {
            subitem.addProperty("ItemType",str_itemType);
            subitem.addProperty("SerialNumber", serialNumber);
            subitem.addProperty("Quantity",quantity );
            subitem.addProperty("Item",strItem );
            subitem.addProperty("SubItem",strSubItem );
            subitem.addProperty("ConsumptionType","111260002" );
            subitem.addProperty("MacId", macId);
            subitem.addProperty("NRGPSerialNumber",strNrgpSerial);
            subitem.addProperty("NRGP",strNrgp);
            subitem.addProperty("NRGPItem",strNrgpItem);
            subitem.addProperty("TemplateID",strTemplateId);
            item.add(subitem);
        }else{
            subequipment.addProperty("ItemType",str_itemType);
            subequipment.addProperty("SerialNumber", serialNumber);
            subequipment.addProperty("Quantity",quantity );
            subequipment.addProperty("Item",strItem );
            subequipment.addProperty("SubItem",strSubItem );
            subequipment.addProperty("ConsumptionType","");
            subequipment.addProperty("MacId", macId);
            subequipment.addProperty("NRGPSerialNumber",strNrgpSerial);
            subequipment.addProperty("NRGP",strNrgp);
            subequipment.addProperty("NRGPItem",strNrgpItem);
            subequipment.addProperty("TemplateID",strTemplateId);
            equipment.add(subequipment);
        }
        test.add("item",item);
        test.add("equipment",equipment);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ItemResponse> call = apiService.addItemConsumption(test);
        call.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(@NonNull Call<ItemResponse> call, @NonNull Response<ItemResponse> response) {
                int status = Objects.requireNonNull(response.body()).getStatus();
                if (status==1) {
                    AddMaterial();
                   /* Intent i = new Intent(getActivity(), Activity_Resolve.class);
                    i.putExtra("SrNumber", SrNumber);
                    i.putExtra("CustomerId", canId);
                    i.putExtra("SlotType",strSlotType);
                    i.putExtra("SubSubType",StrSubSubType);
                    i.putExtra("customerNetworkTech",customerNetworkTech);*/
                    Toast.makeText(getActivity(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                   /* startActivity(i);
                    requireActivity().finish();*/
                }else if(status==0){
                   // Toast.makeText(getActivity(),"Object reference not set to an instance of an object.",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ItemResponse> call, @NonNull Throwable t) {
                //  handleVisOfNetworkAndProgress(View.GONE, View.GONE);
            }
        });
    }

    private void getItemNrgpDetails() {
        AssignmentRequest assignmentRequest = new AssignmentRequest();
        assignmentRequest.setAuthkey(Constants.AUTH_KEY);
        assignmentRequest.setAction(Constants.GET_ALL_ITEM_MASTER);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<NrgpDetails> call = apiService.getNrgpData(assignmentRequest);
        call.enqueue(new Callback<NrgpDetails>() {
            @Override
            public void onResponse(@NonNull Call<NrgpDetails> call, @NonNull Response<NrgpDetails> response) {
                int status = Objects.requireNonNull(response.body()).getStatus();
                if (status==1) {
                    NrgpDetailsList = response.body().getResponse();
                    NrgpDetailsItem = new ArrayList<>();
                    NrgpDetailsSubItem = new ArrayList<>();
                    NrgpSubbItemName = new ArrayList<>();
                    NrgpDetailsItem.add("Select NRGP Item");
                    NrgpSubbItemName.add("Select NRGP SubItem");
                    for (NrgpDetails.Response nrgplist : NrgpDetailsList)
                        NrgpDetailsItem.add(nrgplist.getItemName());
                    for(NrgpDetails.Response nrgpSubItem : NrgpDetailsList)
                        NrgpDetailsSubItem.add(nrgpSubItem.getSubItemCode());
                    for(NrgpDetails.Response nrgpSubItem : NrgpDetailsList)
                        NrgpSubbItemName.add(nrgpSubItem.getSubItemName());
                }
                if(getActivity()!=null) {
                    SpinnerAdapter spinnerAdapter11 = new SpinnerAdapter(requireActivity(), R.layout.spinner_item, R.id.tv_item_name, NrgpDetailsItem);
                    sp_nrgp_item.setAdapter(spinnerAdapter11);
                }
            }

            @Override
            public void onFailure(@NonNull Call<NrgpDetails> call, @NonNull Throwable t) {
                //  handleVisOfNetworkAndProgress(View.GONE, View.GONE);
            }
        });
    }


    public void getItemConsumptionDetails(String strType) {
        sp_consumption_type.setOnItemSelectedListener(this);
        sp_item.setOnItemSelectedListener(this);
        sp_sub_item.setOnItemSelectedListener(this);
        sp_item_type.setOnItemSelectedListener(this);


        GetItemConsumptionRequest comsumptionDetails = new GetItemConsumptionRequest(Constants.GETALL_ITEM_MASTER,Constants.AUTH_KEY,SrNumber,canId,"true");
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ItemConsumptionDetails> call = apiService.getItemConsumptionDetails(comsumptionDetails);
        call.enqueue(new Callback<ItemConsumptionDetails>() {
            @Override
            public void onResponse(Call<ItemConsumptionDetails> call, Response<ItemConsumptionDetails> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    try {
                        int status = Objects.requireNonNull(response.body()).getStatus();
                        if (status==1) {
                            itemlist = response.body().getResponse().getItem();
                            equipmentresponselist = response.body().getResponse().getEquipment();
                            item = new ArrayList<>();
                            subItem = new ArrayList<>();
                            ItemID = new ArrayList<>();
                            SubItemID = new ArrayList<>();
                            MaxCap = new ArrayList<>();
                            ItemType = new ArrayList<>();
                            NRGP = new ArrayList<>();
                            TemplateId = new ArrayList<>();
                            item.add("Select Item");
                            subItem.add("Select Sub Item");
                            ItemType.add("Select Item Type");
                            if(strType.equals("Item")) {
                                if (itemlist != null) {
                                    for (ItemConsumptionDetails.Item itemName : itemlist)
                                        item.add(itemName.getItemName());
                                    for (ItemConsumptionDetails.Item sub_item : itemlist)
                                        subItem.add(sub_item.getSub_item_name());
                                    for (ItemConsumptionDetails.Item itemId : itemlist)
                                        ItemID.add(itemId.getItemCode());
                                    for (ItemConsumptionDetails.Item sub_itemId : itemlist)
                                        SubItemID.add(sub_itemId.getSubItemCode());
                                    for (ItemConsumptionDetails.Item maxacapp : itemlist)
                                        MaxCap.add(maxacapp.getMaxcap());
                                    for(ItemConsumptionDetails.Item itemType: itemlist)
                                        ItemType.add(itemType.getItemType());
                                    for(ItemConsumptionDetails.Item itemType: itemlist)
                                        NRGP.add(itemType.getNRGP());
                                    for(ItemConsumptionDetails.Item templateId : itemlist)
                                        TemplateId.add(templateId.getTemplateId());
                                }
                            }else{
                                if(equipmentresponselist!=null) {
                                    for (ItemConsumptionDetails.Equipment itemName : equipmentresponselist)
                                        item.add(itemName.getItemName());
                                    for (ItemConsumptionDetails.Equipment sub_item : equipmentresponselist)
                                        subItem.add(sub_item.getSub_item_name());
                                    for (ItemConsumptionDetails.Equipment itemId : equipmentresponselist)
                                        ItemID.add(itemId.getItemCode());
                                    for (ItemConsumptionDetails.Equipment sub_itemId : equipmentresponselist)
                                        SubItemID.add(sub_itemId.getSubItemCode());
                                    for (ItemConsumptionDetails.Equipment maxxcapp : equipmentresponselist)
                                        MaxCap.add(maxxcapp.getMaxcap());
                                    for(ItemConsumptionDetails.Equipment itemType1: equipmentresponselist)
                                        ItemType.add(itemType1.getItemType());
                                    for(ItemConsumptionDetails.Equipment nrgp : equipmentresponselist)
                                        NRGP.add(nrgp.getNRGP());
                                    for(ItemConsumptionDetails.Equipment templateId : equipmentresponselist)
                                        TemplateId.add(templateId.getTemplateId());
                                }
                            }
                            if(getActivity()!=null) {
                                SpinnerAdapter spinnerAdapter1 = new SpinnerAdapter(requireActivity(), R.layout.spinner_item, R.id.tv_item_name, item);
                                sp_item.setAdapter(spinnerAdapter1);
                            }
                            getItemNrgpDetails();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ItemConsumptionDetails> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }

    private void AddMaterial(){
        Bundle bundle_consumption =new Bundle();
        bundle_consumption.putString("CustomerId",canId );
        bundle_consumption.putString("SrNumber", SrNumber);
        bundle_consumption.putString("SlotType",strSlotType);
        bundle_consumption.putString("SubSubType",StrSubSubType);
        bundle_consumption.putString("customerNetworkTech",customerNetworkTech);
        @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t1= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
        ItemConsumptionDetailsAddFragment itemConsumptionDetailsAddFragment = new ItemConsumptionDetailsAddFragment();
        itemConsumptionDetailsAddFragment.setArguments(bundle_consumption);
        t1.replace(R.id.fregment_container, itemConsumptionDetailsAddFragment);
        t1.commit();
    }


}
