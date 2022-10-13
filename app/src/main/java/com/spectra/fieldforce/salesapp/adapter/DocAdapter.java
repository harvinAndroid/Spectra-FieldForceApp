package com.spectra.fieldforce.salesapp.adapter;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.spectra.fieldforce.BuildConfig;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.databinding.ActivityAllDocumentListBinding;
import com.spectra.fieldforce.databinding.AdapterManholeListBinding;
import com.spectra.fieldforce.fragment.WcrEditManholeFragment;
import com.spectra.fieldforce.fragment.WcrFragment;
import com.spectra.fieldforce.model.gpon.request.DeleteItemRequest;
import com.spectra.fieldforce.model.gpon.response.CommonClassResponse;
import com.spectra.fieldforce.model.gpon.response.WcrResponse;
import com.spectra.fieldforce.salesapp.activity.DocumentCafAct;
import com.spectra.fieldforce.salesapp.model.AttachDoc;
import com.spectra.fieldforce.salesapp.model.DocResponse;
import com.spectra.fieldforce.salesapp.model.GetDocCafResponse;
import com.spectra.fieldforce.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocAdapter extends RecyclerView.Adapter<DocAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<AttachDoc> attachDocs;
    ActivityAllDocumentListBinding binding;
    String gallery;

    public DocAdapter(Context context, ArrayList<AttachDoc> docResponses) {
        this.context=context;
        this.attachDocs=docResponses;
    }


    @NotNull
    @Override
    public DocAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
         binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.activity_all_document_list, parent, false);
        return new DocAdapter.MyViewHolder(binding);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.etIte1.setText(attachDocs.get(position).getFilename());
        holder.binding.etIte1.setOnClickListener(view -> {
            holder.binding.etIte1.setText(attachDocs.get(position).getFilename());
            gallery =holder.binding.etIte1.getText().toString();
            if(gallery.contains("jpg")||gallery.contains("jpeg")||gallery.contains("png")){
                saveToGallery(attachDocs.get(position).getAttachment());
            } else {
                storetoPdfandOpen(attachDocs.get(position).getAttachment(),context);
            }
        });

    }

    @Override
    public int getItemCount() {

        return attachDocs.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ActivityAllDocumentListBinding binding;
        MyViewHolder( ActivityAllDocumentListBinding binding) {
            super(binding.getRoot());
           this.binding = binding;
        }
    }

    private void saveToGallery(String attachment){
        byte [] encodeByte = Base64.decode(attachment,Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

        FileOutputStream outputStream = null;
        File file = Environment.getExternalStorageDirectory();
        File dir = new File(file.getAbsolutePath() + "/Spectra Downloads");
        dir.mkdirs();

        String filename = String.format("%d.png",System.currentTimeMillis());
        File outFile = new File(dir,filename);
        try{
            outputStream = new FileOutputStream(outFile);
        }catch (Exception e){
            e.printStackTrace();
        }
         bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
        try{
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            outputStream.close();
            Toast.makeText(context,"Downloaded image ....",Toast.LENGTH_LONG).show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    private void saveImage(Bitmap data) {
        File createFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"test");
        createFolder.mkdir();
        File saveImage = new File(createFolder,"downloadimage.jpg");
        try {
            OutputStream outputStream = new FileOutputStream(saveImage);
            data.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void storetoPdfandOpen( String base,Context context) {
        String root = Environment.getExternalStorageDirectory().toString();

        File myDir = new File(root + "/WorkBox");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }

        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);

        String fname = "Attachments-" + n + ".pdf";
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            byte[] pdfAsBytes = Base64.decode(base, 0);
            out.write(pdfAsBytes);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        File dir = new File(Environment.getExternalStorageDirectory(), "WorkBox");
        File imgFile = new File(dir, fname);
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);

        Uri uri;
        uri =  FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file) ;
        sendIntent.setDataAndType(uri, "application/pdf");
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(sendIntent);

    }
}
