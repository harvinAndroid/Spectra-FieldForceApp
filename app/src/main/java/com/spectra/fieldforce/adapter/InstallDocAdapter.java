package com.spectra.fieldforce.adapter;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.spectra.fieldforce.BuildConfig;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.databinding.ActivityAllDocumentListBinding;
import com.spectra.fieldforce.model.gpon.response.GetWcrDocResponse;
import com.spectra.fieldforce.salesapp.model.AttachDoc;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

public class InstallDocAdapter extends RecyclerView.Adapter<InstallDocAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<GetWcrDocResponse.Datum> attachDocs;
    ActivityAllDocumentListBinding binding;
    String gallery;

    public InstallDocAdapter(Context context, ArrayList<GetWcrDocResponse.Datum> docResponses) {
        this.context=context;
        this.attachDocs=docResponses;
    }


    @NotNull
    @Override
    public InstallDocAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
         binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.activity_all_document_list, parent, false);
        return new InstallDocAdapter.MyViewHolder(binding);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.etIte1.setText(attachDocs.get(position).filename);
        holder.binding.etIte1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.binding.etIte1.setText(attachDocs.get(position).filename);
                gallery =holder.binding.etIte1.getText().toString();
                if(gallery.contains("jpg")||gallery.contains("jpeg")||gallery.contains("png")){
                    saveToGallery(attachDocs.get(position).attachment);
                } else {
                    storetoPdfandOpen(attachDocs.get(position).attachment,context);
                }
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

        Log.d("ResponseEnv",root);

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
