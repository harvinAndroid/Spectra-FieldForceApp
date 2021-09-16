package com.spectra.fieldforce.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
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
import com.spectra.fieldforce.BuildConfig;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.activity.ProvisioningMainActivity;
import com.spectra.fieldforce.adapter.WcrAddManholeAdapter;
import com.spectra.fieldforce.adapter.WcrCompletteItemConsumptionListAdapter;
import com.spectra.fieldforce.adapter.WcrEquipmentConsumpAdapter;
import com.spectra.fieldforce.adapter.WcrServiceConsumptionListAdapter;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.databinding.WcrFragmentBinding;
import com.spectra.fieldforce.model.gpon.request.AccountInfoRequest;
import com.spectra.fieldforce.model.gpon.request.AssociatedResquest;
import com.spectra.fieldforce.model.gpon.request.HoldWcrRequest;
import com.spectra.fieldforce.model.gpon.request.ResendNavRequest;
import com.spectra.fieldforce.model.gpon.request.SubmitApprovalRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateCustomerNetwork;
import com.spectra.fieldforce.model.gpon.request.UpdateFmsRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateQualityParamRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateWcrEnggRequest;
import com.spectra.fieldforce.model.gpon.request.UploadWcrImgRequest;
import com.spectra.fieldforce.model.gpon.request.WcrCompleteRequest;
import com.spectra.fieldforce.model.gpon.response.CommonClassResponse;
import com.spectra.fieldforce.model.gpon.response.GetFmsListResponse;
import com.spectra.fieldforce.model.gpon.response.HoldReasonResponse;
import com.spectra.fieldforce.model.gpon.response.WcrResponse;
import com.spectra.fieldforce.utils.Constants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.spectra.fieldforce.utils.AppConstants.REQUEST_CAMERA_PERMISSION_ONE;
import static com.spectra.fieldforce.utils.AppConstants.REQUEST_CAMERA_PERMISSION_TWO;

public class WcrFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener{
    private WcrFragmentBinding binding;
    private ArrayList<String> FmsType;
    private ArrayList<String> FmsId;
    private List<GetFmsListResponse.Fms> fmsList;
    private List<HoldReasonResponse.WCRHoldCategory> holdList;
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
    private String strGuuId,strSegment, strHold,doa,strInsta,strfmsId,strSecFmsId,strCanId ,strholdId="",strProductSegment,straddition,OrderId,StatusOfReport;
    private ArrayList<String> itemType;
    private ArrayList<WcrResponse.ManHoleDetails> manHoleDetails;
    private ArrayList<WcrResponse.ItemConsumtion> itemConsumtions;
    private ArrayList<WcrResponse.EquipmentDetailsList> equipmentDetailsLists;
    private ArrayList<WcrResponse.ServiceConsumtion> serviceConsumtions;
    private AlphaAnimation inAnimation,outAnimation;
    ArrayAdapter<String> adapterfms;
    ArrayAdapter<String> adaptersecond;
    private String str_ext1="",str_ext2="",str_ext3="",str_ext4="",strSlotType,currentImagePath,currentImagePath1;
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
    private Calendar mCalendar;
    private String add;
    private String fromDateString = "";
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

