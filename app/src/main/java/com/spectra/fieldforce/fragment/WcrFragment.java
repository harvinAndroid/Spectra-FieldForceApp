package com.spectra.fieldforce.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.spectra.fieldforce.BuildConfig;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.activity.MainActivity;
import com.spectra.fieldforce.activity.ProvisioningMainActivity;
import com.spectra.fieldforce.adapter.WcrAddManholeAdapter;
import com.spectra.fieldforce.adapter.WcrCompletteItemConsumptionListAdapter;
import com.spectra.fieldforce.adapter.WcrEquipmentConsumpAdapter;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.databinding.WcrFragmentBinding;
import com.spectra.fieldforce.model.CommonResponse;
import com.spectra.fieldforce.model.ItemConsumption.NrgpDetails;
import com.spectra.fieldforce.model.gpon.request.AccountInfoRequest;
import com.spectra.fieldforce.model.gpon.request.AssociatedResquest;
import com.spectra.fieldforce.model.gpon.request.HoldWcrRequest;
import com.spectra.fieldforce.model.gpon.request.SubmitApprovalRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateCustomerNetwork;
import com.spectra.fieldforce.model.gpon.request.UpdateFmsRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateQualityParamRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateWcrEnggRequest;
import com.spectra.fieldforce.model.gpon.request.WcrCompleteRequest;
import com.spectra.fieldforce.model.gpon.response.CommonClassResponse;
import com.spectra.fieldforce.model.gpon.response.GetFibreCable;
import com.spectra.fieldforce.model.gpon.response.GetFmsListResponse;
import com.spectra.fieldforce.model.gpon.response.WCRHoldCategoryResponse;
import com.spectra.fieldforce.model.gpon.response.WcrResponse;
import com.spectra.fieldforce.utils.Constants;
import com.spectra.fieldforce.utils.FilePath;
import com.spectra.fieldforce.utils.FileUtils;
import com.spectra.fieldforce.utils.PermissionUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.spectra.fieldforce.utils.AppConstants.PERMISSION_REQUEST_CODE_READ_WRITE;
import static com.spectra.fieldforce.utils.AppConstants.REQUEST_CAMERA_PERMISSION_ONE;
import static com.spectra.fieldforce.utils.AppConstants.REQUEST_CODE_ONE;
import static com.spectra.fieldforce.utils.AppConstants.REQUEST_CODE_READ_WRITE_CAMERA_PERMISSION;

public class WcrFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener{
    private WcrFragmentBinding binding;
    private ArrayList<String> FmsType;
    private ArrayList<String> FmsId;
    private List<GetFmsListResponse.Fms> fmsList;
    private ArrayList<String> fmsName;
    private ArrayList<String> firstFmsID;
    private ArrayList<String> QualityParam;
    private ArrayList<String> QualityParamCable;
    private ArrayList<String> QualityParamLogin;
    private ArrayList<String> QualityParamCustomer;
    private ArrayList<String> QualityParamSpeed;
    private ArrayList<String> QualityParamEducation;
    private ArrayList<String> QualityParamWifi;
    private ArrayList<String> QualityParamFace;
    private ArrayList<String> holdCategory;
    private ArrayList<String> holdCategoryId;
    private String strGuuId,strSegment, strHold,doa,strInsta,strfmsId,strSecFmsId,strCanId ,strholdId,strProductSegment,straddition,OrderId,StatusOfReport;
    private ArrayList<String> itemType;
    private ArrayList<WcrResponse.ManHoleDetails> manHoleDetails;
    private ArrayList<WcrResponse.ItemConsumtion> itemConsumtions;
    private ArrayList<WcrResponse.EquipmentDetailsList> equipmentDetailsLists;
    private AlphaAnimation inAnimation,outAnimation;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adaptersecond;
    private String filepath,filepath1,filepath2,filepath3,str_ext1="",str_ext2="",str_ext3="",str_ext4="",strSlotType,currentImagePath;
    private Uri uri,uri1,uri2,uri3;
    private Uri cameraFileUri;
    private Bitmap bitmap1,bitmap2,bitmap3,bitmap4,bitmap5,bitmap6,bitmap7,bitmap8;
    LocationManager locationManager;
    String latitude, longitude;
    private static final int REQUEST_LOCATION = 1;
   // ArrayAdapter<String> adapterParam;
    ArrayAdapter<String> adapterParamCable;
    ArrayAdapter<String> adapterParamLogin;
    ArrayAdapter<String> adapterParamCustomer;
    ArrayAdapter<String> adapterParamSpeed;
    ArrayAdapter<String> adapterParamEducation;
    ArrayAdapter<String> adapterParamWifi;
    ArrayAdapter<String> adapterParamFace;
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;

