package com.spectra.fieldforce.salesapp.activity;

import static com.spectra.fieldforce.utils.AppConstants.REQUEST_CAMERA_PERMISSION_ONE;
import static com.spectra.fieldforce.utils.AppConstants.REQUEST_CODE_ONE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.spectra.fieldforce.BuildConfig;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.activity.BaseActivity;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.databinding.DocCafFragBinding;
import com.spectra.fieldforce.salesapp.adapter.DocAdapter;
import com.spectra.fieldforce.salesapp.model.AttachDoc;
import com.spectra.fieldforce.salesapp.model.CafRequest;
import com.spectra.fieldforce.salesapp.model.DeleteProductResponse;
import com.spectra.fieldforce.salesapp.model.DocumentData;
import com.spectra.fieldforce.salesapp.model.DocumentRequired;
import com.spectra.fieldforce.salesapp.model.GetDocCafResponse;
import com.spectra.fieldforce.salesapp.model.UploadDocRequest;
import com.spectra.fieldforce.utils.AppConstants;
import com.spectra.fieldforce.utils.Constants;
import com.spectra.fieldforce.utils.FilePath;
import com.spectra.fieldforce.utils.FileUtils;
import com.spectra.fieldforce.utils.PermissionUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DocumentSafActivity extends BaseActivity {
    DocCafFragBinding binding;
    Uri cameraFileUri;
    String selectedMediaPath,currentImagePath,filepath,str_ext1="",strCafId,status,strSafId;
    BaseActivity baseActivity;
     DocAdapter docAdapter;
    private ArrayList<AttachDoc> docResponses;
    Uri uri;
    private  DocumentData data_image;
    private AlphaAnimation inAnimation;
    private AlphaAnimation outAnimation;
   /* private  ViewDocumentAdapter myBucketListAdapter;*/
    private String userName,password;
    Bitmap bitmap1;
    ArrayList<DocumentData> name;
     ArrayList<String> mFilepaths;
    String encodedImage="";
    Boolean chtann,chk_tin,chk_caf,chk_po,chk_apnic,chk_photo,chk_osp,chk_netwrk,chk_adproof,chk_pan,chk_deed;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.doc_caf_frag);
        baseActivity = ((BaseActivity) this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
           // strOppId = extras.getString("OppId");
            status= extras.getString("Status");
            strSafId= extras.getString("SafId");
        }
        binding.etCafnum.setVisibility(View.GONE);
        binding.chkCaf.setVisibility(View.GONE);
        SharedPreferences sp1=this.getSharedPreferences("Login",0);
        userName =sp1.getString("UserName", null);
        password = sp1.getString("Password", null);
        mFilepaths = new ArrayList<>();
        binding.tlbrdoc.tvLang.setText(AppConstants.SAF);
        camera();
        getDocumentDetails();

        binding.etAttachfile.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                String att =  Objects.requireNonNull(binding.etAttachfile.getText()).toString();
                if(!att.isEmpty()){
                    Toast.makeText(DocumentSafActivity.this,"Please wait while Uploading....",Toast.LENGTH_LONG).show();
                   UploadDoc();
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

        binding.savedoc.setOnClickListener(view -> {
            updateDoc();
        });

        binding.tlbrdoc.rlBack.setOnClickListener(view -> back());
    }

    private void inProgress(){
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.docprogressLayout.progressOverlay.setAnimation(inAnimation);
        binding.docprogressLayout.progressOverlay.setVisibility(View.VISIBLE);
    }

    private void UploadDoc(){
        try {
            if (str_ext1 != null && ((str_ext1.equals(".pdf"))||str_ext1.equals(".doc")||str_ext1.equals(".docx"))) {
                InputStream inputStream = this.getContentResolver().openInputStream(uri);
                byte[] pdfInBytes = new byte[inputStream.available()];
                inputStream.read(pdfInBytes);
                encodedImage = Base64.encodeToString(pdfInBytes, Base64.NO_WRAP);
                Calendar c = Calendar.getInstance();
                int seconds = c.get(Calendar.SECOND);
               // String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
              //  currentImagePath =(seconds)+"/"+ "file";
                currentImagePath = "file"+"doc("+(seconds)+"/)";

                // str_ext1=".pdf";
            } else if (str_ext1 != null && bitmap1 != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap1.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
                byte[] imageInByte = byteArrayOutputStream.toByteArray();
                encodedImage = Base64.encodeToString(imageInByte, Base64.NO_WRAP);
                str_ext1=".jpg";
                Calendar c = Calendar.getInstance();
                int seconds = c.get(Calendar.SECOND);
             //   String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
                currentImagePath =  "file"+"img("+(seconds)+"/)";
            }
         /*   String str = binding.etAttachfile.getText().toString();
            String currentString = str;
            String[] separated = currentString.split("/file");
            currentImagePath = separated[1];*/
            data_image = new DocumentData(encodedImage, currentImagePath+str_ext1);

        //    data_image = new DocumentData(currentImagePath, encodedImage+str_ext1);
            Log.e("encoded",encodedImage);
            Log.e("str_ext1",str_ext1);

        }catch (Exception ex){
            ex.getMessage();
        }
        name = new ArrayList<>();
        name.add(data_image);
        Log.e("Path",currentImagePath);
        Log.e("Ext",str_ext1);
        updateDoc();
    }

    private void outProgress(){
        outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        binding.docprogressLayout.progressOverlay.setAnimation(outAnimation);
        binding.docprogressLayout.progressOverlay.setVisibility(View.GONE);
    }


    private void getDocumentDetails() {
        inProgress();
        CafRequest cafRequest = new CafRequest(Constants.GETDOCUMENT,Constants.AUTH_KEY,"",
                "",password,userName,strSafId);
         ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GetDocCafResponse> call = apiService.getDoc(cafRequest);
        call.enqueue(new Callback<GetDocCafResponse>() {
            @Override
            public void onResponse(Call<GetDocCafResponse> call, Response<GetDocCafResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        outProgress();
                        if (response.body().getStatus().equals("Success")) {
                            binding.setCafdoc(response.body().getResponse().getData());
                            binding.etSafnum.setText(strSafId);

                          //  strSafId =  response.body().getResponse().getData().getSafNo();


                            if(response.body().getResponse().getData().getAccordingtoFirmType().getTanNo().equals("1")){
                                binding.ckTan.setChecked(true);
                            }
                            if(response.body().getResponse().getData().getAccordingtoFirmType().getTinNo().equals("1")){
                                binding.chkTin.setChecked(true);
                            }
                            if(response.body().getResponse().getData().getAccordingtoFirmType().getCafNo().equals("1")){
                                binding.chkCaf.setChecked(true);
                            }
                            if(response.body().getResponse().getData().getAccordingtoFirmType().getPurchaseOrder().equals("1")){
                                binding.chkPo.setChecked(true);
                            }
                            if(response.body().getResponse().getData().getAccordingtoFirmType().getApnic().equals("1")){
                                binding.chkApnic.setChecked(true);
                            }
                            if(response.body().getResponse().getData().getAccordingtoFirmType().getPhotoId().equals("1")){
                                binding.chkPhoto.setChecked(true);
                            }
                            if(response.body().getResponse().getData().getAccordingtoFirmType().getOspDeclaration().equals("1")){
                                binding.chkOsp.setChecked(true);
                            }
                            if(response.body().getResponse().getData().getAccordingtoFirmType().getNtwDiagram().equals("1")){
                                binding.chkNetwrk.setChecked(true);
                            }
                            if(response.body().getResponse().getData().getAccordingtoFirmType().getAddressProofCom().equals("1")){
                                binding.chkAdproof.setChecked(true);
                            }
                            if(response.body().getResponse().getData().getAccordingtoFirmType().getPanCard().equals("1")){
                                binding.chkPan.setChecked(true);
                            }
                            if(response.body().getResponse().getData().getAccordingtoFirmType().getListDirector().equals("1")){
                                binding.chkDeed.setChecked(true);
                            }
                            binding.rvDocView.setHasFixedSize(true);
                            binding.rvDocView.setLayoutManager(new LinearLayoutManager(DocumentSafActivity.this));
                            docResponses = response.body().getResponse().getAttachDocs();
                            docAdapter = new DocAdapter(DocumentSafActivity.this,docResponses);

                            binding.rvDocView.setAdapter(docAdapter);

                            binding.etRemark.setText(response.body().getResponse().getData().getAccordingtoFirmType().getRemark());
                            if((status.equals("Verification Pending"))||(status.equals("Approved"))||(status.equals("Installation pending"))||
                                    (status.equals("Provisioning in Progress"))||(status.equals("CPE dispatched"))||(status.equals("CPE delivered @ Customer location"))
                                    ||(status.equals("Completed"))||(status.equals("Order accepted by Customer"))){
                                lock();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetDocCafResponse> call, Throwable t) {
                binding.docprogressLayout.progressOverlay.setVisibility(View.GONE);
                Log.e("RetroError", t.toString());
            }
        });
    }



    private void lock(){
            binding.ckTan.setEnabled(false);
            binding.chkTin.setEnabled(false);
            binding.chkCaf.setEnabled(false);
            binding.chkPo.setEnabled(false);
            binding.chkApnic.setEnabled(false);
            binding.chkPhoto.setEnabled(false);
            binding.chkOsp.setEnabled(false);
            binding.chkNetwrk.setEnabled(false);
            binding.chkAdproof.setEnabled(false);
            binding.chkPan.setEnabled(false);
            binding.chkDeed.setEnabled(false);
            binding.etAttachfile.setFocusable(false);
            binding.etAttachfile.setEnabled(false);
            binding.savedoc.setVisibility(View.GONE);
    }


    private void updateDoc() {
        inProgress();
        if(binding.ckTan.isChecked()){
            chtann=true;
        }else{
            chtann=false;
        }
        if(binding.chkTin.isChecked()){
            chk_tin=true;
        }else{
            chk_tin=false;
        }
        if(binding.chkCaf.isChecked()){
            chk_caf=true;
        }else{
            chk_caf=false;
        }
        if(binding.chkPo.isChecked()){
            chk_po=true;
        }else{
            chk_po=false;
        }
        if(binding.chkApnic.isChecked()){
            chk_apnic=true;
        }else{
            chk_apnic=false;
        }
        if(binding.chkPhoto.isChecked()){
            chk_photo=true;
        }else{
            chk_photo=false;
        }
        if(binding.chkOsp.isChecked()){
            chk_osp=true;
        }else{
            chk_osp=false;
        }
        if(binding.chkNetwrk.isChecked()){
            chk_netwrk=true;
        }else{
            chk_netwrk=false;
        }
        if(binding.chkAdproof.isChecked()){
            chk_adproof=true;
        }else{
            chk_adproof=false;
        }
        if(binding.chkDeed.isChecked()){
            chk_deed=true;
        }else{
            chk_deed=false;
        }
        if(binding.chkPan.isChecked()){
            chk_pan=true;
        }else{
            chk_pan=false;
        }
        Log.e("SafNum",strSafId);

        DocumentRequired doc = new DocumentRequired(chk_adproof,chk_apnic,chk_caf,true,chk_pan,
                name,"","", chk_netwrk,chk_osp, chk_po,
                chk_deed,chk_photo,"","","",chtann, chk_tin);

        UploadDocRequest uploadDocRequest = new UploadDocRequest(Constants.UPDATEDOCUMENT,Constants.AUTH_KEY,
                "","",doc,"",password,"",userName,/*"SAF_124"*/strSafId);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DeleteProductResponse> call = apiService.uploadDoc(uploadDocRequest);
        call.enqueue(new Callback<DeleteProductResponse>() {
            @Override
            public void onResponse(Call<DeleteProductResponse> call, Response<DeleteProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    outProgress();
                    try {
                        String msg = response.body().getResponse().getMessage();
                        if (response.body().getStatus().equals("Success")) {
                            Toast.makeText(DocumentSafActivity.this,msg,Toast.LENGTH_SHORT).show();
                            next();
                        }else{
                            Toast.makeText(DocumentSafActivity.this,msg,Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<DeleteProductResponse> call, Throwable t) {
                binding.docprogressLayout.progressOverlay.setVisibility(View.GONE);
                Log.e("RetroError", t.toString());
            }
        });
    }

    private void next(){
        Intent intent = new Intent(DocumentSafActivity.this, DocumentSafActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("SafId",strSafId );
        bundle.putString("Status", status);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }


    private void back(){
        Intent intent = new Intent(DocumentSafActivity.this, SAFActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("SafId",strSafId );
       // bundle.putString("OppId", strOppId);
        bundle.putString("Status", status);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void camera(){
        binding.etAttachfile.setOnClickListener(view -> checkPermission(Manifest.permission.CAMERA, REQUEST_CAMERA_PERMISSION_ONE));
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] { permission }, requestCode);
        }
        else {
            alertSelectImage(REQUEST_CAMERA_PERMISSION_ONE,REQUEST_CODE_ONE);
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    private void alertSelectImage(int requestCameraPermission, int requestCode) {
        cameraFileUri = null;
        selectedMediaPath = null;
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_select_image);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.findViewById(R.id.from_camera).setOnClickListener(v -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(cameraIntent.resolveActivity(getPackageManager())!=null) {
                File imageFile = null;
                try {
                    imageFile = getImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (imageFile != null) {
                    Uri imageUri = FileProvider.getUriForFile(this,
                            BuildConfig.APPLICATION_ID + ".provider", imageFile);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(cameraIntent, requestCameraPermission);
                }
            }
            dialog.dismiss();
        });
        dialog.findViewById(R.id.from_gallery).setOnClickListener(v -> {
            if (PermissionUtils.checkWritePermission(this))
                FileUtils.showFileChooser(this,requestCode);
            dialog.dismiss();
        });
        dialog.getWindow().getAttributes().windowAnimations = R.style.SelectMediaDialogTheme;
        dialog.show();
    }

    private File getImageFile() throws IOException {
        // Create an image file name
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = /*"jpg_"+*/timeStamp+ "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        currentImagePath = mFile.getAbsolutePath();
        return mFile;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_ONE) {
                try {
                    uri = data.getData();
                    filepath = FilePath.getPath(DocumentSafActivity.this, uri);
                    if (filepath!=null) {
                        if (FileUtils.checkExtension(DocumentSafActivity.this, uri)) {
                            if(filepath.contains("pdf")){
                                str_ext1=".pdf";
                            }else if(filepath.contains("doc")){
                                str_ext1=".doc";
                            }else if(filepath.contains("docx")){
                                str_ext1=".docx";
                            }else if(filepath.contains("xl")){
                                str_ext1=".docx";
                            }
                            String[] filePath = { MediaStore.Images.Media.DATA };
                            Cursor cursor = getContentResolver().query(uri, filePath, null, null, null);
                            cursor.moveToFirst();
                            @SuppressLint("Range")
                            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                            bitmap1 = BitmapFactory.decodeFile(filepath, options);
                            binding.etAttachfile.setText(filepath);

                           // str_ext1=".jpg";
                            // Do something with the bitmap
                            // At the end remember to close the cursor or you will end with the RuntimeException!
                            cursor.close();
                        } else {
                            displayToast(R.string.valid_formats);
                        }
                    } else {
                        displayToast(R.string.upload_files_message);
                    }
                } catch (Exception EX) {
                    EX.getStackTrace();
                }
            }else  if ( requestCode == REQUEST_CAMERA_PERMISSION_ONE) {
                try {
                    bitmap1 = BitmapFactory.decodeFile(currentImagePath);
                    //   Toast.makeText(this,  currentImagePath.toString(), Toast.LENGTH_SHORT).show();
                    binding.etAttachfile.setText(currentImagePath);
                    str_ext1 = "jpg";
                    //  image.setImageBitmap(bitmap5);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }



    @Override
    public void onBackPressed() {
        back();
    }
}


