package com.spectra.fieldforce.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.spectra.fieldforce.model.ItemConsumption.DeleteItemConsumption;
import com.spectra.fieldforce.model.ItemConsumption.ItemEquipment;
import com.spectra.fieldforce.model.ItemConsumption.ItemResponse;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.activity.MainActivity;
import com.spectra.fieldforce.api.ApiClient;
import com.spectra.fieldforce.api.ApiInterface;
import com.spectra.fieldforce.fragment.EditItemConsumptionFragment;
import com.spectra.fieldforce.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemConsumptionDetailAdapter extends RecyclerView.Adapter<ItemConsumptionDetailAdapter.MyViewHolder> {

    private Context context;

    private String approvalStatus;
    private String CanId;
    private String SrNumber,firstStatus;
    private String GetItemConsumption;
    private ArrayList<ItemEquipment> details;
    private String statusMsg;


    public ItemConsumptionDetailAdapter(FragmentActivity activity, ArrayList<ItemEquipment> details, String canId, String srNumber, String approval, String firstStatus, String statusMsg) {
        this.context = activity;
        this.CanId = canId;
        this.SrNumber = srNumber;
        this.approvalStatus = approval;
        this.firstStatus = firstStatus;
        this.details = details;
        this.statusMsg = statusMsg;
    }

    @NotNull
    @Override
    public ItemConsumptionDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_consumption_list, viewGroup, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tv_item.setText("Item Name : "+details.get(position).getItemName());
        holder.tv_subitem.setText("Sub Item Name : "+details.get(position).getSubitemName());
        holder.tv_status.setText("Status : " +  firstStatus);
        holder.tv_cons_status.setText("Consumption Status : " + approvalStatus);
        holder.btn_edit.setOnClickListener(v -> {
            if(approvalStatus!=null){
            if(approvalStatus.equals("Rejected")){
                Bundle bundle = new Bundle();
                bundle.putString("SrNumber", SrNumber);
                bundle.putString("CustomerId", CanId);
                AppCompatActivity activity = (AppCompatActivity) context;
                Fragment myFragment = new EditItemConsumptionFragment();
                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fregment_container, myFragment).addToBackStack(null).commit();
            }else{
                if(approvalStatus!=null){
                    Toast.makeText(context,approvalStatus,Toast.LENGTH_LONG).show();
                }else if(approvalStatus==null){
                    Toast.makeText(context,statusMsg,Toast.LENGTH_LONG).show();
                }
            }
            }else{
                Toast.makeText(context,statusMsg,Toast.LENGTH_LONG).show();
            }
        });
        holder.img_delete.setOnClickListener(v -> {
            if(details.get(position).getDefaultKey().equals("item"))
            {
                deleteItemConsumption("",details.get(position).getItemconsumptionID());
            }else{
                deleteItemConsumption(details.get(position).getItemconsumptionID(),"");
            }
        });

    }

    private void deleteItemConsumption(String itemcosmid, String etsqcomsid) {

        DeleteItemConsumption deleteItemConsumption = new DeleteItemConsumption(Constants.DELETE_ITEM_CONSUMPTION,Constants.AUTH_KEY,itemcosmid,etsqcomsid,SrNumber);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ItemResponse> call = apiService.deleteItemConsumptionDetails(deleteItemConsumption);
        call.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(@NonNull Call<ItemResponse> call, @NonNull Response<ItemResponse> response) {
                int status = Objects.requireNonNull(response.body()).getStatus();
                if (status==1) {
                    Intent i = new Intent(context, MainActivity.class);
                    Toast.makeText(context,response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                    context.startActivity(i);
                    ((Activity)context).finish();
                }else{
                    Toast.makeText(context,response.body().getResponse().getMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ItemResponse> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

     class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_item,tv_subitem,tv_status,tv_cons_status;
        private Button btn_edit;
        private ImageView img_delete ;

         MyViewHolder(@NonNull View itemView) {
             super(itemView);
             tv_item = (TextView)itemView.findViewById(R.id.tv_item);
             img_delete = (ImageView) itemView.findViewById(R.id.img_delete);
             tv_subitem = (TextView) itemView.findViewById(R.id.tv_subitem);
             btn_edit = (Button) itemView.findViewById(R.id.btn_edit);
             tv_status = (TextView) itemView.findViewById(R.id.tv_status);
             tv_cons_status = (TextView)itemView.findViewById(R.id.tv_cons_status);
        }
    }
}