    public WcrFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = WcrFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.rl_back) {
           nextScreen();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.searchtoolbar.rlBack.setOnClickListener(this);
        binding.searchtoolbar.tvLang.setText("WCR");
        strCanId = requireArguments().getString("canId");
        StatusOfReport = requireArguments().getString("StatusofReport");
        OrderId = requireArguments().getString("OrderId");


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        init();
        initOne();
    }

    private void initOne(){
        getWcrInfo();
        getFmsList();
        Type();
        ActivityCompat.requestPermissions( getActivity(),
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        QualityParamCable = new ArrayList<String>();
        QualityParamLogin = new ArrayList<String>();
        QualityParamCustomer = new ArrayList<String>();
        QualityParamSpeed = new ArrayList<String>();
        QualityParamEducation = new ArrayList<String>();
        QualityParamWifi = new ArrayList<String>();
        QualityParamFace = new ArrayList<String>();
      /*  QualityParamCable = new ArrayList<String>();
        QualityParamCable.add("Select Type");
        QualityParamCable.add("Yes");
        QualityParamCable.add("No");
        adapterParamCable = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamCable);
        adapterParamCable.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        QualityParamLogin = new ArrayList<String>();
        QualityParamLogin.add("Select Type");
        QualityParamLogin.add("Yes");
        QualityParamLogin.add("No");
        adapterParamLogin = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamLogin);
        adapterParamLogin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        QualityParamCustomer = new ArrayList<String>();
        QualityParamCustomer.add("Select Type");
        QualityParamCustomer.add("Yes");
        QualityParamCustomer.add("No");
        adapterParamCustomer = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamCustomer);
        adapterParamCustomer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        QualityParamSpeed = new ArrayList<String>();
        QualityParamSpeed.add("Select Type");
        QualityParamSpeed.add("Yes");
        QualityParamSpeed.add("No");
        adapterParamSpeed = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamSpeed);
        adapterParamSpeed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        QualityParamEducation = new ArrayList<String>();
        QualityParamEducation.add("Select Type");
        QualityParamEducation.add("Yes");
        QualityParamEducation.add("No");
        adapterParamEducation = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamEducation);
        adapterParamEducation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        QualityParamWifi = new ArrayList<String>();
        QualityParamWifi.add("Select Type");
        QualityParamWifi.add("Yes");
        QualityParamWifi.add("No");
        adapterParamWifi= new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamWifi);
        adapterParamWifi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        QualityParamFace = new ArrayList<String>();
        QualityParamFace.add("Select Type");
        QualityParamFace.add("Yes");
        QualityParamFace.add("No");
        adapterParamFace= new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamFace);
        adapterParamFace.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/


        binding.layoutItemConsumption.btnItemConsumption1.setOnClickListener(v -> {
            @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t1= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
            WcrItemConsumption wcrItemConsumption = new WcrItemConsumption();
            Bundle bundle = new Bundle();
            bundle.putString("canId", strCanId);
            bundle.putString("strGuuId", strGuuId);
            bundle.putString("StatusofReport", StatusOfReport);
            bundle.putString("OrderId", OrderId);
            t1.replace(R.id.frag_container, wcrItemConsumption);
            wcrItemConsumption.setArguments(bundle);
            t1.commit();
        });


        binding.layoutCustomerNetwork.tvCustSave.setOnClickListener(v -> {
            String rxpower,speedlan,speedwifi,wifissd,txpower;
            rxpower = Objects.requireNonNull(binding.layoutCustomerNetwork.etRxPower.getText()).toString();
            speedlan = Objects.requireNonNull(binding.layoutCustomerNetwork.etSpeedLan.getText()).toString();
            speedwifi = Objects.requireNonNull(binding.layoutCustomerNetwork.etSpeedWifi.getText()).toString();
            wifissd = Objects.requireNonNull(binding.layoutCustomerNetwork.etWifiSsd.getText()).toString();
            txpower = Objects.requireNonNull(binding.layoutCustomerNetwork.etTxPower.getText()).toString();
           if(speedlan.isEmpty()){
                Toast.makeText(getContext(),"Please Enter SpeedLan",Toast.LENGTH_LONG).show();
            }   if(speedwifi.isEmpty()){
                Toast.makeText(getContext(),"Please Enter Speed Wifi",Toast.LENGTH_LONG).show();
            }else if(wifissd.isEmpty()){
                Toast.makeText(getContext(),"Please Enter Wifi SSD",Toast.LENGTH_LONG).show();
            }else{
                updateCustomerNetwork(rxpower,speedlan,speedwifi,wifissd,txpower);
            }
                }
        );

        binding.layoutAssociatedDetails.btSubmitAssociate.setOnClickListener((View v) -> {
            String idb = Objects.requireNonNull(binding.layoutAssociatedDetails.etIdbLength.getText()).toString();
            String link = Objects.requireNonNull(binding.layoutAssociatedDetails.etLinkBudget.getText()).toString();

            if(idb.isEmpty()){
                Toast.makeText(getContext(),"Please Enter IDB Length",Toast.LENGTH_LONG).show();
            }else if(link.isEmpty()){
                Toast.makeText(getContext(),"Please Enter Link Budget",Toast.LENGTH_LONG).show();
            }else{
                updateAssociateDetails();
            }
            }
        );


        binding.layoutAddEquipment.btnItemEqipment.setOnClickListener(v -> {
            @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t1= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
            WcrEquipmentConsumption wcrEquipmentConsumption = new WcrEquipmentConsumption();
            Bundle bundle = new Bundle();
            bundle.putString("strGuuId", strGuuId);
            bundle.putString("canId",strCanId);
            bundle.putString("StatusofReport", StatusOfReport);
            bundle.putString("OrderId", OrderId);

            t1.replace(R.id.frag_container, wcrEquipmentConsumption);
            wcrEquipmentConsumption.setArguments(bundle);
            t1.commit();
        });

        binding.layoutWcrFms.btSubmitFmsDetails.setOnClickListener(v -> {
            String custEnd = Objects.requireNonNull(binding.layoutWcrFms.etCustomerEndFmsSec.getText()).toString();
            String custEndFms = Objects.requireNonNull(binding.layoutWcrFms.etPodEnd.getText()).toString();
            String PortNumCx = Objects.requireNonNull(binding.layoutWcrFms.etPortNumCx.getText()).toString();
            String PortnumEnd = Objects.requireNonNull(binding.layoutWcrFms.etPortnumEnd.getText()).toString();
            if(Objects.requireNonNull(binding.layoutWcrFms.etCustomerEndFms.getText()).toString().equals("Select Fms Type")){
                Toast.makeText(getContext(), "Please Select Fms Type", Toast.LENGTH_LONG).show();
            } else if (custEnd.equals("Select Customer End FMS(Second Level)")||custEnd.equals("")) {
                        Toast.makeText(getContext(), "Please Select Customer End FMS(Second Level)", Toast.LENGTH_LONG).show();
                    } else if (custEndFms.equals("POD End FMS No.:")||custEndFms.equals("")) {
                        Toast.makeText(getContext(), "Please Enter Pod End Fms", Toast.LENGTH_LONG).show();
                    } else if (PortNumCx.equals("Port Number (Cx End)")||PortNumCx.equals("")) {
                        Toast.makeText(getContext(), "Please Enter Port Number CX End", Toast.LENGTH_LONG).show();
                    } else if (PortnumEnd.equals("Port Number (POD End)")||PortnumEnd.equals("")) {
                        Toast.makeText(getContext(), "Please Enter Port Number Pod End", Toast.LENGTH_LONG).show();
                    } else {
                        updateFmsDetails();
                    }
                }
        );
        binding.layoutmanholDetails.btnAddManhole.setOnClickListener(v -> {
            @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t1= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
            WcrAddManholeFragment wcrAddManholeFragment = new WcrAddManholeFragment();
            Bundle bundle = new Bundle();
            bundle.putString("canId", strCanId);
            bundle.putString("strGuuId", strGuuId);
            bundle.putString("StatusofReport", StatusOfReport);
            bundle.putString("OrderId", OrderId);

            wcrAddManholeFragment.setArguments(bundle);
            t1.replace(R.id.frag_container, wcrAddManholeFragment);
            t1.commit();
        });

        binding.tvWcrSave.setOnClickListener(v -> {
                    String remark = Objects.requireNonNull(binding.etRemarksText.getText()).toString();
                    if (remark.isEmpty()) {
                        Toast.makeText(getContext(), "Please Enter The Remark", Toast.LENGTH_LONG).show();
                    } else {
                        try {
                                getLastLocation();

                                if (strSegment.equals("Business")) {
                                    if (manHoleDetails.size() == 0 || manHoleDetails == null) {
                                        Toast.makeText(getContext(), "Please Add Manhole", Toast.LENGTH_LONG).show();
                                    } else if (itemConsumtions.size() == 0 || itemConsumtions == null) {
                                        Toast.makeText(getContext(), "Please Add ItemConsumption", Toast.LENGTH_LONG).show();
                                    } else {
                                        updateWcrComplete(remark,latitude,longitude);
                                    }
                                } else if (strSegment.equals("Home")) {
                                    if (equipmentDetailsLists.size() == 0 || equipmentDetailsLists == null) {
                                        Toast.makeText(getContext(), "Please Add Equipment", Toast.LENGTH_LONG).show();
                                    } else if (itemConsumtions.size() == 0 || itemConsumtions == null) {
                                        Toast.makeText(getContext(), "Please Add ItemConsumption", Toast.LENGTH_LONG).show();
                                    } else {
                                        updateWcrComplete(remark,latitude,longitude);
                                    }
                                }
                           // }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
        );
        binding.etAttach.setOnClickListener(view -> {
            requestReadWriteCameraPermission();

            Permission();
           /* Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Fragment frag = this;
            *//** Pass your fragment reference **//*
            frag.startActivityForResult(intent, REQUEST_CAMERA_PERMISSION_ONE);*/
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(cameraIntent.resolveActivity(getContext().getPackageManager())!=null) {
                File imageFile = null;
                try {
                    imageFile = getImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (imageFile != null) {
                    Uri imageUri = FileProvider.getUriForFile(getContext(),
                            BuildConfig.APPLICATION_ID + ".provider", imageFile);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(cameraIntent, REQUEST_CAMERA_PERMISSION_ONE);
                }
            }
            //alertSelectImage(REQUEST_CAMERA_PERMISSION_ONE,REQUEST_CODE_ONE);
        });
        binding.layoutWcrEngrDetails.saveEnggDetails.setOnClickListener((View v) -> {
            strInsta = Objects.requireNonNull(binding.layoutWcrEngrDetails.etInstallationCode.getText()).toString();
            if (strSegment.equals("Business")) {
                updateWcrEnginer(strInsta);
            } else if (strSegment.equals("Home")) {
                if(strInsta.isEmpty()){
                    Toast.makeText(getContext(),"Please Enter Installation Code",Toast.LENGTH_LONG).show();
                }else{
                    updateWcrEnginer(strInsta);
                }
            }


        });
        binding.tvWcrApproval.setOnClickListener(view -> SubmitApproval());
        binding.layoutInstallationparam.tvSaveQualityParam.setOnClickListener((View v) -> {

            String ont = Objects.requireNonNull(binding.layoutInstallationparam.etOntLogin.getText()).toString();
            String face = Objects.requireNonNull(binding.layoutInstallationparam.etFacePlate.getText()).toString();
            String wifi = Objects.requireNonNull(binding.layoutInstallationparam.etWifiSsid.getText()).toString();
            String selfcare = Objects.requireNonNull(binding.layoutInstallationparam.etEducationCustomer.getText()).toString();
            String virus = Objects.requireNonNull(binding.layoutInstallationparam.etEducationAntivirus.getText()).toString();
            String cable = Objects.requireNonNull(binding.layoutInstallationparam.etCableCrimped.getText()).toString();
            String speed = Objects.requireNonNull(binding.layoutInstallationparam.etSpeedTest.getText()).toString();
            if(ont.isEmpty()){
                Toast.makeText(getContext(), "Please Select  ONT login details shared with Customer", Toast.LENGTH_LONG).show();
            }if(face.isEmpty()){
                Toast.makeText(getContext(), "Please Select  Face plate mount to the wall properly", Toast.LENGTH_LONG).show();
            }if(wifi.isEmpty()){
                Toast.makeText(getContext(), "Please Select Wifi", Toast.LENGTH_LONG).show();
            }if(selfcare.isEmpty()){
                Toast.makeText(getContext(), "Please Select  Education customer Regarding Selfcare Portal", Toast.LENGTH_LONG).show();
            }if(virus.isEmpty()){
                Toast.makeText(getContext(), "Please Select Antivirus", Toast.LENGTH_LONG).show();
            }if(cable.isEmpty()){
                Toast.makeText(getContext(), "Please Select Cable_crimped to the wall properly", Toast.LENGTH_LONG).show();
            }if(speed.isEmpty()){
                Toast.makeText(getContext(), "Please Select Speed test results shown to coustomer", Toast.LENGTH_LONG).show();
            }else{
                updateQualityParam(ont,face,wifi,selfcare,virus,cable,speed);
            }
        });
    }




    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                mFusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
                    Location location = task.getResult();
                    if (location == null) {
                        requestNewLocationData();
                    } else {
                        latitude= String.valueOf(location.getLatitude());
                        longitude = String.valueOf((location.getLongitude()));
                    }
                });
            } else {
                Toast.makeText(getActivity(), "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latitude = String.valueOf(mLastLocation.getLatitude());
            longitude = String.valueOf(mLastLocation.getLongitude());
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

       }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }

    private void init(){
        binding.layoutWcrFms.etCustomerEndFms.setOnClickListener(v-> binding.layoutWcrFms.spCustomerEndFms.performClick());
        binding.layoutWcrFms.spCustomerEndFms.setOnItemSelectedListener(this);
        binding.layoutWcrFms.etCustomerEndFmsSec.setOnClickListener(v-> binding.layoutWcrFms.spCustomerEndFmsSec.performClick());
        binding.layoutWcrFms.spCustomerEndFmsSec.setOnItemSelectedListener(this);

        binding.layoutInstallationparam.etOntLogin.setOnClickListener(v-> binding.layoutInstallationparam.spOntLogin.performClick());
        binding.layoutInstallationparam.spOntLogin.setOnItemSelectedListener(this);
        binding.layoutInstallationparam.etFacePlate.setOnClickListener(v-> binding.layoutInstallationparam.spFacePlate.performClick());
        binding.layoutInstallationparam.spFacePlate.setOnItemSelectedListener(this);
        binding.layoutInstallationparam.etWifiSsid.setOnClickListener(v-> binding.layoutInstallationparam.spWifiSsid.performClick());
        binding.layoutInstallationparam.spWifiSsid.setOnItemSelectedListener(this);
        binding.layoutInstallationparam.etSpeedTest.setOnClickListener(v-> binding.layoutInstallationparam.spSpeedTest.performClick());
        binding.layoutInstallationparam.spSpeedTest.setOnItemSelectedListener(this);
        binding.layoutInstallationparam.etEducationAntivirus.setOnClickListener(v-> binding.layoutInstallationparam.spEducationAntivirus.performClick());
        binding.layoutInstallationparam.spEducationAntivirus.setOnItemSelectedListener(this);
        binding.layoutInstallationparam.etCableCrimped.setOnClickListener(v-> binding.layoutInstallationparam.spCableCrimped.performClick());
        binding.layoutInstallationparam.spCableCrimped.setOnItemSelectedListener(this);
        binding.layoutInstallationparam.etEducationCustomer.setOnClickListener(v-> binding.layoutInstallationparam.spEducationCustomer.performClick());
        binding.layoutInstallationparam.spEducationCustomer.setOnItemSelectedListener(this);

        binding.etHoldCategory.setOnClickListener(v-> binding.spHoldCategory.performClick());
        binding.spHoldCategory.setOnItemSelectedListener(this);
        binding.linearFive.setVisibility(View.VISIBLE);
        binding.linearFour.setOnClickListener(v -> {
            binding.linearFive.setVisibility(View.VISIBLE);
            binding.linearTen.setVisibility(View.GONE);
            binding.linearEight.setVisibility(View.GONE);
            binding.linear12.setVisibility(View.GONE);
            binding.linear14.setVisibility(View.GONE);
            binding.linear16.setVisibility(View.GONE);
            binding.linear19.setVisibility(View.GONE);
            binding.linear21.setVisibility(View.GONE);
            binding.linearEqipmentdetails.setVisibility(View.GONE);
            binding.linearCustomernetworkDetails.setVisibility(View.GONE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
        });
        binding.linearNine.setOnClickListener(v -> {
            binding.linearFive.setVisibility(View.GONE);
            binding.linearTen.setVisibility(View.VISIBLE);
            binding.linearEight.setVisibility(View.GONE);
            binding.linear12.setVisibility(View.GONE);
            binding.linear14.setVisibility(View.GONE);
            binding.linear16.setVisibility(View.GONE);
            binding.linear19.setVisibility(View.GONE);
            binding.linear21.setVisibility(View.GONE);
            binding.linearEqipmentdetails.setVisibility(View.GONE);
            binding.linearCustomernetworkDetails.setVisibility(View.GONE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
        });
        binding.linearSix.setOnClickListener(v -> {
            binding.linearFive.setVisibility(View.GONE);
            binding.linearTen.setVisibility(View.GONE);
            binding.linearEight.setVisibility(View.VISIBLE);
            binding.linear12.setVisibility(View.GONE);
            binding.linear14.setVisibility(View.GONE);
            binding.linear16.setVisibility(View.GONE);
            binding.linear19.setVisibility(View.GONE);
            binding.linear21.setVisibility(View.GONE);
            binding.linearEqipmentdetails.setVisibility(View.GONE);
            binding.linearCustomernetworkDetails.setVisibility(View.GONE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
        });
        binding.linea11.setOnClickListener(v -> {
            binding.linearFive.setVisibility(View.GONE);
            binding.linearTen.setVisibility(View.GONE);
            binding.linearEight.setVisibility(View.GONE);
            binding.linear12.setVisibility(View.VISIBLE);
            binding.linear14.setVisibility(View.GONE);
            binding.linear16.setVisibility(View.GONE);
            binding.linear19.setVisibility(View.GONE);
            binding.linear21.setVisibility(View.GONE);
            binding.linearEqipmentdetails.setVisibility(View.GONE);
            binding.linearCustomernetworkDetails.setVisibility(View.GONE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
        });
        binding.linea13.setOnClickListener(v -> {
            binding.linearFive.setVisibility(View.GONE);
            binding.linearTen.setVisibility(View.GONE);
            binding.linearEight.setVisibility(View.GONE);
            binding.linear12.setVisibility(View.GONE);
            binding.linear14.setVisibility(View.VISIBLE);
            binding.linear16.setVisibility(View.GONE);
            binding.linear19.setVisibility(View.GONE);
            binding.linear21.setVisibility(View.GONE);
            binding.linearEqipmentdetails.setVisibility(View.GONE);
            binding.linearCustomernetworkDetails.setVisibility(View.GONE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
        });
        binding.linea15.setOnClickListener(v -> {
            binding.linearFive.setVisibility(View.GONE);
            binding.linearTen.setVisibility(View.GONE);
            binding.linearEight.setVisibility(View.GONE);
            binding.linear12.setVisibility(View.GONE);
            binding.linear14.setVisibility(View.GONE);
            binding.linear16.setVisibility(View.VISIBLE);
            binding.linear19.setVisibility(View.GONE);
            binding.linear21.setVisibility(View.GONE);
            binding.linearEqipmentdetails.setVisibility(View.GONE);
            binding.linearCustomernetworkDetails.setVisibility(View.GONE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
        });
        binding.linea18.setOnClickListener(v -> {
            binding.linearFive.setVisibility(View.GONE);
            binding.linearTen.setVisibility(View.GONE);
            binding.linearEight.setVisibility(View.GONE);
            binding.linear12.setVisibility(View.GONE);
            binding.linear14.setVisibility(View.GONE);
            binding.linear16.setVisibility(View.GONE);
            binding.linear19.setVisibility(View.VISIBLE);
            binding.linear21.setVisibility(View.GONE);
            binding.linearEqipmentdetails.setVisibility(View.GONE);
            binding.linearCustomernetworkDetails.setVisibility(View.GONE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
        });
        binding.linea20.setOnClickListener(v -> {
            binding.linearFive.setVisibility(View.GONE);
            binding.linearTen.setVisibility(View.GONE);
            binding.linearEight.setVisibility(View.GONE);
            binding.linear12.setVisibility(View.GONE);
            binding.linear14.setVisibility(View.GONE);
            binding.linear16.setVisibility(View.GONE);
            binding.linear19.setVisibility(View.GONE);
            binding.linear21.setVisibility(View.VISIBLE);
            binding.linearEqipmentdetails.setVisibility(View.GONE);
            binding.linearCustomernetworkDetails.setVisibility(View.GONE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
        });
        binding.linearEquipment.setOnClickListener(v -> {
            binding.linearFive.setVisibility(View.GONE);
            binding.linearTen.setVisibility(View.GONE);
            binding.linearEight.setVisibility(View.GONE);
            binding.linear12.setVisibility(View.GONE);
            binding.linear14.setVisibility(View.GONE);
            binding.linear16.setVisibility(View.GONE);
            binding.linear19.setVisibility(View.GONE);
            binding.linear21.setVisibility(View.GONE);
            binding.linearEqipmentdetails.setVisibility(View.VISIBLE);
            binding.linearCustomernetworkDetails.setVisibility(View.GONE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
        });
        binding.linearCustomerNetwork.setOnClickListener(v -> {
            binding.linearFive.setVisibility(View.GONE);
            binding.linearTen.setVisibility(View.GONE);
            binding.linearEight.setVisibility(View.GONE);
            binding.linear12.setVisibility(View.GONE);
            binding.linear14.setVisibility(View.GONE);
            binding.linear16.setVisibility(View.GONE);
            binding.linear19.setVisibility(View.GONE);
            binding.linear21.setVisibility(View.GONE);
            binding.linearEqipmentdetails.setVisibility(View.GONE);
            binding.linearCustomernetworkDetails.setVisibility(View.VISIBLE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
        });
        binding.linearInstallationParam.setOnClickListener(v -> {
            binding.linearFive.setVisibility(View.GONE);
            binding.linearTen.setVisibility(View.GONE);
            binding.linearEight.setVisibility(View.GONE);
            binding.linear12.setVisibility(View.GONE);
            binding.linear14.setVisibility(View.GONE);
            binding.linear16.setVisibility(View.GONE);
            binding.linear19.setVisibility(View.GONE);
            binding.linear21.setVisibility(View.GONE);
            binding.linearEqipmentdetails.setVisibility(View.GONE);
            binding.linearCustomernetworkDetails.setVisibility(View.GONE);
            binding.linearInstallationParamDetails.setVisibility(View.VISIBLE);
        });
    }

    private void listener(){
        if(strSegment.equals("Business")){
            binding.linearFour.setVisibility(View.VISIBLE);
            binding.linearNine.setVisibility(View.VISIBLE);
            binding.linearSix.setVisibility(View.VISIBLE);
            binding.linea11.setVisibility(View.VISIBLE);
            binding.linea13.setVisibility(View.VISIBLE);
            binding.linea15.setVisibility(View.VISIBLE);
            binding.linea18.setVisibility(View.GONE);
            binding.tvWcrApproval.setVisibility(View.GONE);
            binding.tvWcrApproval.setVisibility(View.GONE);
            binding.linearEquipment.setVisibility(View.GONE);
            binding.linearCustomerNetwork.setVisibility(View.GONE);
            binding.linearInstallationParam.setVisibility(View.GONE);
            binding.linea20.setVisibility(View.VISIBLE);
            binding.layoutWcrEngrDetails.etInstallationCode.setVisibility(View.GONE);
        }else if(strSegment.equals("Home")){
            binding.linearFour.setVisibility(View.VISIBLE);
            binding.linea11.setVisibility(View.VISIBLE);
            binding.linearEquipment.setVisibility(View.VISIBLE);
            binding.linearCustomerNetwork.setVisibility(View.VISIBLE);
            binding.linearInstallationParam.setVisibility(View.VISIBLE);
            binding.linearNine.setVisibility(View.GONE);
            binding.linearSix.setVisibility(View.GONE);
            binding.linea13.setVisibility(View.GONE);
            binding.linea15.setVisibility(View.VISIBLE);
            //binding.linea18.setVisibility(View.VISIBLE);
            binding.linea20.setVisibility(View.VISIBLE);
        }
    }

   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_ONE) {

                Bitmap photo = (Bitmap) data.getExtras().get("data");
             //   imageview.setImageBitmap(photo);
                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri selectedImage = getImageUri(getActivity(), photo);
                String realPath=getRealPathFromURI(selectedImage);
                selectedImage = Uri.parse(realPath);
                Toast.makeText(getContext(),String.valueOf(selectedImage),Toast.LENGTH_LONG).show();
            }
        }
    }
*/
  /*  public String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = getActivity().getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }*/

    public void getFmsList() {
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        AccountInfoRequest accountInfoRequest = new AccountInfoRequest();
        accountInfoRequest.setAuthkey(Constants.AUTH_KEY);
        accountInfoRequest.setAction(Constants.GET_FMS_LIST);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GetFmsListResponse> call = apiService.getFmsList(accountInfoRequest);
        call.enqueue(new Callback<GetFmsListResponse>() {
            @Override
            public void onResponse(Call<GetFmsListResponse> call, Response<GetFmsListResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    outAnimation = new AlphaAnimation(1f, 0f);
                    outAnimation.setDuration(200);
                    binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                    binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                    try {
                        fmsList = response.body().getResponse().getFMSList();
                        firstFmsID = new ArrayList<>();
                        fmsName = new ArrayList<>();
                        fmsName.add("Select Customer End FMS(Second Level)");
                        for (GetFmsListResponse.Fms fms : fmsList)
                            fmsName.add(fms.getFms());
                        for (GetFmsListResponse.Fms fmss : fmsList)
                            firstFmsID.add(fmss.getId());
                        adaptersecond = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, fmsName);
                        adaptersecond.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.layoutWcrFms.spCustomerEndFmsSec.setAdapter(adaptersecond);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<GetFmsListResponse> call, Throwable t) {
                binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                Log.e("RetroError", t.toString());
            }
        });
    }



    private void Type() {
        FmsType = new ArrayList<String>();
        FmsType.add("Select Fms Type");
        FmsType.add("WallMount");
        FmsType.add("RackMount");
        FmsId = new ArrayList<String>();
        FmsId.add("569480000");
        FmsId.add("569480001");
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, FmsType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.layoutWcrFms.spCustomerEndFms.setAdapter(adapter);

        holdCategory = new ArrayList<String>();
        holdCategory.add("Hold Category");
        holdCategory.add("Building Permission Issue");
        holdCategory.add("Customer Site Not Ready");
        holdCategory.add("Delayed By Customer");
        holdCategory.add("Installation Report Pending To Sign");
        holdCategory.add("Local Permission Issue");
        holdCategory.add("Network Constraint");
        holdCategoryId = new ArrayList<String>();
        holdCategoryId.add("1");
        holdCategoryId.add("2");
        holdCategoryId.add("3");
        holdCategoryId.add("4");
        holdCategoryId.add("5");
        holdCategoryId.add("6");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, holdCategory);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spHoldCategory.setAdapter(adapter2);

        binding.saveHold.setOnClickListener(v -> {
            if (binding.spHoldCategory.getSelectedItem().toString().equals("Hold Category")) {
                Toast.makeText(getContext(), "Please Select Hold Category", Toast.LENGTH_LONG).show();
            } else if (Objects.requireNonNull(binding.etHoldReason.getText()).toString().equals("Hold Reason:")) {
                Toast.makeText(getContext(), "Please Enter Hold Reason", Toast.LENGTH_LONG).show();
            }else {
                updateHoldCategory();
                updateHoldCategoryStatus();
            }
          }
        );
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.sp_customer_end_fms) {
            binding.layoutWcrFms.etCustomerEndFms.setText(FmsType.get(position));
            if (position != 0) strfmsId = "" + FmsId.get(position - 1);
            else strfmsId = " ";
        }else if(parent.getId() == R.id.sp_customer_end_fms_sec){
            binding.layoutWcrFms.etCustomerEndFmsSec.setText(fmsName.get(position));
            if (position != 0) strSecFmsId = "" + firstFmsID.get(position - 1);
            else strSecFmsId = " ";
        }else if(parent.getId() == R.id.sp_hold_category){
            binding.etHoldCategory.setText(holdCategory.get(position));
            if (position != 0) strholdId = "" + holdCategoryId.get(position - 1);
            else strholdId = " ";
        }else if(parent.getId() == R.id.sp_speed_test) {
            binding.layoutInstallationparam.etSpeedTest.setText(QualityParamSpeed.get(position));
        }
        else if(parent.getId() == R.id.sp_cable_crimped) {
            binding.layoutInstallationparam.etCableCrimped.setText(QualityParamCable.get(position));
        }
        else if(parent.getId() == R.id.sp_face_plate) {
            binding.layoutInstallationparam.etFacePlate.setText(QualityParamFace.get(position));
        }
        else if(parent.getId() == R.id.sp_ont_login) {
            binding.layoutInstallationparam.etOntLogin.setText(QualityParamLogin.get(position));
        }
        else if(parent.getId() == R.id.sp_wifi_ssid) {
            binding.layoutInstallationparam.etWifiSsid.setText(QualityParamWifi.get(position));
        }
        else if(parent.getId() == R.id.sp_education_customer) {
            binding.layoutInstallationparam.etEducationCustomer.setText(QualityParamCustomer.get(position));
        }
        else if(parent.getId() == R.id.sp_education_antivirus) {
            binding.layoutInstallationparam.etEducationAntivirus.setText(QualityParamEducation.get(position));
        }
       /* else if (parent.getId() == R.id.et_attach) {
            requestReadWriteCameraPermission();

            Permission();
        } else alertSelectImage(REQUEST_CAMERA_PERMISSION_ONE,REQUEST_CODE_ONE);
*/
    }
    public boolean requestReadWriteCameraPermission() {
        return ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void getWcrInfo() {
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        AccountInfoRequest accountInfoRequest = new AccountInfoRequest();
        accountInfoRequest.setAuthkey(Constants.AUTH_KEY);
        accountInfoRequest.setAction(Constants.GET_WCR_INFO);
        accountInfoRequest.setCanId(strCanId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<WcrResponse> call = apiService.getWcrInfo(accountInfoRequest);
        call.enqueue(new Callback<WcrResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<WcrResponse> call, Response<WcrResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    outAnimation = new AlphaAnimation(1f, 0f);
                    outAnimation.setDuration(200);
                    binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                    binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                    if(response.body().getStatus().equals("Success")) {
                        try {
                            binding.tvWcrStatus.setText("WCR Status: " + response.body().getResponse().getWcr().getWCRConsumptionStatus());
                            binding.layoutWcrFragmentCustomerDetails.setCustomer(response.body().getResponse().getWcr());
                            strGuuId = response.body().getResponse().getWcr().getWcrguidid();
                            strHold = response.body().getResponse().getWcr().getShowHold();
                            if(strHold.equals("true")){
                                binding.linea18.setVisibility(View.VISIBLE);
                            }else{
                                binding.linea18.setVisibility(View.GONE);
                            }
                            strProductSegment = response.body().getResponse().getWcr().getProductSegment();
                            strSegment = response.body().getResponse().getWcr().getBusinessSegment();
                            manHoleDetails = response.body().getResponse().getManHoleDetailsList();
                            binding.layoutAssociatedDetails.setAssociated(response.body().getResponse().getAssociated());
                            binding.layoutWcrFms.setFms(response.body().getResponse().getFMSDetails());
                            String strfmsfirst = response.body().getResponse().getFMSDetails().getFmsfirst();
                            if(strfmsfirst!=null && !strfmsfirst.equals("0")){
                                FmsType = new ArrayList<String>();
                                FmsType.add(strfmsfirst);
                                FmsId = new ArrayList<String>();
                                if(strfmsfirst.equals("Wall Mount")){
                                    FmsId.add("569480000");
                                    strfmsId ="569480000";
                                }else if(strfmsfirst.equals("Rack Mount")){
                                    FmsId.add("569480001");
                                    strfmsId ="569480001";
                                }
                                adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, FmsType);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.layoutWcrFms.spCustomerEndFms.setAdapter(adapter);
                            }
                            String strfmssec = response.body().getResponse().getFMSDetails().getFmssecond();
                            if(strfmssec!=null && !strfmssec.equals("0")){
                              //  firstFmsID = new ArrayList<>();
                                fmsName = new ArrayList<>();
                                fmsName.add(strfmssec);
                                adaptersecond = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, fmsName);
                                adaptersecond.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.layoutWcrFms.spCustomerEndFmsSec.setAdapter(adaptersecond);
                            }
                            binding.layoutWcrEngrDetails.setEngg(response.body().getResponse().getEngineerDetails());
                            binding.setHoldCategory(response.body().getResponse().getEngineerDetails());
                            binding.layoutCustomerNetwork.setCustomerNetwork(response.body().getResponse().getCusotmerNetwork());
                            binding.layoutmanholDetails.rvAddManhole.setHasFixedSize(true);
                            binding.layoutmanholDetails.rvAddManhole.setLayoutManager(new LinearLayoutManager(getActivity()));
                            binding.layoutmanholDetails.rvAddManhole.setAdapter(new WcrAddManholeAdapter(getActivity(), manHoleDetails));
                            itemConsumtions = response.body().getResponse().getItemConsumtionList();
                            binding.layoutItemConsumption.rvWcrItemlist.setHasFixedSize(true);
                            binding.layoutItemConsumption.rvWcrItemlist.setLayoutManager(new LinearLayoutManager(getActivity()));
                            binding.layoutItemConsumption.rvWcrItemlist.setAdapter(new WcrCompletteItemConsumptionListAdapter(getActivity(), itemConsumtions));
                            straddition = itemConsumtions.get(0).getItemType();
                            if(straddition.equals("Additional")&& strSegment.equals("Home")){
                                binding.tvWcrApproval.setVisibility(View.VISIBLE);
                            }
                            doa = response.body().getResponse().getWcr().getDOAFlag();
                            if(doa!=null){
                                if(doa.equals("No") && straddition.equals("Additional")){
                                    binding.tvWcrApproval.setVisibility(View.VISIBLE);
                                }
                            }


                            equipmentDetailsLists = response.body().getResponse().getEquipmentDetailsList();
                            binding.layoutAddEquipment.rvAddEquipment.setHasFixedSize(true);
                            binding.layoutAddEquipment.rvAddEquipment.setLayoutManager(new LinearLayoutManager(getActivity()));
                            if(equipmentDetailsLists.size()!=0){
                                binding.layoutAddEquipment.rvAddEquipment.setAdapter(new WcrEquipmentConsumpAdapter(getActivity(),equipmentDetailsLists));
                            }
                            binding.layoutInstallationparam.setQuality(response.body().getResponse().getInstallationQuality());

                            if(response.body().getResponse().getInstallationQuality().getAntiVirus().equals("Yes")){
                                QualityParamEducation.clear();
                                QualityParamEducation.add("Yes");
                                ArrayAdapter<String> adapterParam1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamEducation);
                                adapterParam1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.layoutInstallationparam.spEducationAntivirus.setAdapter(adapterParam1);

                            }else if(response.body().getResponse().getInstallationQuality().getAntiVirus().equals("No")){

                                QualityParamEducation.clear();
                                QualityParamEducation.add("No");
                                ArrayAdapter<String> adapterParam1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamEducation);
                                adapterParam1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.layoutInstallationparam.spEducationAntivirus.setAdapter(adapterParam1);
                            }
                            else {
                                QualityParamEducation.add("Select Type");
                                QualityParamEducation.add("Yes");
                                QualityParamEducation.add("No");
                                ArrayAdapter<String> adapterParam1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamEducation);
                                adapterParam1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.layoutInstallationparam.spEducationAntivirus.setAdapter(adapterParam1);
                            }
                            if(response.body().getResponse().getInstallationQuality().getCable().equals("Yes")){
                                QualityParamCable.clear();
                                QualityParamCable.add("Yes");
                                 adapterParamCable = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamCable);
                                adapterParamCable.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.layoutInstallationparam.spCableCrimped.setAdapter(adapterParamCable);

                            }else  if(response.body().getResponse().getInstallationQuality().getCable().equals("No")){
                                QualityParamCable.clear();
                                QualityParamCable.add("No");
                                adapterParamCable = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamCable);
                                adapterParamCable.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.layoutInstallationparam.spCableCrimped.setAdapter(adapterParamCable);
                            }else {
                                QualityParamCable.add("Select Type");
                                QualityParamCable.add("Yes");
                                QualityParamCable.add("No");
                                adapterParamCable = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamCable);
                                adapterParamCable.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.layoutInstallationparam.spCableCrimped.setAdapter(adapterParamCable);
                            }
                            if(response.body().getResponse().getInstallationQuality().getFace().equals("Yes")){
                                QualityParamFace.clear();
                                QualityParamFace.add("Yes");
                                adapterParamFace = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamFace);
                                adapterParamFace.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.layoutInstallationparam.spFacePlate.setAdapter(adapterParamFace);
                            }else  if(response.body().getResponse().getInstallationQuality().getFace().equals("No")){
                                QualityParamFace.clear();
                                QualityParamFace.add("No");
                                adapterParamFace = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamFace);
                                adapterParamFace.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.layoutInstallationparam.spFacePlate.setAdapter(adapterParamFace);
                            }else{
                                QualityParamFace.add("Select Type");
                                QualityParamFace.add("Yes");
                                QualityParamFace.add("No");
                                adapterParamFace = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamFace);
                                adapterParamFace.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.layoutInstallationparam.spFacePlate.setAdapter(adapterParamFace);
                            }
                            if(response.body().getResponse().getInstallationQuality().getOnt().equals("Yes")){
                                QualityParamLogin.clear();
                                QualityParamLogin.add("Yes");
                                adapterParamLogin = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamLogin);
                                adapterParamLogin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.layoutInstallationparam.spOntLogin.setAdapter(adapterParamLogin);
                            }else  if(response.body().getResponse().getInstallationQuality().getOnt().equals("No")){
                                QualityParamLogin.add("No");
                                adapterParamLogin = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamLogin);
                                adapterParamLogin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.layoutInstallationparam.spOntLogin.setAdapter(adapterParamLogin);
                            }else {
                                QualityParamLogin.add("Select Type");
                                QualityParamLogin.add("Yes");
                                QualityParamLogin.add("No");
                                adapterParamLogin = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamLogin);
                                adapterParamLogin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.layoutInstallationparam.spOntLogin.setAdapter(adapterParamLogin);
                            }
                            if(response.body().getResponse().getInstallationQuality().getSelfCare().equals("Yes")){
                                QualityParamCustomer.clear();
                                QualityParamCustomer.add("Yes");
                                adapterParamCustomer = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamCustomer);
                                adapterParamCustomer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.layoutInstallationparam.spEducationCustomer.setAdapter(adapterParamCustomer);
                            }else  if(response.body().getResponse().getInstallationQuality().getSelfCare().equals("No")){
                                QualityParamCustomer.clear();
                                QualityParamCustomer.add("No");
                                adapterParamCustomer = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamCustomer);
                                adapterParamCustomer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.layoutInstallationparam.spEducationCustomer.setAdapter(adapterParamCustomer);
                            }else{
                                QualityParamCustomer.add("Select Type");
                                QualityParamCustomer.add("Yes");
                                QualityParamCustomer.add("No");
                                adapterParamCustomer = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamCustomer);
                                adapterParamCustomer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.layoutInstallationparam.spEducationCustomer.setAdapter(adapterParamCustomer);
                            }
                            if(response.body().getResponse().getInstallationQuality().getSpeed().equals("Yes")){
                                QualityParamSpeed.clear();
                                QualityParamSpeed.add("Yes");
                                adapterParamSpeed = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamSpeed);
                                adapterParamSpeed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.layoutInstallationparam.spSpeedTest.setAdapter(adapterParamSpeed);
                            }else  if(response.body().getResponse().getInstallationQuality().getSpeed().equals("No")){
                                QualityParamSpeed.clear();
                                QualityParamSpeed.add("No");
                                adapterParamSpeed = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamSpeed);
                                adapterParamSpeed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.layoutInstallationparam.spSpeedTest.setAdapter(adapterParamSpeed);
                            }else {
                                QualityParamSpeed.add("Select Type");
                                QualityParamSpeed.add("Yes");
                                QualityParamSpeed.add("No");
                                adapterParamSpeed = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamSpeed);
                                adapterParamSpeed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.layoutInstallationparam.spSpeedTest.setAdapter(adapterParamSpeed);
                            }
                            if(response.body().getResponse().getInstallationQuality().getWifi().equals("Yes")){
                                QualityParamWifi.clear();
                                QualityParamWifi.add("Yes");
                                adapterParamWifi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamWifi);
                                adapterParamWifi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.layoutInstallationparam.spWifiSsid.setAdapter(adapterParamWifi);
                            }else  if(response.body().getResponse().getInstallationQuality().getWifi().equals("No")){
                                QualityParamWifi.clear();
                                QualityParamWifi.add("No");
                                adapterParamWifi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamWifi);
                                adapterParamWifi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.layoutInstallationparam.spWifiSsid.setAdapter(adapterParamWifi);
                            }else {
                                QualityParamWifi.add("Select Type");
                                QualityParamWifi.add("Yes");
                                QualityParamWifi.add("No");
                                adapterParamWifi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamWifi);
                                adapterParamWifi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.layoutInstallationparam.spWifiSsid.setAdapter(adapterParamWifi);
                            }


                            listener();
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }else if(response.body().getStatus().equals("Failure")){
                        Toast.makeText(getContext(),"No Data Available.",Toast.LENGTH_LONG).show();
                        moveNext();
                    }
                }
            }

            @Override
            public void onFailure(Call<WcrResponse> call, Throwable t) {
                binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                Log.e("RetroError", t.toString());
            }
        });
    }

    private void nextScreen(){
        Intent i = new Intent(getActivity(), ProvisioningMainActivity.class);
        i.putExtra("canId", strCanId);
        i.putExtra("StatusofReport", StatusOfReport);
        i.putExtra("OrderId", OrderId);

        startActivity(i);
        getActivity().finish();
    }



    private void moveNext(){
        @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
        WcrFragment wcrFragment = new WcrFragment();
        Bundle accountinfo = new Bundle();
        accountinfo.putString("canId", strCanId);
        accountinfo.putString("StatusofReport", StatusOfReport);
        accountinfo.putString("OrderId", OrderId);

        t.replace(R.id.frag_container, wcrFragment);
        wcrFragment.setArguments(accountinfo);
        t.commit();
    }

    private void updateAssociateDetails(){
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        AssociatedResquest associatedResquest = new AssociatedResquest();
        associatedResquest.setAuthkey(Constants.AUTH_KEY);
        associatedResquest.setAction(Constants.UPDATE_ASSOCIATE);
        associatedResquest.setIdb(Objects.requireNonNull(binding.layoutAssociatedDetails.etIdbLength.getText()).toString());
        associatedResquest.setLink(Objects.requireNonNull(binding.layoutAssociatedDetails.etLinkBudget.getText()).toString());
        associatedResquest.setWCRguidId(strGuuId);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<CommonClassResponse> call = apiService.updateAssociateDetails(associatedResquest);
            call.enqueue(new Callback<CommonClassResponse>() {
                @Override
                public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                    if (response.isSuccessful()&& response.body()!=null) {
                        outAnimation = new AlphaAnimation(1f, 0f);
                        outAnimation.setDuration(200);
                        binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                        binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                        try {
                        if(response.body().getStatus().equals("Success")){
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                            moveNext();
                        }else{
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<CommonClassResponse> call, Throwable t) {
                    binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                    Log.e("RetroError", t.toString());
                }
            });

    }


    private void updateWcrComplete(String remark,String latitude,String longitude){
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        WcrCompleteRequest wcrCompleteRequest = new WcrCompleteRequest();
        wcrCompleteRequest.setAuthkey(Constants.AUTH_KEY);
        wcrCompleteRequest.setAction(Constants.WCR_COMPLETE);
        wcrCompleteRequest.setIsHold(strholdId);
        wcrCompleteRequest.setProductSegment(strProductSegment);
        wcrCompleteRequest.setSegment(strSegment);
        wcrCompleteRequest.setRemarks(remark);
        wcrCompleteRequest.setWCRguidId(strGuuId);
        wcrCompleteRequest.setLat(latitude);
        wcrCompleteRequest.setLong(longitude);
        wcrCompleteRequest.setSource("App");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.wcrComplete(wcrCompleteRequest);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    outAnimation = new AlphaAnimation(1f, 0f);
                    outAnimation.setDuration(200);
                    binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                    binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                    try {
                        if(response.body().getStatus().equals("Success")){
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                            moveNext();
                        }else{
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<CommonClassResponse> call, Throwable t) {
                binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                Log.e("RetroError", t.toString());
            }
        });

    }

    private void updateCustomerNetwork(String rxpower, String speedlan, String speedwifi, String wifissd, String txpower){
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        UpdateCustomerNetwork updateCustomerNetwork = new UpdateCustomerNetwork();
        updateCustomerNetwork.setAuthkey(Constants.AUTH_KEY);
        updateCustomerNetwork.setAction(Constants.UPDATE_CUSTOMER_NETWORK);
        updateCustomerNetwork.setRxPower(rxpower);
        updateCustomerNetwork.setSpeedLan(speedlan);
        updateCustomerNetwork.setSpeedWifi(speedwifi);
        updateCustomerNetwork.setWifiSSID(wifissd);
        updateCustomerNetwork.setTxPower(txpower);
        updateCustomerNetwork.setWCRguidId(strGuuId);


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.updateCustomerNetwork(updateCustomerNetwork);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    outAnimation = new AlphaAnimation(1f, 0f);
                    outAnimation.setDuration(200);
                    binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                    binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                    try {
                        if(response.body().getStatus().equals("Success")){
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                            nextScreen();
                        }else{
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<CommonClassResponse> call, Throwable t) {
                binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                Log.e("RetroError", t.toString());
            }
        });

    }

    private void updateQualityParam(String ont, String face, String wifi, String selfcare, String virus, String cable, String speed){
        String ont1,face1,wifi1,sel1,virus1,cable1,speed1;
        if(ont.equals("Yes")){
            ont1 =  "111260000";
        }else{
            ont1 = "111260001";
        }
        if(face.equals("Yes")){
            face1 =  "111260000";
        }else{
            face1 = "111260001";
        }
        if(wifi.equals("Yes")){
            wifi1 =  "111260000";
        }else{
            wifi1 = "111260001";
        }
        if(selfcare.equals("Yes")){
            sel1 =  "111260000";
        }else{
            sel1 = "111260001";
        }
        if(virus.equals("Yes")){
            virus1 =  "111260000";
        }else{
            virus1 = "111260001";
        }
        if(cable.equals("Yes")){
            cable1 =  "111260000";
        }else{
            cable1 = "111260001";
        }
        if(speed.equals("Yes")){
            speed1 =  "111260000";
        }else{
            speed1 = "111260001";
        }
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        UpdateQualityParamRequest updateQualityParamRequest = new UpdateQualityParamRequest();
        updateQualityParamRequest.setAuthkey(Constants.AUTH_KEY);
        updateQualityParamRequest.setAction(Constants.UPDATE_POSTHOLD_IRQUALITY);
        updateQualityParamRequest.setONT(ont1);
        updateQualityParamRequest.setFace(face1);
        updateQualityParamRequest.setWifi(wifi1);
        updateQualityParamRequest.setSelfCare(sel1);
        updateQualityParamRequest.setAntiVirus(virus1);
        updateQualityParamRequest.setCable(cable1);
        updateQualityParamRequest.setSpeed(speed1);
        updateQualityParamRequest.setWCRguidId(strGuuId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.updateQualityParamReq(updateQualityParamRequest);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    outAnimation = new AlphaAnimation(1f, 0f);
                    outAnimation.setDuration(200);
                    binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                    binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                    try {
                        if(response.body().getStatus().equals("Success")){
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                            nextScreen();
                        }else{
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<CommonClassResponse> call, Throwable t) {
                binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                Log.e("RetroError", t.toString());
            }
        });

    }

    private void updateFmsDetails(){
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        UpdateFmsRequest updateFmsRequest = new UpdateFmsRequest();
        updateFmsRequest.setAuthkey(Constants.AUTH_KEY);
        updateFmsRequest.setAction(Constants.UPDATE_FMS_DETAILS);
        updateFmsRequest.setFmsFirst(strfmsId);
        updateFmsRequest.setFmsSecond(strSecFmsId);
        updateFmsRequest.setWCRguidId(strGuuId);
        updateFmsRequest.setFmsPODno(Objects.requireNonNull(binding.layoutWcrFms.etPodEnd.getText()).toString());
        updateFmsRequest.setFmsPortCX(Objects.requireNonNull(binding.layoutWcrFms.etPortNumCx.getText()).toString());
        updateFmsRequest.setFmsPortPOD(Objects.requireNonNull(binding.layoutWcrFms.etPortnumEnd.getText()).toString());


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.updsteFmsDetails(updateFmsRequest);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    outAnimation = new AlphaAnimation(1f, 0f);
                    outAnimation.setDuration(200);
                    binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                    binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                    try {
                        if(response.body().getStatus().equals("Success")){
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                            moveNext();
                        }else{
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<CommonClassResponse> call, Throwable t) {
                binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                Log.e("RetroError", t.toString());
            }
        });

    }

  /*  @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE_READ_WRITE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // FileUtils.showFileChooser(Activity_Resolve.this);
            }
        }
    }
*/
    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_ONE) {
                try {
                    uri = data.getData();
                    filepath = FilePath.getPath(getContext(), uri);
                    if (filepath != null) {
                        if (FileUtils.checkExtension(getContext(), uri)) {
                            Uri file = Uri.fromFile(new File(filepath));
                            str_ext1 = MimeTypeMap.getFileExtensionFromUrl(file.toString());
                            binding.etAttach.setText(filepath);
                            try {
                                bitmap1 = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                          //  displayToast(R.string.valid_formats);
                        }
                    } else {
                        //displayToast(R.string.upload_files_message);
                    }
                } catch (Exception EX) {
                    EX.getStackTrace();
                }
            }
        }
    }
    private void updateWcrEnginer(String insta){
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        UpdateWcrEnggRequest updateWcrEnggRequest = new UpdateWcrEnggRequest();
        updateWcrEnggRequest.setAuthkey(Constants.AUTH_KEY);
        updateWcrEnggRequest.setAction(Constants.UPDATE_WCR_ENGINER);
        updateWcrEnggRequest.setEngName(binding.layoutWcrEngrDetails.etEnggName.getText().toString());
        updateWcrEnggRequest.setInstcode(insta);
        updateWcrEnggRequest.setAppointmentDate("");
        updateWcrEnggRequest.setWCRguidId(strGuuId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.updateWcrEng(updateWcrEnggRequest);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    outAnimation = new AlphaAnimation(1f, 0f);
                    outAnimation.setDuration(200);
                    binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                    binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                    try {
                        if(response.body().getStatus().equals("Success")){
                            moveNext();
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<CommonClassResponse> call, Throwable t) {
                binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                Log.e("RetroError", t.toString());
            }
        });
    }

    private void Permission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA},
                    REQUEST_CODE_READ_WRITE_CAMERA_PERMISSION);
        }
    }

   /* @SuppressLint("QueryPermissionsNeeded")
    private void alertSelectImage(int requestCameraPermission, int requestCode) {
        cameraFileUri = null;
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_select_image);
        dialog.findViewById(R.id.from_gallery).setVisibility(View.GONE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.findViewById(R.id.from_camera).setOnClickListener(v -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(cameraIntent.resolveActivity(getContext().getPackageManager())!=null) {
                File imageFile = null;
                try {
                    imageFile = getImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (imageFile != null) {
                    Uri imageUri = FileProvider.getUriForFile(getContext(),
                            BuildConfig.APPLICATION_ID + ".provider", imageFile);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(cameraIntent, requestCameraPermission);
                }
            }
            dialog.dismiss();
        });
     *//*   dialog.findViewById(R.id.from_gallery).setOnClickListener(v -> {
            if (PermissionUtils.checkWritePermission(getActivity()))
                FileUtils.showFileChooser(getActivity(),requestCode);
            dialog.dismiss();
        });*//*
        dialog.getWindow().getAttributes().windowAnimations = R.style.SelectMediaDialogTheme;
        dialog.show();
    }*/
    private File getImageFile() throws IOException {
        // Create an image file name
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "jpg_"+timeStamp+ "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        currentImagePath = mFile.getAbsolutePath();
        return mFile;
    }

    private void SubmitApproval(){
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        SubmitApprovalRequest submitApprovalRequest = new SubmitApprovalRequest();
        submitApprovalRequest.setAuthkey(Constants.AUTH_KEY);
        submitApprovalRequest.setAction(Constants.SUBMIT_FOR_APPROVAL);
        submitApprovalRequest.setItemId(strGuuId);
        submitApprovalRequest.setItemType("WCR");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.submitApproval(submitApprovalRequest);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    outAnimation = new AlphaAnimation(1f, 0f);
                    outAnimation.setDuration(200);
                    binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                    binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                    try {
                        if(response.body().getStatus().equals("Success")){
                            moveNext();
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<CommonClassResponse> call, Throwable t) {
                binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                Log.e("RetroError", t.toString());
            }
        });
    }

    private void updateHoldCategoryStatus(){
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        HoldWcrRequest holdWcrRequest = new HoldWcrRequest();
        holdWcrRequest.setAuthkey(Constants.AUTH_KEY);
        holdWcrRequest.setAction(Constants.HOLD_ORDER_INSTALLATION);
        holdWcrRequest.setHoldCategory(strholdId);
        holdWcrRequest.setHoldReason(binding.etHoldReason.getText().toString());
        holdWcrRequest.setOrderID(OrderId);
        holdWcrRequest.setStatus("hold");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.updateHoldCategory(holdWcrRequest);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    outAnimation = new AlphaAnimation(1f, 0f);
                    outAnimation.setDuration(200);
                    binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                    binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                    try {
                        if(response.body().getStatus().equals("Success")){
                            moveNext();
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonClassResponse> call, Throwable t) {
                binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                Log.e("RetroError", t.toString());
            }
        });

    }

    private void updateHoldCategory(){
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        HoldWcrRequest holdWcrRequest = new HoldWcrRequest();
        holdWcrRequest.setAuthkey(Constants.AUTH_KEY);
        holdWcrRequest.setAction(Constants.HOLD_WCR);
        holdWcrRequest.setCategory(strholdId);
        holdWcrRequest.setReason(binding.etHoldReason.getText().toString());
        holdWcrRequest.setWCRguidId(strGuuId);
        holdWcrRequest.setSegment(strSegment);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.updateHoldCategory(holdWcrRequest);
        call.enqueue(new Callback<CommonClassResponse>() {
            @Override
            public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    outAnimation = new AlphaAnimation(1f, 0f);
                    outAnimation.setDuration(200);
                    binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                    binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                    try {
                        if(response.body().getStatus().equals("Success")){
                            moveNext();
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getContext(),response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonClassResponse> call, Throwable t) {
                binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                Log.e("RetroError", t.toString());
            }
        });

    }
}