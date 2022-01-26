package com.spectra.fieldforce.salesapp.activity;

import static com.spectra.fieldforce.utils.AppConstants.REQUEST_CAMERA_PERMISSION_ONE;
import static com.spectra.fieldforce.utils.AppConstants.REQUEST_CODE_ONE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.spectra.fieldforce.BuildConfig;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.activity.BaseActivity;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.databinding.DocCafFragBinding;
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
import java.util.Date;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocumentCafAct extends BaseActivity {
    DocCafFragBinding binding;
    Uri cameraFileUri;
    String selectedMediaPath,currentImagePath,filepath,str_ext1="",strCafId,strOppId;
    BaseActivity baseActivity;
    Uri uri;
    Bitmap bitmap1,bitmap5;
    ArrayList<DocumentData> name;
     ArrayList<String> mFilepaths;
    ArrayList<String> img;
    String encodedImage="";
    Boolean chtann,chk_tin,chk_caf,chk_po,chk_apnic,chk_photo,chk_osp,chk_netwrk,chk_adproof,chk_pan,chk_deed;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.doc_caf_frag);
        baseActivity = ((BaseActivity) this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            strCafId = extras.getString("CafId");
            strOppId = extras.getString("OppId");
        }
        mFilepaths = new ArrayList<>();
      //  binding.tlbrdoc.rlBack.setOnClickListener(this);
        binding.tlbrdoc.tvLang.setText(AppConstants.Caf);
        camera();
        getDocumentDetails();
        binding.savedoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!binding.ckTan.isChecked()){
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

                updateDoc();
            }
        });
        binding.tlbrdoc.rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });

    }

    private void getDocumentDetails() {
        CafRequest cafRequest = new CafRequest(Constants.GETDOCUMENT,Constants.AUTH_KEY,strCafId,
                strOppId,"Target@2021#@","manager1");
         ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GetDocCafResponse> call = apiService.getDoc(cafRequest);
        call.enqueue(new Callback<GetDocCafResponse>() {
            @Override
            public void onResponse(Call<GetDocCafResponse> call, Response<GetDocCafResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {

                        if (response.body().getStatus().equals("Success")) {
                            binding.setCafdoc(response.body().getResponse().getData());
                            strCafId = response.body().getResponse().getData().getCafId();
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
                            /*String status = response.body().getResponse().getData().getVerificationStatus();
                            if(status.equals("111260000")){
                                lock();
                            }*/
                    }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetDocCafResponse> call, Throwable t) {
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
    }


    private void updateDoc() {
        DocumentRequired doc = new DocumentRequired(chk_adproof,chk_apnic,chk_caf,true,chk_pan,
                name,"","", chk_netwrk,chk_osp, chk_po,
                chk_deed,chk_photo,"","","",chtann, chk_tin);

        UploadDocRequest uploadDocRequest = new UploadDocRequest(Constants.UPDATEDOCUMENT,Constants.AUTH_KEY,
                strCafId,"",doc,"","Target@2021#@","","manager1");
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DeleteProductResponse> call = apiService.uploadDoc(uploadDocRequest);
        call.enqueue(new Callback<DeleteProductResponse>() {
            @Override
            public void onResponse(Call<DeleteProductResponse> call, Response<DeleteProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String msg = response.body().getResponse().getMessage();
                        if (response.body().getStatus().equals("Success")) {
                            Toast.makeText(DocumentCafAct.this,msg,Toast.LENGTH_SHORT).show();
                            next();
                        }else{
                            Toast.makeText(DocumentCafAct.this,msg,Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<DeleteProductResponse> call, Throwable t) {
                Log.e("RetroError", t.toString());
            }
        });
    }

    private void next(){
        Intent intent = new Intent(DocumentCafAct.this, CAFActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("CafId",strCafId );
        bundle.putString("OppId", strOppId);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void camera(){
        binding.etAttachfile.setOnClickListener(view -> checkPermission(Manifest.permission.CAMERA, REQUEST_CAMERA_PERMISSION_ONE));
    }
    public void checkPermission(String permission, int requestCode)
    {
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
        String mFileName = "jpg_"+timeStamp+ "_";
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
                    filepath = FilePath.getPath(this, uri);
                    if (filepath != null) {
                        if (FileUtils.checkExtension(this, uri)) {
                            Uri file = Uri.fromFile(new File(filepath));
                            str_ext1 = MimeTypeMap.getFileExtensionFromUrl(file.toString());
                            binding.etAttachfile.setText(filepath);
                            mFilepaths.add(filepath);
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
            }else if ( requestCode == REQUEST_CAMERA_PERMISSION_ONE) {
                try {
                    bitmap5 = BitmapFactory.decodeFile(currentImagePath);
                    //   Toast.makeText(this,  currentImagePath.toString(), Toast.LENGTH_SHORT).show();
                    binding.etAttachfile.setText(currentImagePath);
                    str_ext1 = "jpg";
                    mFilepaths.add(currentImagePath);
                    //image.setImageBitmap(bitmap5);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                if (str_ext1 != null && str_ext1.equals("pdf")) {
                    InputStream inputStream = this.getContentResolver().openInputStream(uri);
                    byte[] pdfInBytes = new byte[inputStream.available()];
                    inputStream.read(pdfInBytes);
                    encodedImage = Base64.encodeToString(pdfInBytes, Base64.NO_WRAP);
                } else if (str_ext1 != null && bitmap1 != null) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap1.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
                    byte[] imageInByte = byteArrayOutputStream.toByteArray();
                    encodedImage = Base64.encodeToString(imageInByte, Base64.NO_WRAP);
                }

                DocumentData data_image = new DocumentData(encodedImage,str_ext1);
                name = new ArrayList<>();
                name.add(data_image);
            }catch (Exception ex){
                ex.getMessage();
            }
        }
    }

}
