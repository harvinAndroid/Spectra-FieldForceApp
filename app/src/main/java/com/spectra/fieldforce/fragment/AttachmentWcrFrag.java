package com.spectra.fieldforce.fragment;

import static com.spectra.fieldforce.utils.AppConstants.REQUEST_CAMERA_PERMISSION_ONE;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.spectra.fieldforce.BuildConfig;
import com.spectra.fieldforce.adapter.InstallDocAdapter;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.databinding.WcrDocBinding;
import com.spectra.fieldforce.model.gpon.request.CreateWcrIrDocReq;
import com.spectra.fieldforce.model.gpon.request.GetDocDetailsReq;
import com.spectra.fieldforce.model.gpon.response.GetWcrDocResponse;
import com.spectra.fieldforce.salesapp.model.DeleteProductResponse;
import com.spectra.fieldforce.utils.Constants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttachmentWcrFrag extends BottomSheetDialogFragment {
    private WcrDocBinding binding;
    private String strGuuId;
    private ArrayList<GetWcrDocResponse.Datum> docResponses;
    private  InstallDocAdapter docAdapter;
    private String str_ext1="",str_ext2="",str_ext3="",str_ext4="",strSlotType,currentImagePath,currentImagePath1;
    private Calendar mCalendar;
    private Bitmap bitmap1,bitmap2,bitmap3,bitmap4,bitmap5,bitmap6,bitmap7,bitmap8;
    private AlphaAnimation inAnimation,outAnimation;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = WcrDocBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String segment= requireArguments().getString("segment");
        strGuuId= requireArguments().getString("strGuuId");
        getDocumentDetails();
        binding.etAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissionAttach(Manifest.permission.CAMERA, REQUEST_CAMERA_PERMISSION_ONE);
            }
        });

    }

    public void checkPermissionAttach(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] { permission }, requestCode);
        }
        else {
            OpenCamera(REQUEST_CAMERA_PERMISSION_ONE);
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

    private void inProgress(){
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(inAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.VISIBLE);
    }

    private void outProgress(){
        outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        binding.progressLayout.progressOverlay.setAnimation(outAnimation);
        binding.progressLayout.progressOverlay.setVisibility(View.GONE);
    }

    private File getImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "jpg_"+timeStamp+ "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        currentImagePath = mFile.getAbsolutePath();
        return mFile;
    }

    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CAMERA_PERMISSION_ONE) {
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
                bitmap3 = BitmapFactory.decodeFile(currentImagePath);
                binding.etAttach.setText(currentImagePath);

                if(currentImagePath!=null) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap3.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
                    byte[] imageInByte = byteArrayOutputStream.toByteArray();
                    String encodedImage = Base64.encodeToString(imageInByte, Base64.NO_WRAP);
                    str_ext3 = "Img" + ".jpg";
                    Calendar c = Calendar.getInstance();
                    int seconds = c.get(Calendar.SECOND);
                    currentImagePath = "file" + "img(" + (seconds) + "/)";
                    Toast.makeText(getContext(),"Please Wait while Uploading...",Toast.LENGTH_SHORT).show();
                    updateDoc(encodedImage, currentImagePath + str_ext3);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void moveNext(){
        Bundle bundle1=new Bundle();
        bundle1.putString("strGuuId",strGuuId);
        AttachmentWcrFrag attachmentWcrFrag = new AttachmentWcrFrag();
        attachmentWcrFrag.setArguments(bundle1);
        attachmentWcrFrag.show(requireActivity().getSupportFragmentManager(), attachmentWcrFrag.getTag());
    }

    private void updateDoc(String encodedPathhh, String str_ext33) {
        inProgress();
        CreateWcrIrDocReq createWcrIrDocReq = new CreateWcrIrDocReq();
        createWcrIrDocReq.setAction(Constants.CREATE_WCRIR_DOCUMENT);
        createWcrIrDocReq.setAuthkey(Constants.AUTH_KEY);
        createWcrIrDocReq.setWcrId(strGuuId);
        createWcrIrDocReq.setIrId("");
        createWcrIrDocReq.setFileContent(encodedPathhh);
        createWcrIrDocReq.setFileNameWithExtension(str_ext33);


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DeleteProductResponse> call = apiService.uploadWcrDoc(createWcrIrDocReq);
        call.enqueue(new Callback<DeleteProductResponse>() {
            @Override
            public void onResponse(Call<DeleteProductResponse> call, Response<DeleteProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    outProgress();
                    try {
                        String msg = response.body().getResponse().getMessage();
                        if (response.body().getStatus().equals("Success")) {
                            Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
                            binding.etAttach.setText("");
                            moveNext();
                        }else{
                            Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<DeleteProductResponse> call, Throwable t) {
                binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                Log.e("RetroError", t.toString());
            }
        });
    }


    private void getDocumentDetails() {
        inProgress();
        Toast.makeText(getContext(),"Please Wait...",Toast.LENGTH_SHORT).show();
        GetDocDetailsReq getDocDetailsReq = new GetDocDetailsReq (Constants.GETWCRIR_DOCUMENT,
                Constants.AUTH_KEY,strGuuId,"");
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GetWcrDocResponse> call = apiService.getInstallationDoc(getDocDetailsReq);
        call.enqueue(new Callback<GetWcrDocResponse>() {
            @Override
            public void onResponse(Call<GetWcrDocResponse> call, Response<GetWcrDocResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        outProgress();
                        if (response.body().status.equals("Success")) {

                            binding.rvDocument.setHasFixedSize(true);
                            binding.rvDocument.setLayoutManager(new LinearLayoutManager(getContext()));
                            docResponses = response.body().response.data;
                            if(docResponses!=null) {
                                binding.rvDocument.setVisibility(View.VISIBLE);
                                docAdapter = new InstallDocAdapter(getContext(), docResponses);
                                binding.rvDocument.setAdapter(docAdapter);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetWcrDocResponse> call, Throwable t) {
                binding.progressLayout.progressOverlay.setVisibility(View.GONE);
                Log.e("RetroError", t.toString());
            }
        });
    }



}
