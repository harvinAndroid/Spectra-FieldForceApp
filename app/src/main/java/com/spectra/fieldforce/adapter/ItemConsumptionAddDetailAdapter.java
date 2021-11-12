package com.spectra.fieldforce.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.spectra.fieldforce.model.ItemConsumption.ItemEquipment;
import com.spectra.fieldforce.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ItemConsumptionAddDetailAdapter extends RecyclerView.Adapter<ItemConsumptionAddDetailAdapter.MyViewHolder> {

    private Context context;

    private String approvalStatus;
    private String CanId;
    private String SrNumber,firstStatus;
    private String GetItemConsumption;
    private ArrayList<ItemEquipment> details;
    private String statusMsg;


    public ItemConsumptionAddDetailAdapter(FragmentActivity activity, ArrayList<ItemEquipment> details, String canId, String srNumber, String approval, String firstStatus, String statusMsg) {
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
    public ItemConsumptionAddDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_consumption_list_add, viewGroup, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tv_item.setText("Item Name : " + details.get(position).getItemName());
        holder.tv_subitem.setText("Sub Item Name : " + details.get(position).getSubitemName());
        holder.tv_status.setText("Status : " + firstStatus);
        holder.tv_cons_status.setText("Consumption Status : " + approvalStatus);
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

     class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_item,tv_subitem,tv_status,tv_cons_status;
      /*  private Button btn_edit;
        private ImageView img_delete ;*/

         MyViewHolder(@NonNull View itemView) {
             super(itemView);
             tv_item = (TextView)itemView.findViewById(R.id.tv_item);
           //  img_delete = (ImageView) itemView.findViewById(R.id.img_delete);
             tv_subitem = (TextView) itemView.findViewById(R.id.tv_subitem);
             //btn_edit = (Button) itemView.findViewById(R.id.btn_edit);
             tv_status = (TextView) itemView.findViewById(R.id.tv_status);
             tv_cons_status = (TextView)itemView.findViewById(R.id.tv_cons_status);
        }
    }
}
