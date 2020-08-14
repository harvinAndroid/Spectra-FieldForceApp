package com.spectra.fieldforce;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonElement;
import com.karumi.dexter.BuildConfig;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.spectra.fieldforce.Model.CommonResponse;
import com.spectra.fieldforce.Model.QuestionListRequest;
import com.spectra.fieldforce.Model.QuestionListResponse;
import com.spectra.fieldforce.Model.QuestionareList;
import com.spectra.fieldforce.Model.RCRequest;
import com.spectra.fieldforce.Model.SRRequest;
import com.spectra.fieldforce.utils.FileCompressor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Resolve extends AppCompatActivity {
    Button btnResolveSubmit,btnUnifySession;
    TextView sessionStatus;
    EditText rfo,speed_on_wifi,other_in_ml,speed_on_lan,router_position;
    Spinner rc1,rc2,rc3,resolveContacted,changeStatus;
    RecyclerView question_recyler_view;
    ArrayList<String> rc1Code;
    ArrayList<String> rc1Name;
    ArrayList<String> rc2Code;
    ArrayList<String> rc2Name;
    ArrayList<String> rc3Code;
    ArrayList<String> rc3Name;
    private ArrayList<QuestionListResponse.Data> questionList;
    private ArrayList<String> itemlist = new ArrayList<>();
    private String SrNumber,customerId;
    private String status;
    private JSONArray result;
    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;
    private FrameLayout progressOverlay;
    private boolean startFlag, endFlag;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GALLERY_PHOTO = 2;
    File mPhotoFile;
    FileCompressor mCompressor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resolve);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            customerId = extras.getString("CustomerId");
            SrNumber = extras.getString("SrNumber");
        } else {

        }
        init();
        getRC1();
        getQuestionList();
        resolveButton();
        listener();
        bindContacted();

    }


    private void init(){
        btnResolveSubmit = findViewById(R.id.btnResolveSubmit);
        btnUnifySession = findViewById(R.id.btnUnifySession);
        sessionStatus = findViewById(R.id.sessionStatus);
        btnUnifySession = findViewById(R.id.btnUnifySession);
        progressOverlay = (FrameLayout) findViewById(R.id.progress_overlay);
        question_recyler_view =(RecyclerView)findViewById(R.id.question_recyler_view);
        resolveContacted = findViewById(R.id.resolveContacted);
        speed_on_wifi = findViewById(R.id.speed_on_wifi);
        other_in_ml = findViewById(R.id.other_in_ml);
        speed_on_lan = findViewById(R.id.speed_on_lan);
        router_position = findViewById(R.id.router_position);


      //  changeStatus =  findViewById(R.id.changeStatus);
        rfo = findViewById(R.id.rfo);
        rc1 = findViewById(R.id.rc1);
        rc2 = findViewById(R.id.rc2);
        rc3 = findViewById(R.id.rc3);
        //bindChangeStatus(1);

    }

    private void bindContacted() {
        ArrayList<String> contact = new ArrayList<String>();
        contact.add("Select Status");
        contact.add("Yes");
        contact.add("No");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Activity_Resolve.this, android.R.layout.simple_spinner_item, contact);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        resolveContacted.setAdapter(adapter);
    }


    private void getRC2(String rc1Code) {
        String authKey = "ac7b51de9d888e1458dd53d8aJAN3ba6f";
        String action = "getRCtwo";
        String RConeId = rc1Code;

        RCRequest rcRequest = new RCRequest();
        rcRequest.setAuthkey(authKey);
        rcRequest.setAction(action);
        rcRequest.setRC1(RConeId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiService.getRCDetail(rcRequest);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(retrofit2.Call<JsonElement> call, Response<JsonElement> response) {
                try {
                    if (response.isSuccessful()) {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        status = jsonObject.getString("Status");
                        rc2Code = new ArrayList<String>();
                        rc2Name = new ArrayList<String>();
                        rc2Code.add("0");
                        rc2Name.add("Select Resolution Code 2");
                        if (status.equals("Failure")) {
                            Log.d("Failure", "error");
                        } else if (status.equals("Success")) {
                            try {
                                result = jsonObject.getJSONArray("data");
                                if (result != null) {
                                    for (int i = 0; i < result.length(); i++) {
                                        JSONObject jsonData = new JSONObject(String.valueOf(result.getString(i)));
                                        Log.d("RC2Response", jsonData.toString());
                                        String code = jsonData.getString("rc_twoId");
                                        String name = jsonData.getString("rootCauseTwo");
                                        rc2Code.add(code);
                                        rc2Name.add(name);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Activity_Resolve.this, android.R.layout.simple_spinner_item, rc2Name);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        rc2.setAdapter(adapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<JsonElement> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }

    private void listener(){
        rc1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int itemPosition = rc1.getSelectedItemPosition();
                String rc1Id = rc1Code.get(itemPosition).toString();
                getRC2(rc1Id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        rc2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int itemPosition = rc2.getSelectedItemPosition();
                String rc2Id = rc2Code.get(itemPosition).toString();
                getRC3(rc2Id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnUnifySession.setOnClickListener(v -> getUnifySession());

        speed_on_wifi.setOnClickListener(v -> selectImage());
       /* speed_on_lan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });*/

    }


    private void getQuestionList() {
        String authKey = "ac7b51de9d888e1458dd53d8aJAN3ba6f";
        String action = "getAllQuestioner";

        QuestionListRequest questionListRequest = new QuestionListRequest();
        questionListRequest.setAuthkey(authKey);
        questionListRequest.setAction(action);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<QuestionListResponse> call = apiService.getQuestionList(questionListRequest);
        call.enqueue(new Callback<QuestionListResponse>() {
            @Override
            public void onResponse(retrofit2.Call<QuestionListResponse> call, Response<QuestionListResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        status=response.body().getStatus();
                     /*   JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        status = jsonObject.getString("Status");*/
                        if (status.equals("Failure")) {
                            Log.d("Failure", "error");
                        } else if (status.equals("Success")) {
                            try {
                                questionList = response.body().getData();
                                // questionList = new ArrayList<>();
                                if (response != null) {
                                    QuestionAnswerAdapter adapter = new QuestionAnswerAdapter(Activity_Resolve.this,questionList,myClickListener);
                                    //question_recyler_view.setVisibility(View.VISIBLE);
                                    question_recyler_view.setHasFixedSize(true);
                                    question_recyler_view.setLayoutManager(new LinearLayoutManager(Activity_Resolve.this));
                                    question_recyler_view.setAdapter(adapter);

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<QuestionListResponse> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }


    private OnItemClickListener myClickListener = (tag,questionid) -> {
        tag.get(0);
        // questionid.get(0);
        itemlist.add(tag.get(0));
    };

    private void getUnifySession() {
        String authKey = "ac7b51de9d888e1458dd53d8aJAN3ba6f";
        String action = "getCurrentSession";
        String canId = customerId;

        RCRequest rcRequest = new RCRequest();
        rcRequest.setAuthkey(authKey);
        rcRequest.setAction(action);
        rcRequest.setCanId(canId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiService.getRCDetail(rcRequest);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(retrofit2.Call<JsonElement> call, Response<JsonElement> response) {
                try {
                    if (response.isSuccessful()) {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        status = jsonObject.getString("status");
                        String bytesin = "";
                        if (status.equals("Failure")) {
                            bytesin = "Failure in getting Status";
                        } else if (status.equals("success")) {
                            try {
                                JSONObject response1 = jsonObject.getJSONObject("response");
                                if (response1 != null) {
                                    int byteIn = 0;
                                    bytesin = response1.getString("bytesin");
                                    byteIn = Integer.parseInt(bytesin);
                                    if (byteIn > 0) {
                                        bytesin = "Up & Running";
                                    } else {
                                        bytesin = "Not Working";
                                    }
                                }
                            } catch (Exception e) {
                                bytesin = "Failure in getting Status";
                                e.printStackTrace();
                            }
                        }
                        sessionStatus.setText(bytesin);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<JsonElement> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }


    private void getRC3(String rc2Code) {
        String authKey = "ac7b51de9d888e1458dd53d8aJAN3ba6f";
        String action = "getRCthree";
        String RCtwoId = rc2Code;

        RCRequest rcRequest = new RCRequest();
        rcRequest.setAuthkey(authKey);
        rcRequest.setAction(action);
        rcRequest.setRC2(RCtwoId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiService.getRCDetail(rcRequest);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(retrofit2.Call<JsonElement> call, Response<JsonElement> response) {
                try {
                    if (response.isSuccessful()) {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        status = jsonObject.getString("Status");
                        rc3Code = new ArrayList<String>();
                        rc3Name = new ArrayList<String>();
                        rc3Code.add("0");
                        rc3Name.add("Select Resolution Code 3");
                        if (status.equals("Failure")) {
                            Log.d("Failure", "error");
                        } else if (status.equals("Success")) {
                            try {
                                result = jsonObject.getJSONArray("data");
                                if (result != null) {
                                    for (int i = 0; i < result.length(); i++) {
                                        JSONObject jsonData = new JSONObject(String.valueOf(result.getString(i)));
                                        Log.d("RC3Response", jsonData.toString());
                                        String code = jsonData.getString("rc_thirdId");
                                        String name = jsonData.getString("rootCauseThree");
                                        rc3Code.add(code);
                                        rc3Name.add(name);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Activity_Resolve.this, android.R.layout.simple_spinner_item, rc3Name);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        rc3.setAdapter(adapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<JsonElement> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }


    private void getRC1() {
        String authKey = "ac7b51de9d888e1458dd53d8aJAN3ba6f";
        String action = "getRCone";

        RCRequest rcRequest = new RCRequest();
        rcRequest.setAuthkey(authKey);
        rcRequest.setAction(action);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiService.getRCDetail(rcRequest);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(retrofit2.Call<JsonElement> call, Response<JsonElement> response) {
                try {
                    if (response.isSuccessful()) {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        status = jsonObject.getString("Status");
                        if (status.equals("Failure")) {
                            Log.d("Failure", "error");
                        } else if (status.equals("Success")) {
                            try {
                                result = jsonObject.getJSONArray("data");
                                if (result != null) {
                                    rc1Code = new ArrayList<String>();
                                    rc1Name = new ArrayList<String>();
                                    rc1Code.add("0");
                                    rc1Name.add("Select Resolution Code 1");
                                    for (int i = 0; i < result.length(); i++) {
                                        JSONObject jsonData = new JSONObject(String.valueOf(result.getString(i)));
                                        Log.d("RC1Response", jsonData.toString());
                                        String code = jsonData.getString("rc_oneId");
                                        String name = jsonData.getString("rootCauseOne");
                                        rc1Code.add(code);
                                        rc1Name.add(name);
                                    }
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Activity_Resolve.this, android.R.layout.simple_spinner_item, rc1Name);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    rc1.setAdapter(adapter);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<JsonElement> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }



    private void resolveButton(){
        btnResolveSubmit.setOnClickListener(v -> {
            boolean isValid = true;
            endFlag= true;

            if (rc1.getSelectedItem().toString().equals("Select Resolution Code 1")) {
                isValid = false;
                Toast.makeText(Activity_Resolve.this, "Please select Resolution Code 1", Toast.LENGTH_LONG).show();
            } else if (rc2.getSelectedItem().toString().equals("Select Resolution Code 2")) {
                isValid = false;
                Toast.makeText(Activity_Resolve.this, "Please select Resolution Code 2", Toast.LENGTH_LONG).show();
            } else if (rc3.getSelectedItem().toString().equals("Select Resolution Code 3")) {
                isValid = false;
                Toast.makeText(Activity_Resolve.this, "Please select Resolution Code 3", Toast.LENGTH_LONG).show();
            } else if (resolveContacted.getSelectedItem().toString().equals("Select Status")) {
                isValid = false;
                Toast.makeText(Activity_Resolve.this, "Please select Customer contacted or not", Toast.LENGTH_LONG).show();
            } else if (rfo.getText().toString().equals("")) {
                isValid = false;
                Toast.makeText(Activity_Resolve.this, "Please enter RFO", Toast.LENGTH_LONG).show();
            }
            if (isValid == true) {
                submitOnResolve();
                submitQuestionare();
                //activity.getSupportFragmentManager().beginTransaction().add(R.id.fregment_container, new WelcomeFragment(), WelcomeFragment.class.getSimpleName()).addToBackStack(null).commit();
            }
        });
    }

    private void submitQuestionare() {
        String authKey = "ac7b51de9d888e1458dd53d8aJAN3ba6f";
        String action = "saveQuestionerdetails";

        QuestionareList questionListRequest = new QuestionareList();
        questionListRequest.setAuthkey(authKey);
        questionListRequest.setAction(action);
        questionListRequest.setAction(itemlist.get(0));
        questionListRequest.setAction(itemlist.get(1));
        questionListRequest.setAction(itemlist.get(2));
        questionListRequest.setAction(itemlist.get(3));
        questionListRequest.setAction(itemlist.get(4));
        questionListRequest.setAction(itemlist.get(5));
        questionListRequest.setAction(SrNumber);
        questionListRequest.setAction(MainActivity.prefConfig.readName());
        questionListRequest.setAction("App");
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiService.sendQuestionare(questionListRequest);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(retrofit2.Call<CommonResponse> call, Response<CommonResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        status=response.body().getStatus();
                     /*   JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        status = jsonObject.getString("Status");*/
                        if (status.equals("Failure")) {
                            Log.d("Failure", "error");
                        } else if (status.equals("Success")) {
                            try {

                                Toast.makeText(Activity_Resolve.this,response.message(),Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<CommonResponse> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }

    private void bindChangeStatus(int isResolve) {
        ArrayList<String> caseStatus = new ArrayList<String>();
        caseStatus.add("Select Status");
        caseStatus.add("Hold");
        if (isResolve == 1) {
            caseStatus.add("Resolved");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Activity_Resolve.this, android.R.layout.simple_spinner_item, caseStatus);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        changeStatus.setAdapter(adapter);
    }


    private void submitOnResolve() {
        String authKey = "ac7b51de9d888e1458dd53d8aJAN3ba6f";
        String action = "saveRCdetails";
        String emailId = MainActivity.prefConfig.readName();
        String sr = SrNumber;
        int itemPosition = rc1.getSelectedItemPosition();
        String rc1Id = rc1Code.get(itemPosition).toString();
        itemPosition = rc2.getSelectedItemPosition();
        String rc2Id = rc2Code.get(itemPosition).toString();
        itemPosition = rc3.getSelectedItemPosition();
        String rc3Id = rc3Code.get(itemPosition).toString();
        String reason = rfo.getText().toString();
        String isContacted = resolveContacted.getSelectedItem().toString();

        SRRequest srRequest = new SRRequest();
        srRequest.setAuthkey(authKey);
        srRequest.setAction(action);
        srRequest.setEmailId(emailId);
        srRequest.setSrNumber(sr);
        srRequest.setRConeId(rc1Id);
        srRequest.setRCtwoId(rc2Id);
        srRequest.setRCthirdId(rc3Id);
        srRequest.setReasonOf(reason);
        srRequest.setResolveContacted(isContacted == "Yes" ? "true" : "false");
        srRequest.setSource("FFA App");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
       /* inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        progressOverlay.setAnimation(inAnimation);
        progressOverlay.setVisibility(View.VISIBLE);*/
        btnResolveSubmit.setEnabled(false);
        Call<JsonElement> call = apiService.getSRDetail(srRequest);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(retrofit2.Call<JsonElement> call, Response<JsonElement> response) {
               /* outAnimation = new AlphaAnimation(1f, 0f);
                outAnimation.setDuration(200);
                progressOverlay.setAnimation(outAnimation);
                progressOverlay.setVisibility(View.GONE);*/
                try {
                    if (response.isSuccessful()) {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        status = jsonObject.getString("Status");
                        if (status.equals("1")) {
                            try {
                                String result = jsonObject.getString("Response");
                                Toast.makeText(Activity_Resolve.this, result, Toast.LENGTH_LONG).show();
                                Intent i = new Intent(Activity_Resolve.this,MainActivity.class);
                                startActivity(i);
                                finish();
                               // Activity_Resolve.this.getSupportFragmentManager().beginTransaction().replace(R.id.fregment_container, new WelcomeFragment(), WelcomeFragment.class.getSimpleName()).commit();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            String result = jsonObject.getString("Message");
                            Toast.makeText(Activity_Resolve.this, result, Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (Exception e) {
                    btnResolveSubmit.setEnabled(true);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<JsonElement> call, Throwable t) {
                btnResolveSubmit.setEnabled(true);
                Log.e("RetroError", t.toString());
            }
        });
    }



    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Resolve.this);
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Take Photo")) {
                requestStoragePermission(true);
            } else if (items[item].equals("Choose from Library")) {
                requestStoragePermission(false);
            }
            else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);

                mPhotoFile = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }
        }
    }


    /**
     * Select image fro gallery
     */
    private void dispatchGalleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                try {
                    mPhotoFile = mCompressor.compressToFile(mPhotoFile);
                    Toast.makeText(this, mPhotoFile.toString(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                speed_on_wifi.setText(mPhotoFile.toString());

              //  Glide.with(Activity_Resolve.this).load(mPhotoFile).apply(new RequestOptions().centerCrop().circleCrop().placeholder(R.drawable.profile_pic_place_holder)).into(imageViewProfilePic);
            } else if (requestCode == REQUEST_GALLERY_PHOTO) {
                Uri selectedImage = data.getData();
                try {
                    mPhotoFile = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));
                    Toast.makeText(this, mPhotoFile.toString(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                speed_on_wifi.setText(mPhotoFile.toString());
               // Glide.with(Activity_Resolve.this).load(mPhotoFile).apply(new RequestOptions().centerCrop().circleCrop().placeholder(R.drawable.profile_pic_place_holder)).into(imageViewProfilePic);

            }
        }
    }

    /**
     * Requesting multiple permissions (storage and camera) at once
     * This uses multiple permission model from dexter
     * On permanent denial opens settings dialog
     */
    private void requestStoragePermission(boolean isCamera) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Dexter.withActivity(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            // check if all permissions are granted
                            if (report.areAllPermissionsGranted()) {
                                if (isCamera) {
                                    dispatchTakePictureIntent();
                                } else {
                                    dispatchGalleryIntent();
                                }
                            }
                            // check for permanent denial of any permission
                            if (report.isAnyPermissionPermanentlyDenied()) {
                                // show alert dialog navigating to Settings
                                showSettingsDialog();
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).withErrorListener(error -> Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show())
                    .onSameThread()
                    .check();
        }
    }


    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    /**
     * Create file with current timestamp name
     *
     * @return
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        return mFile;
    }

    /**
     * Get real file path from URI
     *
     * @param contentUri
     * @return
     */
    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }



//    private void button(){
//        button.setOnClickListener(v -> {
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("*/*");
//            startActivityForResult(intent, 7);
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // TODO Auto-generated method stub
//
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//
//            case 7:
//
//                if (resultCode == RESULT_OK) {
//
//                    String PathHolder = Objects.requireNonNull(data.getData()).getPath();
//
//                    Toast.makeText(Activity_Resolve.this, PathHolder, Toast.LENGTH_LONG).show();
//                    text.setText(PathHolder);
//                }
//
//                break;
//
//        }
//    }
//

}