        ActivityCompat.requestPermissions( getActivity(),
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        QualityParamCable = new ArrayList<String>();
        QualityParamLogin = new ArrayList<String>();
        QualityParamCustomer = new ArrayList<String>();
        QualityParamSpeed = new ArrayList<String>();
        QualityParamEducation = new ArrayList<String>();
        QualityParamWifi = new ArrayList<String>();
        QualityParamFace = new ArrayList<String>();


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

        binding.layoutServiceDetails.btnAddService.setOnClickListener(v -> {
            @SuppressLint("UseRequireInsteadOfGet") FragmentTransaction t1= Objects.requireNonNull(this.getFragmentManager()).beginTransaction();
            WcrServiceConsumption wcrServiceConsumption = new WcrServiceConsumption();
            Bundle bundle = new Bundle();
            bundle.putString("canId", strCanId);
            bundle.putString("strGuuId", strGuuId);
            bundle.putString("StatusofReport", StatusOfReport);
            bundle.putString("OrderId", OrderId);
            t1.replace(R.id.frag_container, wcrServiceConsumption);
            wcrServiceConsumption.setArguments(bundle);
            t1.commit();
        });


        binding.layoutCustomerNetwork.tvCustSave.setOnClickListener(v -> {
            String rxpower,speedlan,speedwifi,wifissd,txpower;
            rxpower = Objects.requireNonNull(binding.layoutCustomerNetwork.etRxPower.getText()).toString();
            speedlan = Objects.requireNonNull(binding.layoutCustomerNetwork.etSpeedLan.getText()).toString();
            speedwifi = Objects.requireNonNull(binding.layoutCustomerNetwork.etSpeedWifi.getText()).toString();
            wifissd = Objects.requireNonNull(binding.layoutCustomerNetwork.etWifiSsd.getText()).toString();
            txpower = Objects.requireNonNull(binding.layoutCustomerNetwork.etTxPower.getText()).toString();
       /*    if(speedlan.isEmpty()){
                Toast.makeText(getContext(),"Please Enter SpeedLan",Toast.LENGTH_LONG).show();
            }   if(speedwifi.isEmpty()){
                Toast.makeText(getContext(),"Please Enter Speed Wifi",Toast.LENGTH_LONG).show();
            }else*/ if(wifissd.isEmpty()){
                Toast.makeText(getContext(),"Please Enter Wifi SSD",Toast.LENGTH_LONG).show();
            }else{
                updateCustomerNetwork(rxpower,speedlan,speedwifi,wifissd,txpower);
                uploadArtifacts();
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
                    String misc = Objects.requireNonNull(binding.etMisc.getText()).toString();
                    if (remark.isEmpty()) {
                        Toast.makeText(getContext(), "Please Enter The Remark", Toast.LENGTH_LONG).show();
                    }else if (misc.isEmpty()) {
                Toast.makeText(getContext(), "Please Enter The Remark", Toast.LENGTH_LONG).show();
            }
                    else {
                        try {
                                getLastLocation();

                                if (strSegment.equals("Business")) {
                                    if (manHoleDetails.size() == 0 || manHoleDetails == null) {
                                        Toast.makeText(getContext(), "Please Add Manhole", Toast.LENGTH_LONG).show();
                                    } else if (itemConsumtions.size() == 0 || itemConsumtions == null) {
                                        Toast.makeText(getContext(), "Please Add ItemConsumption", Toast.LENGTH_LONG).show();
                                    } else {
                                        updateWcrComplete(remark,latitude,longitude, misc);
                                    }
                                } else if (strSegment.equals("Home")) {
                                    if (equipmentDetailsLists.size() == 0 || equipmentDetailsLists == null) {
                                        Toast.makeText(getContext(), "Please Add Equipment", Toast.LENGTH_LONG).show();
                                    } else if (itemConsumtions.size() == 0 || itemConsumtions == null) {
                                        Toast.makeText(getContext(), "Please Add ItemConsumption", Toast.LENGTH_LONG).show();
                                    } else {
                                        updateWcrComplete(remark,latitude,longitude,misc);
                                    }
                                }
                           // }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
        );
        binding.layoutCustomerNetwork.etSpeedWifi.setOnClickListener(view -> {
            checkPermission(Manifest.permission.CAMERA, REQUEST_CAMERA_PERMISSION_ONE);
           //myStoragePermission(REQUEST_CAMERA_PERMISSION_ONE);


        });
        binding.layoutCustomerNetwork.etSpeedLan.setOnClickListener(view -> {
            checkPermission1(Manifest.permission.CAMERA, REQUEST_CAMERA_PERMISSION_TWO);
            //OpenCamera(REQUEST_CAMERA_PERMISSION_TWO);

        });
        binding.layoutWcrEngrDetails.saveEnggDetails.setOnClickListener((View v) -> {
            strInsta = Objects.requireNonNull(binding.layoutWcrEngrDetails.etInstallationCode.getText()).toString();
            if (strSegment.equals("Business")) {
                updateWcrEnginer(strInsta);
            } else if (strSegment.equals("Home")) {
                if(strInsta.isEmpty()|| strInsta.equals("Installation Code")){
                    Toast.makeText(getContext(),"Please Enter Installation Code",Toast.LENGTH_LONG).show();
                }else{
                    updateWcrEnginer(strInsta);
                }
            }


        });
        binding.tvWcrApproval.setOnClickListener(view -> SubmitApproval());

        binding.tvResendNav.setOnClickListener(view -> resendNav());

        binding.layoutInstallationparam.tvSaveQualityParam.setOnClickListener((View v) -> {

            String ont = Objects.requireNonNull(binding.layoutInstallationparam.etOntLogin.getText()).toString();
            String face = Objects.requireNonNull(binding.layoutInstallationparam.etFacePlate.getText()).toString();
            String wifi = Objects.requireNonNull(binding.layoutInstallationparam.etWifiSsid.getText()).toString();
            String selfcare = Objects.requireNonNull(binding.layoutInstallationparam.etEducationCustomer.getText()).toString();
            String virus = Objects.requireNonNull(binding.layoutInstallationparam.etEducationAntivirus.getText()).toString();
            String cable = Objects.requireNonNull(binding.layoutInstallationparam.etCableCrimped.getText()).toString();
            String speed = Objects.requireNonNull(binding.layoutInstallationparam.etSpeedTest.getText()).toString();
            if(ont.isEmpty()||ont.equals("Select Type")){
                Toast.makeText(getContext(), "Please Select  ONT login details shared with Customer", Toast.LENGTH_LONG).show();
            }else if(face.isEmpty()||face.equals("Select Type")){
                Toast.makeText(getContext(), "Please Select  Face plate mount to the wall properly", Toast.LENGTH_LONG).show();
            }else if(wifi.isEmpty()||wifi.equals("Select Type")){
                Toast.makeText(getContext(), "Please Select Wifi", Toast.LENGTH_LONG).show();
            }else if(selfcare.isEmpty()||selfcare.equals("Select Type")){
                Toast.makeText(getContext(), "Please Select  Education customer Regarding Selfcare Portal", Toast.LENGTH_LONG).show();
            }else if(virus.isEmpty()||virus.equals("Select Type")){
                Toast.makeText(getContext(), "Please Select Antivirus", Toast.LENGTH_LONG).show();
            }else if(cable.isEmpty()||cable.equals("Select Type")){
                Toast.makeText(getContext(), "Please Select Cable crimped to the wall properly", Toast.LENGTH_LONG).show();
            }else if(speed.isEmpty()||speed.equals("Select Type")){
                Toast.makeText(getContext(), "Please Select Speed test results shown to coustomer", Toast.LENGTH_LONG).show();
            }else{
                updateQualityParam(ont,face,wifi,selfcare,virus,cable,speed);
            }
        });
    }



    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(getActivity(), new String[] { permission }, requestCode);
        }
        else {
            OpenCamera(REQUEST_CAMERA_PERMISSION_ONE);
            Toast.makeText(getActivity(), "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }
    public void checkPermission1(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(getActivity(), new String[] { permission }, requestCode);
        }
        else {
            OpenCamera(REQUEST_CAMERA_PERMISSION_TWO);
            Toast.makeText(getActivity(), "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    private void OpenCamera(int requestCameraPermissionOne){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(cameraIntent.resolveActivity(getActivity().getPackageManager())!=null) {
            File imageFile = null;
            try {
                imageFile = getImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (imageFile != null) {
                Uri imageUri = FileProvider.getUriForFile(getActivity(),
                        BuildConfig.APPLICATION_ID + ".provider", imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(cameraIntent, requestCameraPermissionOne);
            }
        }
    }

    private File getImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "jpg_"+timeStamp+ "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        currentImagePath = mFile.getAbsolutePath();
        return mFile;
    }


    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkPermissions()) {
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
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);
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

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

       }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }else  if (requestCode == REQUEST_CAMERA_PERMISSION_ONE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Camera Permission Granted", Toast.LENGTH_SHORT) .show();
            }
            else {
                Toast.makeText(getActivity(), "Camera Permission Denied", Toast.LENGTH_SHORT) .show();
            }

        }else  if (requestCode == REQUEST_CAMERA_PERMISSION_TWO) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Camera Permission Granted", Toast.LENGTH_SHORT) .show();
            }
            else {
                Toast.makeText(getActivity(), "Camera Permission Denied", Toast.LENGTH_SHORT) .show();
            }

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == REQUEST_CAMERA_PERMISSION_ONE) {
            try {
                bitmap1 = BitmapFactory.decodeFile(currentImagePath);
                binding.layoutCustomerNetwork.etSpeedWifi.setText(currentImagePath);
                str_ext1 = "SpeedWifi"+".jpg";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if ( requestCode == REQUEST_CAMERA_PERMISSION_TWO) {
                try {
                    bitmap2 = BitmapFactory.decodeFile(currentImagePath);
                    binding.layoutCustomerNetwork.etSpeedLan.setText(currentImagePath);
                    str_ext2 = "SpeedLan"+".jpg";
                } catch (Exception e) {
                    e.printStackTrace();
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
            binding.linearservicedeatils.setVisibility(View.GONE);
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
            binding.linearservicedeatils.setVisibility(View.GONE);
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
            binding.linearservicedeatils.setVisibility(View.GONE);
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
            binding.linearservicedeatils.setVisibility(View.GONE);
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
            binding.linearservicedeatils.setVisibility(View.GONE);
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
            binding.linearservicedeatils.setVisibility(View.GONE);
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
            binding.linearservicedeatils.setVisibility(View.GONE);
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
            binding.linearservicedeatils.setVisibility(View.GONE);
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
            binding.linearservicedeatils.setVisibility(View.GONE);
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
            binding.linearservicedeatils.setVisibility(View.GONE);
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
            binding.linearservicedeatils.setVisibility(View.GONE);
        });
        binding.linearService.setOnClickListener(v -> {
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
            binding.linearservicedeatils.setVisibility(View.VISIBLE);
            binding.linearInstallationParamDetails.setVisibility(View.GONE);
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
            binding.linearService.setVisibility(View.VISIBLE);
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
            binding.linearService.setVisibility(View.VISIBLE);
        }
    }

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
                        adaptersecond.setDropDownViewResource(android.R.layout.simple_spinner_item);
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

    public void getHoldReason() {
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        AccountInfoRequest accountInfoRequest = new AccountInfoRequest();
        accountInfoRequest.setAuthkey(Constants.AUTH_KEY);
        accountInfoRequest.setAction(Constants.WCR_HOLD_CATEGORY);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<HoldReasonResponse> call = apiService.getHoldReason(accountInfoRequest);
        call.enqueue(new Callback<HoldReasonResponse>() {
            @Override
            public void onResponse(Call<HoldReasonResponse> call, Response<HoldReasonResponse> response) {
                if (response.isSuccessful()&& response.body()!=null) {
                    outAnimation = new AlphaAnimation(1f, 0f);
                    outAnimation.setDuration(200);
                    binding.progressLayout.progressOverlay.setAnimation(outAnimation);
                    binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                    try {
                        holdList = response.body().getResponse().getWCRHoldCategory();
                        holdCategoryId = new ArrayList<>();
                        holdCategory = new ArrayList<>();
                        holdCategory.add("Hold Category");
                        for (HoldReasonResponse.WCRHoldCategory fms : holdList)
                            holdCategory.add(fms.getCategory());
                        for (HoldReasonResponse.WCRHoldCategory fms : holdList)
                            holdCategoryId.add(fms.getId());
                        adaptersecond = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, holdCategory);
                        adaptersecond.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        binding.spHoldCategory.setAdapter(adaptersecond);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<HoldReasonResponse> call, Throwable t) {
                binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                Log.e("RetroError", t.toString());
            }
        });
    }



    private void Type() {
        try {
            FmsType = new ArrayList<String>();
            FmsType.add("Select Fms Type");
            FmsType.add("WallMount");
            FmsType.add("RackMount");
            FmsId = new ArrayList<String>();
            FmsId.add("569480000");
            FmsId.add("569480001");
            adapterfms = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, FmsType);
            adapterfms.setDropDownViewResource(android.R.layout.simple_spinner_item);
            binding.layoutWcrFms.spCustomerEndFms.setAdapter(adapterfms);
        }catch (Exception ex){
            ex.getMessage();
        }

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sendDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
        @SuppressLint("SetTextI18n")
        final DatePickerDialog.OnDateSetListener mFromDateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, monthOfYear);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            fromDateString = sendDateFormat.format(mCalendar.getTime());
            binding.layoutWcrEngrDetails.etCreatedOn.setText("" + fromDateString);
        };
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

        binding.layoutWcrEngrDetails.etCreatedOn.setOnClickListener(view -> {
            try {
                final DatePickerDialog fromPickerDialog = new DatePickerDialog(
                        getActivity(), android.R.style.Theme_Material_Light_Dialog_Alert,
                        mFromDateSetListener,
                        mCalendar.get(Calendar.YEAR),
                        mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH));
                fromPickerDialog.show();
                fromPickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            } catch (Exception ex) {

            }
        });
        mCalendar = Calendar.getInstance();
        fromDateString = sendDateFormat.format(mCalendar.getTime());

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
                            String consumptionStatus = response.body().getResponse().getWcr().getWCRConsumptionStatus();
                            if(consumptionStatus.equals("Material not available")){
                                binding.tvResendNav.setVisibility(View.VISIBLE);
                            }
                            binding.layoutWcrFragmentCustomerDetails.setCustomer(response.body().getResponse().getWcr());
                            strGuuId = response.body().getResponse().getWcr().getWcrguidid();
                            strHold = response.body().getResponse().getWcr().getShowHold();
                            if(strHold.equals("true")){
                                binding.linea18.setVisibility(View.VISIBLE);
                            }else{
                                binding.linea18.setVisibility(View.GONE);
                            }
                            add="0";
                            strProductSegment = response.body().getResponse().getWcr().getProductSegment();
                            strSegment = response.body().getResponse().getWcr().getBusinessSegment();
                            if (((consumptionStatus.equals("false")) && strSegment.equals("Business")) || (consumptionStatus.equals("Pending")) || (consumptionStatus.equals("Rejected")) || (consumptionStatus.equals("Material not Available"))){
                                binding.layoutItemConsumption.btnItemConsumption1.setVisibility(View.VISIBLE);
                                binding.layoutAddEquipment.btnItemEqipment.setVisibility(View.VISIBLE);
                                add="1";
                            }
                            if ((strSegment.equals("Home")) || (consumptionStatus.equals("Pending")) || (consumptionStatus.equals("Rejected")) || (consumptionStatus.equals("Material not Available"))){
                                binding.layoutItemConsumption.btnItemConsumption1.setVisibility(View.VISIBLE);
                                binding.layoutAddEquipment.btnItemEqipment.setVisibility(View.VISIBLE);
                                add="1";
                            }

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
                                adapterfms = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, FmsType);
                                adapterfms.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                binding.layoutWcrFms.spCustomerEndFms.setAdapter(adapterfms);
                            }
                            String strfmssec = response.body().getResponse().getFMSDetails().getFmssecond();
                            if(strfmssec!=null && !strfmssec.equals("0")){
                              //  firstFmsID = new ArrayList<>();
                                fmsName = new ArrayList<>();
                                fmsName.add(strfmssec);
                                adaptersecond = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, fmsName);
                                adaptersecond.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                binding.layoutWcrFms.spCustomerEndFmsSec.setAdapter(adaptersecond);
                            }
                            binding.layoutWcrEngrDetails.setEngg(response.body().getResponse().getEngineerDetails());
                            binding.setHoldCategory(response.body().getResponse().getEngineerDetails());
                            binding.layoutCustomerNetwork.setCustomerNetwork(response.body().getResponse().getCusotmerNetwork());
                            binding.layoutmanholDetails.rvAddManhole.setHasFixedSize(true);
                            binding.layoutmanholDetails.rvAddManhole.setLayoutManager(new LinearLayoutManager(getActivity()));
                            binding.layoutmanholDetails.rvAddManhole.setAdapter(new WcrAddManholeAdapter(getActivity(), manHoleDetails));
                            itemConsumtions = response.body().getResponse().getItemConsumtionList();
                             straddition = itemConsumtions.get(0).getItemType();

                            if(straddition!=null){
                                if(straddition.equals("Additional")&& strSegment.equals("Home")){
                                    if(consumptionStatus.equals("Waiting for approval")){
                                        binding.tvWcrApproval.setVisibility(View.GONE);
                                        binding.layoutItemConsumption.btnItemConsumption1.setVisibility(View.GONE);
                                        binding.layoutAddEquipment.btnItemEqipment.setVisibility(View.GONE);
                                        add="0";
                                    }else{
                                        binding.tvWcrApproval.setVisibility(View.VISIBLE);
                                        binding.layoutItemConsumption.btnItemConsumption1.setVisibility(View.VISIBLE);
                                        binding.layoutAddEquipment.btnItemEqipment.setVisibility(View.VISIBLE);
                                        add="1";
                                    }

                                }

                            }

                            doa = response.body().getResponse().getWcr().getDOAFlag();
                            if(doa!=null){
                                if(doa.equals("No") && straddition.equals("Additional")){
                                    binding.tvWcrApproval.setVisibility(View.VISIBLE);
                                    add="0";
                                }
                            }
                            if(straddition!=null){
                                if(straddition.equals("Additional")&& strSegment.equals("Home")){
                                    if(consumptionStatus.equals("Waiting for approval")){
                                        binding.tvWcrApproval.setVisibility(View.GONE);
                                        binding.layoutItemConsumption.btnItemConsumption1.setVisibility(View.GONE);
                                        binding.layoutAddEquipment.btnItemEqipment.setVisibility(View.GONE);
                                        add="0";
                                    }else{
                                        binding.tvWcrApproval.setVisibility(View.VISIBLE);
                                        binding.layoutItemConsumption.btnItemConsumption1.setVisibility(View.VISIBLE);
                                        binding.layoutAddEquipment.btnItemEqipment.setVisibility(View.VISIBLE);
                                        add="1";
                                    }

                                }

                            }

                            doa = response.body().getResponse().getWcr().getDOAFlag();
                            if(doa!=null){
                                if(doa.equals("No") && straddition.equals("Additional")){
                                    binding.tvWcrApproval.setVisibility(View.VISIBLE);
                                    add="1";
                                }
                            }
                            binding.layoutItemConsumption.rvWcrItemlist.setHasFixedSize(true);
                            binding.layoutItemConsumption.rvWcrItemlist.setLayoutManager(new LinearLayoutManager(getActivity()));
                            binding.layoutItemConsumption.rvWcrItemlist.setAdapter(new WcrCompletteItemConsumptionListAdapter(getActivity(), itemConsumtions,add));
                            serviceConsumtions = response.body().getResponse().getServiceConsumtionList();
                            binding.layoutServiceDetails.rvAddService.setHasFixedSize(true);
                            binding.layoutServiceDetails.rvAddService.setLayoutManager(new LinearLayoutManager(getActivity()));
                            binding.layoutServiceDetails.rvAddService.setAdapter(new WcrServiceConsumptionListAdapter(getActivity(), serviceConsumtions));
                            equipmentDetailsLists = response.body().getResponse().getEquipmentDetailsList();
                            binding.layoutAddEquipment.rvAddEquipment.setHasFixedSize(true);
                            binding.layoutAddEquipment.rvAddEquipment.setLayoutManager(new LinearLayoutManager(getActivity()));
                            if(equipmentDetailsLists.size()!=0){
                                binding.layoutAddEquipment.rvAddEquipment.setAdapter(new WcrEquipmentConsumpAdapter(getActivity(),equipmentDetailsLists,add));
                            }
                            binding.layoutInstallationparam.setQuality(response.body().getResponse().getInstallationQuality());

                            try {

                                if (response.body().getResponse().getInstallationQuality().getAntiVirus().equals("Yes")) {
                                    QualityParamEducation.clear();
                                    QualityParamEducation.add("Yes");
                                    QualityParamEducation.add("Select Type");
                                    QualityParamEducation.add("No");
                                    ArrayAdapter<String> adapterParam1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamEducation);
                                    adapterParam1.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spEducationAntivirus.setAdapter(adapterParam1);

                                } else if (response.body().getResponse().getInstallationQuality().getAntiVirus().equals("No")) {

                                    QualityParamEducation.clear();
                                    QualityParamEducation.add("No");
                                    QualityParamEducation.add("Select Type");
                                    QualityParamEducation.add("Yes");
                                    ArrayAdapter<String> adapterParam1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamEducation);
                                    adapterParam1.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spEducationAntivirus.setAdapter(adapterParam1);
                                } else {
                                    QualityParamEducation.add("Select Type");
                                    QualityParamEducation.add("Yes");
                                    QualityParamEducation.add("No");
                                    ArrayAdapter<String> adapterParam1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamEducation);
                                    adapterParam1.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spEducationAntivirus.setAdapter(adapterParam1);
                                }
                                if (response.body().getResponse().getInstallationQuality().getCable().equals("Yes")) {
                                    QualityParamCable.clear();
                                    QualityParamCable.add("Yes");
                                    QualityParamCable.add("Select Type");
                                    QualityParamCable.add("No");
                                    adapterParamCable = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamCable);
                                    adapterParamCable.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spCableCrimped.setAdapter(adapterParamCable);

                                } else if (response.body().getResponse().getInstallationQuality().getCable().equals("No")) {
                                    QualityParamCable.clear();
                                    QualityParamCable.add("No");
                                    QualityParamCable.add("Select Type");
                                    QualityParamCable.add("Yes");
                                    adapterParamCable = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamCable);
                                    adapterParamCable.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spCableCrimped.setAdapter(adapterParamCable);
                                } else {
                                    QualityParamCable.add("Select Type");
                                    QualityParamCable.add("Yes");
                                    QualityParamCable.add("No");
                                    adapterParamCable = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamCable);
                                    adapterParamCable.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spCableCrimped.setAdapter(adapterParamCable);
                                }
                                if (response.body().getResponse().getInstallationQuality().getFace().equals("Yes")) {
                                    QualityParamFace.clear();
                                    QualityParamFace.add("Yes");
                                    QualityParamFace.add("Select Type");
                                    QualityParamFace.add("No");
                                    adapterParamFace = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamFace);
                                    adapterParamFace.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spFacePlate.setAdapter(adapterParamFace);
                                } else if (response.body().getResponse().getInstallationQuality().getFace().equals("No")) {
                                    QualityParamFace.clear();
                                    QualityParamFace.add("No");
                                    QualityParamFace.add("Select Type");
                                    QualityParamFace.add("Yes");
                                    adapterParamFace = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamFace);
                                    adapterParamFace.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spFacePlate.setAdapter(adapterParamFace);
                                } else {
                                    QualityParamFace.add("Select Type");
                                    QualityParamFace.add("Yes");
                                    QualityParamFace.add("No");
                                    adapterParamFace = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamFace);
                                    adapterParamFace.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spFacePlate.setAdapter(adapterParamFace);
                                }
                                if (response.body().getResponse().getInstallationQuality().getOnt().equals("Yes")) {
                                    QualityParamLogin.clear();
                                    QualityParamLogin.add("Yes");
                                    QualityParamLogin.add("Select Type");
                                    QualityParamLogin.add("No");
                                    adapterParamLogin = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamLogin);
                                    adapterParamLogin.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spOntLogin.setAdapter(adapterParamLogin);
                                } else if (response.body().getResponse().getInstallationQuality().getOnt().equals("No")) {
                                    QualityParamLogin.add("No");
                                    QualityParamLogin.add("Select Type");
                                    QualityParamLogin.add("Yes");
                                    adapterParamLogin = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamLogin);
                                    adapterParamLogin.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spOntLogin.setAdapter(adapterParamLogin);
                                } else {
                                    QualityParamLogin.add("Select Type");
                                    QualityParamLogin.add("Yes");
                                    QualityParamLogin.add("No");
                                    adapterParamLogin = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamLogin);
                                    adapterParamLogin.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spOntLogin.setAdapter(adapterParamLogin);
                                }
                                if (response.body().getResponse().getInstallationQuality().getSelfCare().equals("Yes")) {
                                    QualityParamCustomer.clear();
                                    QualityParamCustomer.add("Yes");
                                    QualityParamCustomer.add("Select Type");
                                    QualityParamCustomer.add("No");
                                    adapterParamCustomer = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamCustomer);
                                    adapterParamCustomer.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spEducationCustomer.setAdapter(adapterParamCustomer);
                                } else if (response.body().getResponse().getInstallationQuality().getSelfCare().equals("No")) {
                                    QualityParamCustomer.clear();
                                    QualityParamCustomer.add("No");
                                    QualityParamCustomer.add("Select Type");
                                    QualityParamCustomer.add("Yes");
                                    adapterParamCustomer = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamCustomer);
                                    adapterParamCustomer.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spEducationCustomer.setAdapter(adapterParamCustomer);
                                } else {
                                    QualityParamCustomer.add("Select Type");
                                    QualityParamCustomer.add("Yes");
                                    QualityParamCustomer.add("No");
                                    adapterParamCustomer = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamCustomer);
                                    adapterParamCustomer.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spEducationCustomer.setAdapter(adapterParamCustomer);
                                }
                                if (response.body().getResponse().getInstallationQuality().getSpeed().equals("Yes")) {
                                    QualityParamSpeed.clear();
                                    QualityParamSpeed.add("Yes");
                                    QualityParamSpeed.add("Select Type");
                                    QualityParamSpeed.add("No");
                                    adapterParamSpeed = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamSpeed);
                                    adapterParamSpeed.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spSpeedTest.setAdapter(adapterParamSpeed);
                                } else if (response.body().getResponse().getInstallationQuality().getSpeed().equals("No")) {
                                    QualityParamSpeed.clear();
                                    QualityParamSpeed.add("No");
                                    QualityParamSpeed.add("Select Type");
                                    QualityParamSpeed.add("Yes");
                                    adapterParamSpeed = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamSpeed);
                                    adapterParamSpeed.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spSpeedTest.setAdapter(adapterParamSpeed);
                                } else {
                                    QualityParamSpeed.add("Select Type");
                                    QualityParamSpeed.add("Yes");
                                    QualityParamSpeed.add("No");
                                    adapterParamSpeed = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamSpeed);
                                    adapterParamSpeed.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spSpeedTest.setAdapter(adapterParamSpeed);
                                }
                                if (response.body().getResponse().getInstallationQuality().getWifi().equals("Yes")) {
                                    QualityParamWifi.clear();
                                    QualityParamWifi.add("Yes");
                                    QualityParamWifi.add("Select Type");
                                    QualityParamWifi.add("No");
                                    adapterParamWifi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamWifi);
                                    adapterParamWifi.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spWifiSsid.setAdapter(adapterParamWifi);
                                } else if (response.body().getResponse().getInstallationQuality().getWifi().equals("No")) {
                                    QualityParamWifi.clear();
                                    QualityParamWifi.add("No");
                                    QualityParamWifi.add("Select Type");
                                    QualityParamWifi.add("Yes");
                                    adapterParamWifi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamWifi);
                                    adapterParamWifi.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spWifiSsid.setAdapter(adapterParamWifi);
                                } else {
                                    QualityParamWifi.add("Select Type");
                                    QualityParamWifi.add("Yes");
                                    QualityParamWifi.add("No");
                                    adapterParamWifi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QualityParamWifi);
                                    adapterParamWifi.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    binding.layoutInstallationparam.spWifiSsid.setAdapter(adapterParamWifi);
                                }
                            }catch (Exception ex){
                                ex.getMessage();
                            }

                            listener();
                            getFmsList();
                            getHoldReason();
                            Type();
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


    private void updateWcrComplete(String remark, String latitude, String longitude, String misc){
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
        wcrCompleteRequest.setMiscWorkCost(misc);
        wcrCompleteRequest.setRemarks(remark);
        wcrCompleteRequest.setWCRguidId(strGuuId);
        wcrCompleteRequest.setLat(latitude);
        wcrCompleteRequest.setLong(longitude);
        wcrCompleteRequest.setSource("App");
        wcrCompleteRequest.setCanId(strCanId);

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
        updateCustomerNetwork.setSpeedLan("");
        updateCustomerNetwork.setSpeedWifi("");
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


    private void resendNav(){
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
        ResendNavRequest resendNavRequest = new ResendNavRequest(Constants.AUTH_KEY,Constants.RESEND_NAVWCR,strGuuId,"Business","");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonClassResponse> call = apiService.submitNavWcr(resendNavRequest);
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

    private void uploadArtifacts()  {
        String encodedImage="",encodedImage1="";

        try {
          if(str_ext1!=null && bitmap1!=null){
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap1.compress(Bitmap.CompressFormat.JPEG,75, byteArrayOutputStream);
                byte[] imageInByte = byteArrayOutputStream.toByteArray();
                encodedImage =  Base64.encodeToString(imageInByte,Base64.NO_WRAP);
            }

          if(str_ext2!=null && bitmap2!=null){
                ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
                bitmap2.compress(Bitmap.CompressFormat.JPEG,75, byteArrayOutputStream1);
                byte[] imageInByte1 = byteArrayOutputStream1.toByteArray();
                encodedImage1 =  Base64.encodeToString(imageInByte1,Base64.NO_WRAP);
            }

            UploadWcrImgRequest uploadWcrImgRequest = new UploadWcrImgRequest(Constants.UPLOAD_WCRDOC,Constants.AUTH_KEY,strGuuId,encodedImage,str_ext1,encodedImage1,str_ext2);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<CommonClassResponse> call = apiService.UploadWcr(uploadWcrImgRequest);
            call.enqueue(new Callback<CommonClassResponse>() {
                @Override
                public void onResponse(Call<CommonClassResponse> call, Response<CommonClassResponse> response) {
                    try {
                        if (response.isSuccessful()) {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<CommonClassResponse> call, Throwable t) {
                    Log.e("RetroError", t.toString());
                }
            });
        }catch (Exception ex){
            ex.getMessage();
        }
    }
}