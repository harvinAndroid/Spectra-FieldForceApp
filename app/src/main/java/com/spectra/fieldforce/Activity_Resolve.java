package com.spectra.fieldforce;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.JsonElement;
import com.spectra.fieldforce.Model.ArtifactRequest;
import com.spectra.fieldforce.Model.CommonResponse;
import com.spectra.fieldforce.Model.QuestionListRequest;
import com.spectra.fieldforce.Model.QuestionListResponse;
import com.spectra.fieldforce.Model.QuestionareList;
import com.spectra.fieldforce.Model.RCRequest;
import com.spectra.fieldforce.Model.SRRequest;
import com.spectra.fieldforce.Model.questionAnsResponse.Ans;
import com.spectra.fieldforce.Model.questionAnsResponse.GetQuestionAnsList;
import com.spectra.fieldforce.Model.questionAnsResponse.Question;
import com.spectra.fieldforce.Model.questionAnsResponse.QuestionareListResponse;
import com.spectra.fieldforce.Model.questionAnsResponse.QuestionareResponse;
import com.spectra.fieldforce.utils.FilePath;
import com.spectra.fieldforce.utils.FileUtils;
import com.spectra.fieldforce.utils.PermissionUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
    private ArrayList<QuestionListResponse.Data> questionList;
    private ArrayList<QuestionareResponse.Response> questionareResponse;
    private List<QuestionareResponse.question> questionareQuestions;
    private ArrayList<QuestionareResponse.ans> questionareAnswers;
    private ArrayList<String> itemlist = new ArrayList<>();
    private String srNumber, customerId;
    private String status;
    private JSONArray result;
    private String mFilepaths;
    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;
    private FrameLayout progressOverlay;
    private boolean startFlag, endFlag;
    String filepath,filepath1,filepath2,filepath3,str_ext1="",str_ext2="",str_ext3="",str_ext4="",strSlotType;
    Uri uri,uri1,uri2,uri3;
    String a,b,c,d;
    Bitmap bitmap1,bitmap2,bitmap3,bitmap4;
    private TextView tv_question,tv_question1,tv_question13,tv_question14,tv_question50,tv_question60;
    private RadioButton radioButton1,radioButton2,radioButton3,radioButton11,radioButton12,radioButton17,
            radioButton13,radioButton14,radioButton15,radioButton40,radioButton41,radioButton42,
            radioButton51,radioButton52,radioButton53,radioButton61,radioButton62,radioButton63;
    private RadioGroup radioGroup,radioGroup1,radioGroup13,radioGroup14,radioGroup51,radioGroup61;
    private ConstraintLayout constraint_list;
    private ArrayList<String> itemlist1 = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resolve);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            customerId = extras.getString("CustomerId");
            srNumber = extras.getString("SrNumber");
            strSlotType=extras.getString("SlotType");
        }
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
      //  img = findViewById(R.id.img);
        rfo = findViewById(R.id.rfo);
        rc1 = findViewById(R.id.rc1);
        rc2 = findViewById(R.id.rc2);
        rc3 = findViewById(R.id.rc3);
        btnUploadArtifacts.setOnClickListener(this);
        speed_on_wifi.setOnClickListener(Activity_Resolve.this);
        other_in_ml.setOnClickListener(Activity_Resolve.this);
        speed_on_lan.setOnClickListener(Activity_Resolve.this);
        router_position.setOnClickListener(Activity_Resolve.this);
       /* tv_question = findViewById(R.id.tv_question);
        tv_question1 = findViewById(R.id.tv_question1);
        tv_question13 = findViewById(R.id.tv_question13);
        tv_question14 = findViewById(R.id.tv_question14);
        tv_question50 = findViewById(R.id.tv_question50);
        tv_question60 =  findViewById(R.id.tv_question60);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton11 = findViewById(R.id.radioButton11);
        radioButton12 = findViewById(R.id.radioButton12);
        radioButton17 = findViewById(R.id.radioButton17);
        radioButton13 = findViewById(R.id.radioButton13);
        radioButton14 = findViewById(R.id.radioButton14);
        radioButton15 =  findViewById(R.id.radioButton15);
        radioButton40 = findViewById(R.id.radioButton40);
        radioButton41 = findViewById(R.id.radioButton41);
        radioButton42 = findViewById(R.id.radioButton42);
        radioButton51 = findViewById(R.id.radioButton51);
        radioButton52 = findViewById(R.id.radioButton52);
        radioButton53 = findViewById(R.id.radioButton53);
        radioButton61 = findViewById(R.id.radioButton61);
        radioButton62 = findViewById(R.id.radioButton62);
        radioButton63 = findViewById(R.id.radioButton63);
        constraint_list = findViewById(R.id.constraint_list);*/

      /*  radioGroup = findViewById(R.id.radioGroup);
        radioGroup1 = findViewById(R.id.radioGroup1);
        radioGroup13 = findViewById(R.id.radioGroup13);
        radioGroup14 = findViewById(R.id.radioGroup14);
        radioGroup51 = findViewById(R.id.radioGroup51);
        radioGroup51 = findViewById(R.id.radioGroup51);
        radioGroup61 = findViewById(R.id.radioGroup61);*/

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
       // String authKey = "ac7b51de9d888e1458dd53d8aJAN3ba6f";
        String action = "getRCtwo";

        RCRequest rcRequest = new RCRequest();
        rcRequest.setAuthkey(Constants.AUTH_KEY);
        rcRequest.setAction(action);
        rcRequest.setRC1(rc1Code);

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
            public void onFailure(retrofit2.Call<JsonElement> call, Throwable t) {
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
        /*radioGroup.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) (group, checkedId) -> {
            RadioButton rb = (RadioButton) group.findViewById(checkedId);
            //  String id = question.get(position).g();
            if (null != rb && checkedId > -1) {
                String ans = rb.getText().toString();
                switch (ans) {
                    case "NA":
                        itemlist1.add("-1");
                        break;
                    case "NO":
                        itemlist1.add("0");
                        break;
                    case "YES":
                        itemlist1.add("1");
                        break;
                }
            }
        });

        radioGroup1.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) (group, checkedId) -> {
            RadioButton rb = (RadioButton) group.findViewById(checkedId);
            //  String id = question.get(position).g();
            if (null != rb && checkedId > -1) {
                String ans = rb.getText().toString();
                switch (ans) {
                    case "NA":
                        itemlist1.add("-1");
                        break;
                    case "NO":
                        itemlist1.add("0");
                        break;
                    case "YES":
                        itemlist1.add("1");
                        break;
                }
            }
        });

        radioGroup13.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) (group, checkedId) -> {
            RadioButton rb = (RadioButton) group.findViewById(checkedId);
            //  String id = question.get(position).g();
            if (null != rb && checkedId > -1) {
                String ans = rb.getText().toString();
                switch (ans) {
                    case "NA":
                        itemlist1.add("-1");
                        break;
                    case "NO":
                        itemlist1.add("0");
                        break;
                    case "YES":
                        itemlist1.add("1");
                        break;
                }
            }
        });


        radioGroup14.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) (group, checkedId) -> {
            RadioButton rb = (RadioButton) group.findViewById(checkedId);
            //  String id = question.get(position).g();
            if (null != rb && checkedId > -1) {
                String ans = rb.getText().toString();
                switch (ans) {
                    case "NA":
                        itemlist1.add("-1");
                        break;
                    case "NO":
                        itemlist1.add("0");
                        break;
                    case "YES":
                        itemlist1.add("1");
                        break;
                }
            }
        });


        radioGroup51.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) (group, checkedId) -> {
            RadioButton rb = (RadioButton) group.findViewById(checkedId);
            //  String id = question.get(position).g();
            if (null != rb && checkedId > -1) {
                String ans = rb.getText().toString();
                switch (ans) {
                    case "NA":
                        itemlist1.add("-1");
                        break;
                    case "NO":
                        itemlist1.add("0");
                        break;
                    case "YES":
                        itemlist1.add("1");
                        break;
                }
            }
        });


        radioGroup61.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) (group, checkedId) -> {
            RadioButton rb = (RadioButton) group.findViewById(checkedId);
            //  String id = question.get(position).g();
            if (null != rb && checkedId > -1) {
                String ans = rb.getText().toString();
                switch (ans) {
                    case "NA":
                        itemlist1.add("-1");
                        break;
                    case "NO":
                        itemlist1.add("0");
                        break;
                    case "YES":
                        itemlist1.add("1");
                        break;
                }
            }
        });*/

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

                }
        }
    }


   /* private void getSaveQuestionList() {
        GetQuestionAnsList questionAnsList = new GetQuestionAnsList(Constants.ACTION_GETSAVEQUESTIONARE,Constants.AUTH_KEY,strSlotType,srNumber);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<QuestionareListResponse> call = apiService.getSaveQuestionAnsList(questionAnsList);
        call.enqueue(new Callback<QuestionareListResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(retrofit2.Call<QuestionareListResponse> call, Response<QuestionareListResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                           if(response.body().getStatus()==1) {
                               if (response.body() != null) {
                                  // constraint_list.setVisibility(View.VISIBLE);
                                   tv_question.setText("1. "+response.body().getResponse().getQuestion().get(0).getInspection());
                                   tv_question1.setText("2. "+response.body().getResponse().getQuestion().get(1).getInspection());
                                   tv_question13.setText("3. "+response.body().getResponse().getQuestion().get(2).getInspection());
                                   tv_question14.setText("4. "+response.body().getResponse().getQuestion().get(3).getInspection());
                                   tv_question50.setText("5. "+response.body().getResponse().getQuestion().get(4).getInspection());
                                   tv_question60.setText("6. "+response.body().getResponse().getQuestion().get(5).getInspection());

                                   if(response.body().getResponse().getAns().get(0).getQuesId_1().equals("1")){
                                       radioButton1.setChecked(true);
                                   }else  if(response.body().getResponse().getAns().get(0).getQuesId_1().equals("0")) {
                                       radioButton2.setChecked(true);
                                   } else  if(response.body().getResponse().getAns().get(0).getQuesId_1().equals("-1")){
                                       radioButton3.setChecked(true);
                                   }

                                   if(response.body().getResponse().getAns().get(0).getQuesId_2().equals("1")){
                                       radioButton11.setChecked(true);
                                   }else  if(response.body().getResponse().getAns().get(0).getQuesId_2().equals("0")) {
                                       radioButton12.setChecked(true);
                                   } else  if(response.body().getResponse().getAns().get(0).getQuesId_2().equals("-1")){
                                       radioButton17.setChecked(true);
                                   }

                                   if(response.body().getResponse().getAns().get(0).getQuesId_3().equals("1")){
                                       radioButton13.setChecked(true);
                                   }else  if(response.body().getResponse().getAns().get(0).getQuesId_3().equals("0")) {
                                       radioButton14.setChecked(true);
                                   } else  if(response.body().getResponse().getAns().get(0).getQuesId_3().equals("-1")){
                                       radioButton15.setChecked(true);
                                   }

                                   if(response.body().getResponse().getAns().get(0).getQuesId_4().equals("1")){
                                       radioButton40.setChecked(true);
                                   }else  if(response.body().getResponse().getAns().get(0).getQuesId_4().equals("0")) {
                                       radioButton41.setChecked(true);
                                   } else  if(response.body().getResponse().getAns().get(0).getQuesId_4().equals("-1")){
                                       radioButton42.setChecked(true);
                                   }

                                   if(response.body().getResponse().getAns().get(0).getQuesId_5().equals("1")){
                                       radioButton51.setChecked(true);
                                   }else  if(response.body().getResponse().getAns().get(0).getQuesId_5().equals("0")) {
                                       radioButton52.setChecked(true);
                                   } else  if(response.body().getResponse().getAns().get(0).getQuesId_5().equals("-1")){
                                       radioButton53.setChecked(true);
                                   }

                                   if(response.body().getResponse().getAns().get(0).getQuesId_6().equals("1")){
                                       radioButton61.setChecked(true);
                                   }else  if(response.body().getResponse().getAns().get(0).getQuesId_6().equals("0")) {
                                       radioButton62.setChecked(true);
                                   } else  if(response.body().getResponse().getAns().get(0).getQuesId_6().equals("-1")){
                                       radioButton63.setChecked(true);
                                   }


                               }
                           }else{

                               Toast.makeText(Activity_Resolve.this,"Something went wrong",Toast.LENGTH_LONG).show();
                           }
                        }else{

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<QuestionareListResponse> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }
*/

    private void getQuestionList() {
        String action = "getAllQuestioner";

        QuestionListRequest questionListRequest = new QuestionListRequest();
        questionListRequest.setAuthkey(Constants.AUTH_KEY);
        questionListRequest.setAction(action);
        questionListRequest.setType(strSlotType);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<QuestionListResponse> call = apiService.getQuestionList(questionListRequest);
        call.enqueue(new Callback<QuestionListResponse>() {
            @Override
            public void onResponse(retrofit2.Call<QuestionListResponse> call, Response<QuestionListResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            status = response.body().getStatus();
                        }
                        if (status.equals("Failure")) {
                            Log.d("Failure", "error");
                        } else if (status.equals("Success")) {
                            try {
                                if (response.body() != null) {
                                    question_recyler_view.setVisibility(View.VISIBLE);
                                    questionList = response.body().getData();
                                    QuestionAnswerAdapter adapter = new QuestionAnswerAdapter(Activity_Resolve.this, questionList, myClickListener);
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


    private OnItemClickListener myClickListener = (tag, questionid) -> {
        itemlist.add(tag.get(0));
    };

    private void getUnifySession() {
        String action = "getCurrentSession";
        String canId = customerId;

        RCRequest rcRequest = new RCRequest();
        rcRequest.setAuthkey(Constants.AUTH_KEY);
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

        RCRequest rcRequest = new RCRequest();
        rcRequest.setAuthkey(authKey);
        rcRequest.setAction(action);
        rcRequest.setRC2(rc2Code);

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
            }else if(itemlist.size()==0||itemlist.size()==1||itemlist.size()==2||itemlist.size()==3||itemlist.size()==4||itemlist.size()==5){
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

   /* private void submitQuestionare1() {
        QuestionareList questionListRequest = new QuestionareList();
        questionListRequest.setAuthkey(Constants.AUTH_KEY);
        questionListRequest.setAction(Constants.ACTION_SAVEQUESTIONARE);
        questionListRequest.setQuesId_1(itemlist1.get(0));
        questionListRequest.setQuesId_2(itemlist1.get(1));
        questionListRequest.setQuesId_3(itemlist1.get(2));
        questionListRequest.setQuesId_4(itemlist1.get(3));
        questionListRequest.setQuesId_5(itemlist1.get(4));
        questionListRequest.setQuesId_6(itemlist1.get(5));
        questionListRequest.setSrNumber(srNumber);
        questionListRequest.setEmailId(MainActivity.prefConfig.readName());
        questionListRequest.setSource("App");
        questionListRequest.setSrType(strSlotType);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiService.sendQuestionare(questionListRequest);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(retrofit2.Call<CommonResponse> call, Response<CommonResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        Toast.makeText(Activity_Resolve.this, "Questionare Submitted sucessfully", Toast.LENGTH_LONG).show();
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
*/

    private void submitQuestionare() {
        QuestionareList questionListRequest = new QuestionareList();
        questionListRequest.setAuthkey(Constants.AUTH_KEY);
        questionListRequest.setAction(Constants.ACTION_SAVEQUESTIONARE);
        questionListRequest.setQuesId_1(itemlist.get(0));
        questionListRequest.setQuesId_2(itemlist.get(1));
        questionListRequest.setQuesId_3(itemlist.get(2));
        questionListRequest.setQuesId_4(itemlist.get(3));
        questionListRequest.setQuesId_5(itemlist.get(4));
        questionListRequest.setQuesId_6(itemlist.get(5));
        questionListRequest.setSrNumber(srNumber);
        questionListRequest.setEmailId(MainActivity.prefConfig.readName());
        questionListRequest.setSource("App");
        questionListRequest.setSrType(strSlotType);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiService.sendQuestionare(questionListRequest);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(retrofit2.Call<CommonResponse> call, Response<CommonResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        Toast.makeText(Activity_Resolve.this, "Questionare Submitted sucessfully", Toast.LENGTH_LONG).show();
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


    private void submitOnResolve() {
        String action = "saveRCdetails";
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
        srRequest.setAction(action);
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
            public void onFailure(retrofit2.Call<JsonElement> call, Throwable t) {
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

    private void listenerArtifact(){
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
        String encodedImage="",encodedImage2="",encodedImage3="";
        String encodedImage1="";
        String authKey = "ac7b51de9d888e1458dd53d8aJAN3ba6f";
        String action = "saveAllArtifactsSR";

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
        artifactRequest.setAuthkey(authKey);
        artifactRequest.setAction(action);
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
            public void onResponse(retrofit2.Call<JsonElement> call, Response<JsonElement> response) {
                try {
                    if (response.isSuccessful()) {
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
            public void onFailure(retrofit2.Call<JsonElement> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
        }catch (IOException ex){
            ex.getMessage();
        }
    }
}
