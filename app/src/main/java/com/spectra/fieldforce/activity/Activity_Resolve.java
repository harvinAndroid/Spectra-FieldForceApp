package com.spectra.fieldforce.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.multidex.BuildConfig;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.JsonElement;

import com.spectra.fieldforce.Model.ArtifactRequest;
import com.spectra.fieldforce.Model.CommonResponse;
import com.spectra.fieldforce.Model.QuestionList.QuestionListRequest;
import com.spectra.fieldforce.Model.RCRequest;
import com.spectra.fieldforce.Model.SRRequest;
import com.spectra.fieldforce.Model.SaveQuestionareList.Answer;
import com.spectra.fieldforce.Model.SaveQuestionareList.SaveQuestionareList;
import com.spectra.fieldforce.Model.questionAnsResponse.QuestionAnswerList;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.adapter.QuestionAnswerAdapter;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.listener.OnItemClickListener;
import com.spectra.fieldforce.utils.Constants;
import com.spectra.fieldforce.utils.FilePath;
import com.spectra.fieldforce.utils.FileUtils;
import com.spectra.fieldforce.utils.PermissionUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.spectra.fieldforce.utils.AppConstants.PERMISSION_REQUEST_CODE_READ_WRITE;

public class Activity_Resolve extends BaseActivity implements View.OnClickListener {
    Button btnResolveSubmit, btnUnifySession,btnUploadArtifacts;
    TextView sessionStatus;
    ImageView img;
    EditText rfo;
    TextView  speed_on_wifi, other_in_ml, speed_on_lan, router_position;
    Spinner rc1, rc2, rc3, resolveContacted;
    RecyclerView question_recyler_view;
    ArrayList<String> rc1Code;
    ArrayList<String> rc1Name;
    ArrayList<String> rc2Code;
    ArrayList<String> rc2Name;
    ArrayList<String> rc3Code;
    ArrayList<String> rc3Name;
    private FrameLayout progressOverlay;
    private ArrayList<QuestionAnswerList.Response> questionareResponse;
    private List<ArrayList<String>> itemlist = new ArrayList<ArrayList<String>>();
    private String srNumber, customerId;
    private String status;
    private JSONArray result;
    private String mFilepaths;
    private AlphaAnimation inAnimation;
    private AlphaAnimation outAnimation;
    private boolean startFlag, endFlag;
    private  ArrayList<Answer> item;
    String filepath,filepath1,filepath2,filepath3,str_ext1="",str_ext2="",str_ext3="",str_ext4="",strSlotType;
    private Uri uri,uri1,uri2,uri3;
    private Bitmap bitmap1,bitmap2,bitmap3,bitmap4;
    private ArrayList<String> itemlist1 = new ArrayList<>();
    private String StrSubSubType;
     private int IntCount;
     ImageView image;
    private Uri cameraFileUri;
    private String selectedMediaPath;
    private BaseActivity baseActivity;
    File mPhotoFile;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resolve);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            customerId = extras.getString("CustomerId");
            srNumber = extras.getString("SrNumber");
            strSlotType=extras.getString("SlotType");
            StrSubSubType = extras.getString("SubSubType");
        }
        item = new  ArrayList<Answer>();
        baseActivity = ((BaseActivity) this);
        init();
       //getSaveQuestionList();
        getRC1();
        getQuestionList();
        resolveButton();
        listener();
        bindContacted();
    }


    private void init() {
        btnResolveSubmit = findViewById(R.id.btnResolveSubmit);
        btnUnifySession = findViewById(R.id.btnUnifySession);
        sessionStatus = findViewById(R.id.sessionStatus);
        btnUnifySession = findViewById(R.id.btnUnifySession);
        progressOverlay = findViewById(R.id.progress_overlay);
        question_recyler_view =  findViewById(R.id.question_recyler_view);
        resolveContacted = findViewById(R.id.resolveContacted);
        speed_on_wifi = findViewById(R.id.speed_on_wifi);
        other_in_ml = findViewById(R.id.other_in_ml);
        speed_on_lan = findViewById(R.id.speed_on_lan);
        router_position = findViewById(R.id.router_position);
        btnUploadArtifacts = findViewById(R.id.btnUploadArtifacts);
        image = findViewById(R.id.image);
      //  img = findViewById(R.id.img);
        rfo = findViewById(R.id.rfo);
        rc1 = findViewById(R.id.rc1);
        rc2 = findViewById(R.id.rc2);
        rc3 = findViewById(R.id.rc3);
        progressOverlay = (FrameLayout) findViewById(R.id.progress_overlay);
        btnUploadArtifacts.setOnClickListener(this);
        speed_on_wifi.setOnClickListener(Activity_Resolve.this);
        other_in_ml.setOnClickListener(Activity_Resolve.this);
        speed_on_lan.setOnClickListener(Activity_Resolve.this);
        router_position.setOnClickListener(Activity_Resolve.this);
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
        RCRequest rcRequest = new RCRequest();
        rcRequest.setAuthkey(Constants.AUTH_KEY);
        rcRequest.setAction(Constants.GET_RC_TWO);
        rcRequest.setRC1(rc1Code);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiService.getRCDetail(rcRequest);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
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
                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject jsonData = new JSONObject(String.valueOf(result.getString(i)));
                                    Log.d("RC2Response", jsonData.toString());
                                    String code = jsonData.getString("rc_twoId");
                                    String name = jsonData.getString("rootCauseTwo");
                                    rc2Code.add(code);
                                    rc2Name.add(name);
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
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }

    private void listener() {
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
        btnUnifySession.setOnClickListener(v ->
                getUnifySession());
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE_READ_WRITE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
             // FileUtils.showFileChooser(Activity_Resolve.this);
            }
        }
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 101) {
                try {
                    uri = data.getData();
                    filepath = FilePath.getPath(Activity_Resolve.this, uri);
                    if (filepath!=null) {
                        if (FileUtils.checkExtension(Activity_Resolve.this, uri)) {
                            Uri file = Uri.fromFile(new File(filepath));
                            str_ext1 = MimeTypeMap.getFileExtensionFromUrl(file.toString());
                            speed_on_wifi.setText(filepath);
                            try {
                                bitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            displayToast(R.string.valid_formats);
                        }
                    } else {
                        displayToast(R.string.upload_files_message);
                    }
                } catch (Exception EX) {
                    EX.getStackTrace();
                }
            }else
                if (requestCode == 102) {
                    try {
                        uri1 = data.getData();
                        filepath1 = FilePath.getPath(Activity_Resolve.this, uri1);
                        if (filepath1!=null) {
                            if (FileUtils.checkExtension(Activity_Resolve.this, uri1)) {
                                Uri file = Uri.fromFile(new File(filepath1));
                                str_ext2 = MimeTypeMap.getFileExtensionFromUrl(file.toString());
                                other_in_ml.setText(filepath1);
                                try {

                                    bitmap2 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri1);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                displayToast(R.string.valid_formats);
                            }
                        } else {
                            displayToast(R.string.upload_files_message);
                        }
                    } catch (Exception EX) {
                        EX.getStackTrace();
                    }


            }else  if (requestCode == 103) {
                    try {
                        uri2 = data.getData();
                        filepath2 = FilePath.getPath(Activity_Resolve.this, uri2);
                        if (filepath2!=null) {
                            if (FileUtils.checkExtension(Activity_Resolve.this, uri2)) {
                                Uri file = Uri.fromFile(new File(filepath2));
                                str_ext3 = MimeTypeMap.getFileExtensionFromUrl(file.toString());
                                speed_on_lan.setText(filepath2);
                                try {
                                    bitmap3 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri2);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                displayToast(R.string.valid_formats);
                            }
                        } else {
                            displayToast(R.string.upload_files_message);
                        }
                    } catch (Exception EX) {
                        EX.getStackTrace();
                    }

                }else  if (requestCode == 104) {
                    try {
                        uri3 = data.getData();
                        filepath3 = FilePath.getPath(Activity_Resolve.this, uri3);
                        if (filepath3!=null) {
                            if (FileUtils.checkExtension(Activity_Resolve.this, uri3)) {
                                Uri file = Uri.fromFile(new File(filepath3));
                                str_ext4 = MimeTypeMap.getFileExtensionFromUrl(file.toString());
                                router_position.setText(filepath3);
                                try {
                                    bitmap4 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri3);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                displayToast(R.string.valid_formats);
                            }
                        } else {
                            displayToast(R.string.upload_files_message);
                        }
                    } catch (Exception EX) {
                        EX.getStackTrace();
                    }

                }/*else if (cameraFileUri != null && requestCode == 1) {

                    try {

                        Toast.makeText(this,  mPhotoFile.toString(), Toast.LENGTH_SHORT).show();
                        Glide.with(Activity_Resolve.this).load(mPhotoFile).apply(new RequestOptions().centerCrop().circleCrop().placeholder(R.drawable.button_purple)).into(image);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }*/
        }
    }

    private void getQuestionList() {
        QuestionListRequest questionListRequest = new QuestionListRequest(Constants.GET_QUESTIONARE_LIST,Constants.AUTH_KEY,strSlotType,srNumber);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<QuestionAnswerList> call = apiService.getQuestionList(questionListRequest);
        call.enqueue(new Callback<QuestionAnswerList>() {
            @Override
            public void onResponse(Call<QuestionAnswerList> call, Response<QuestionAnswerList> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatus()==0) {
                                Log.d("Failure", "error");
                            } else if (response.body().getStatus()==1) {
                                try {
                                    if (response.body() != null) {
                                        question_recyler_view.setVisibility(View.VISIBLE);
                                        questionareResponse = response.body().getResponse();
                                        IntCount = response.body().getCount();
                                        QuestionAnswerAdapter adapter = new QuestionAnswerAdapter(Activity_Resolve.this, questionareResponse, myClickListener);
                                        question_recyler_view.setHasFixedSize(true);
                                        question_recyler_view.setLayoutManager(new LinearLayoutManager(Activity_Resolve.this));
                                        question_recyler_view.setAdapter(adapter);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<QuestionAnswerList> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }


    private OnItemClickListener myClickListener = (answer) -> {
        item.add(answer);
    };

    private void getUnifySession() {
       // String action = "getCurrentSession";
    //    String canId = customerId;

        RCRequest rcRequest = new RCRequest();
        rcRequest.setAuthkey(Constants.AUTH_KEY);
        rcRequest.setAction(Constants.GET_CURRENT_SESSION);
        rcRequest.setCanId(customerId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiService.getRCDetail(rcRequest);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
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
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }


    private void getRC3(String rc2Code) {
      //  String authKey = "ac7b51de9d888e1458dd53d8aJAN3ba6f";
      //  String action = "getRCthree";

        RCRequest rcRequest = new RCRequest();
        rcRequest.setAuthkey(Constants.AUTH_KEY);
        rcRequest.setAction(Constants.GET_RC_THREE);
        rcRequest.setRC2(rc2Code);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiService.getRCDetail(rcRequest);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
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
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }


    private void getRC1() {
        RCRequest rcRequest = new RCRequest();
        rcRequest.setAuthkey(Constants.AUTH_KEY);
        rcRequest.setAction(Constants.GET_RC_ONE);
        rcRequest.setSubsubType(StrSubSubType);


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiService.getRCDetail(rcRequest);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
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
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }


    private void resolveButton() {
        btnResolveSubmit.setOnClickListener(v -> {
            boolean isValid = true;
            endFlag = true;

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
            }else if(item.size()!=IntCount){
                isValid = false;
                Toast.makeText(Activity_Resolve.this, "Please choose the answer", Toast.LENGTH_LONG).show();
            }

            if (isValid == true) {
                submitOnResolve();
                submitQuestionare();
                //activity.getSupportFragmentManager().beginTransaction().add(R.id.fregment_container, new WelcomeFragment(), WelcomeFragment.class.getSimpleName()).addToBackStack(null).commit();
            }
        });
    }

    private void submitQuestionare() {
        SaveQuestionareList questionListRequest = new SaveQuestionareList(Constants.ACTION_SAVEQUESTIONARE,item,Constants.AUTH_KEY,MainActivity.prefConfig.readName(),Constants.APP,srNumber,strSlotType);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiService.sendQuestionare(questionListRequest);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                try {
                    if (response.body() != null) {
                        if (response.body().getStatus().equals("1")) {
                            Toast.makeText(Activity_Resolve.this, "Questionare Submitted sucessfully", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(Activity_Resolve.this, "Something went wrong...", Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }


    private void submitOnResolve() {
        //String action = "saveRCdetails";
        int itemPosition = rc1.getSelectedItemPosition();
        String rc1Id = rc1Code.get(itemPosition).toString();
        itemPosition = rc2.getSelectedItemPosition();
        String rc2Id = rc2Code.get(itemPosition).toString();
        itemPosition = rc3.getSelectedItemPosition();
        String rc3Id = rc3Code.get(itemPosition).toString();
        String reason = rfo.getText().toString();
        String isContacted = resolveContacted.getSelectedItem().toString();

        SRRequest srRequest = new SRRequest();
        srRequest.setAuthkey(Constants.AUTH_KEY);
        srRequest.setAction(Constants.SAVE_RC_DETAILS);
        srRequest.setEmailId( MainActivity.prefConfig.readName());
        srRequest.setSrNumber(srNumber);
        srRequest.setRConeId(rc1Id);
        srRequest.setRCtwoId(rc2Id);
        srRequest.setRCthirdId(rc3Id);
        srRequest.setReasonOf(reason);
        if (isContacted.equals("Yes")) {
            srRequest.setResolveContacted("True");
        } else if (isContacted.equals("No")) {
            srRequest.setResolveContacted("False");
        }
        srRequest.setSource(Constants.APP);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
       /* inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        progressOverlay.setAnimation(inAnimation);
        progressOverlay.setVisibility(View.VISIBLE);*/
        btnResolveSubmit.setEnabled(false);
        Call<JsonElement> call = apiService.getSRDetail(srRequest);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
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
                                Intent i = new Intent(Activity_Resolve.this, MainActivity.class);
                                startActivity(i);
                                finish();
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
            public void onFailure(Call<JsonElement> call, Throwable t) {
                btnResolveSubmit.setEnabled(true);
                Log.e("RetroError", t.toString());
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.speed_on_wifi:
                if (PermissionUtils.checkWritePermission(Activity_Resolve.this))
                    FileUtils.showFileChooser(Activity_Resolve.this,101);
                break;
            case R.id.other_in_ml:
                if (PermissionUtils.checkWritePermission(Activity_Resolve.this))
                    FileUtils.showFileChooser(Activity_Resolve.this,102);
                break;
            case R.id.speed_on_lan:
                if (PermissionUtils.checkWritePermission(Activity_Resolve.this))
                    FileUtils.showFileChooser(Activity_Resolve.this,103);
                break;
            case R.id.router_position:
               // alertSelectImage();
                if (PermissionUtils.checkWritePermission(Activity_Resolve.this))
                    FileUtils.showFileChooser(Activity_Resolve.this,104);
                break;
            case R.id.btnUploadArtifacts:
                uploadArtifacts();
                /*a= speed_on_lan.getText().toString();
                b=speed_on_wifi.getText().toString();
                c= router_position.getText().toString();
                d=other_in_ml.getText().toString();
               // next();
             //  uploadArtifacts();
                if(speed_on_lan.getText().length()==0||speed_on_wifi.getText().length()==0||router_position.getText().length()==0||other_in_ml.getText().length()==0){
                   Toast.makeText(this,"Please Upload atleast 1 image",Toast.LENGTH_LONG).show();
                }else{
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(this);
                }*/
                break;

          //  case R.id.radioGroup:

        }
    }

    private void alertSelectImage() {
        cameraFileUri = null;
        selectedMediaPath = null;
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_select_image);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.findViewById(R.id.from_camera).setOnClickListener(v -> {
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
                    startActivityForResult(takePictureIntent, 1);

                }
            }
          /*  Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File photoFile = new File(Environment.getExternalStorageDirectory().toString(), "image" + System.currentTimeMillis() + ".jpg");
            selectedMediaPath = photoFile.getAbsolutePath();
            cameraFileUri = FileProvider.getUriForFile(baseActivity, BuildConfig.APPLICATION_ID + ".fileProvider", photoFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraFileUri);
            cameraIntent.putExtra("return-data", true);
            startActivityForResult(cameraIntent, 1);
            Log.i("path", "" + selectedMediaPath);*/
            dialog.dismiss();
        });
        dialog.findViewById(R.id.from_gallery).setOnClickListener(v -> {
            if (PermissionUtils.checkWritePermission(Activity_Resolve.this))
                FileUtils.showFileChooser(Activity_Resolve.this,104);
            dialog.dismiss();
        });
        dialog.getWindow().getAttributes().windowAnimations = R.style.SelectMediaDialogTheme;
        dialog.show();
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        return mFile;
    }


    /* public class ViewDialog {

        public void showDialog(Activity activity){
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.artifacts_dialog);

            Button btn_yes = (Button) dialog.findViewById(R.id.btn_yes);
            TextView txt_msg = (TextView) dialog.findViewById(R.id.txt_msg);
            btn_yes.setOnClickListener(v -> {
                if(speed_on_lan.getText()!=null||speed_on_wifi.getText()!=null||router_position.getText()!=null||other_in_ml.getText()!=null){
                    uploadArtifacts();
                }

            });

            Button dialogButton = (Button) dialog.findViewById(R.id.btn_no);
            dialogButton.setOnClickListener(v -> dialog.dismiss());

            dialog.show();

        }
    }
*/
   /* private void listenerArtifact(){
        String a,b,c,d;
        a= speed_on_lan.getText().toString();
        b=speed_on_wifi.getText().toString();
        c= router_position.getText().toString();
        d=other_in_ml.getText().toString();
        btnUploadArtifacts.setOnClickListener(v -> {
            if(a.equals("")||b.equals("")||c.equals("")||d.equals("")){
                Toast.makeText(Activity_Resolve.this,"Please Upload atleast 1 image",Toast.LENGTH_LONG).show();
            }else{
                ViewDialog alert = new ViewDialog();
                alert.showDialog(Activity_Resolve.this);
            }
        });
    }
*/
    private void uploadArtifacts()  {
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        progressOverlay.setAnimation(inAnimation);
        progressOverlay.setVisibility(View.VISIBLE);
        String encodedImage="",encodedImage2="",encodedImage3="", encodedImage1="";
       // String authKey = Constants.AUTH_KEY;
       // String action = AppConstants.SAVE_ALL_ARTIFACTS;

        try {
        if(str_ext1!=null && str_ext1.equals("pdf")){
            InputStream inputStream = this.getContentResolver().openInputStream(uri);
            byte[] pdfInBytes = new byte[inputStream.available()];
            inputStream.read(pdfInBytes);
             encodedImage = Base64.encodeToString(pdfInBytes, Base64.NO_WRAP);
        }else if(str_ext1!=null && bitmap1!=null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap1.compress(Bitmap.CompressFormat.JPEG,75, byteArrayOutputStream);
            byte[] imageInByte = byteArrayOutputStream.toByteArray();
             encodedImage =  Base64.encodeToString(imageInByte,Base64.NO_WRAP);
        }

            if(str_ext2!=null && str_ext2.equals("pdf")){
                InputStream inputStream1 = this.getContentResolver().openInputStream(uri1);
                byte[] pdfInBytes = new byte[inputStream1.available()];
                inputStream1.read(pdfInBytes);
                encodedImage1 = Base64.encodeToString(pdfInBytes, Base64.NO_WRAP);
            }else  if(str_ext2!=null && bitmap2!=null){
                ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
                bitmap2.compress(Bitmap.CompressFormat.JPEG,75, byteArrayOutputStream1);
                byte[] imageInByte1 = byteArrayOutputStream1.toByteArray();
                 encodedImage1 =  Base64.encodeToString(imageInByte1,Base64.NO_WRAP);
            }

            if(str_ext3!=null && str_ext3.equals("pdf")){
                InputStream inputStream2 = this.getContentResolver().openInputStream(uri2);
                byte[] pdfInBytes = new byte[inputStream2.available()];
                inputStream2.read(pdfInBytes);
                encodedImage2 = Base64.encodeToString(pdfInBytes, Base64.NO_WRAP);
            }else  if(str_ext3!=null && bitmap3!=null){
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                bitmap3.compress(Bitmap.CompressFormat.JPEG,75, byteArrayOutputStream2);
                byte[] imageInByte2 = byteArrayOutputStream2.toByteArray();
                 encodedImage2 =  Base64.encodeToString(imageInByte2,Base64.NO_WRAP);
            }

            if(str_ext4!=null && str_ext4.equals("pdf")){
                InputStream inputStream3 = this.getContentResolver().openInputStream(uri3);
                byte[] pdfInBytes = new byte[inputStream3.available()];
                inputStream3.read(pdfInBytes);
                encodedImage3 = Base64.encodeToString(pdfInBytes, Base64.NO_WRAP);
            }else  if(str_ext4!=null && bitmap4!=null){
                ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();
                bitmap4.compress(Bitmap.CompressFormat.JPEG,75, byteArrayOutputStream3);
                byte[] imageInByte3 = byteArrayOutputStream3.toByteArray();
                 encodedImage3 =  Base64.encodeToString(imageInByte3,Base64.NO_WRAP);
            }

        ArtifactRequest artifactRequest = new ArtifactRequest();
        artifactRequest.setAuthkey(Constants.AUTH_KEY);
        artifactRequest.setAction(Constants.SAVE_ALL_ARTIFACTS);
        artifactRequest.setEmailId(MainActivity.prefConfig.readName());
        artifactRequest.setSrNumber(srNumber);
        artifactRequest.setSpeed_on_wifi_extension(str_ext1);
        artifactRequest.setOther_in_ml_extension(str_ext2);
        artifactRequest.setSpeed_on_lan_extension(str_ext3);
        artifactRequest.setRouter_position_extension(str_ext4);
        artifactRequest.setSpeed_on_wifi(encodedImage);
        artifactRequest.setOther_in_ml(encodedImage1);
        artifactRequest.setSpeed_on_lan(encodedImage2);
        artifactRequest.setRouter_position(encodedImage3);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiService.UploadArtifacts(artifactRequest);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                try {
                    if (response.isSuccessful()) {
                        outAnimation = new AlphaAnimation(1f, 0f);
                        outAnimation.setDuration(200);
                        progressOverlay.setAnimation(outAnimation);
                        progressOverlay.setVisibility(View.GONE);
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        status = jsonObject.getString("Status");
                        if (status.equals("Failure")) {
                            Log.d("Failure", "error");
                        } else if (status.equals("1")) {
                            btnUploadArtifacts.setEnabled(false);
                            btnUploadArtifacts.setBackgroundDrawable(Activity_Resolve.this.getResources().getDrawable(R.drawable.gray_background));
                           Toast.makeText(Activity_Resolve.this,"Artifacts Submitted Successfully",Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                progressOverlay.setVisibility(View.GONE);
                Log.e("RetroError", t.toString());
            }
        });
        }catch (IOException ex){
            ex.getMessage();
        }
    }
}
