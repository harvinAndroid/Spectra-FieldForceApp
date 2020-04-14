package com.spectra.fieldforce.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.spectra.fieldforce.R;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.MyViewHolder> {

    private List<Order> orderList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView customerId, customerName, customerEmail, customerMobile, customerAddress, srNumber, slotTime, caseRemarks,
                srStatus,srType,srSubType,srSubSubType;

        public MyViewHolder(View view) {
            super(view);
            customerId=(TextView) view.findViewById(R.id.customerId);
            customerName = (TextView) view.findViewById(R.id.customerName);
            customerEmail = (TextView) view.findViewById(R.id.customerEmail);
            customerMobile = (TextView) view.findViewById(R.id.customerMobile);
            customerAddress = (TextView) view.findViewById(R.id.customerAddress);
            srNumber = (TextView) view.findViewById(R.id.srNumber);
            slotTime = (TextView) view.findViewById(R.id.slotTime);
            caseRemarks=(TextView) view.findViewById(R.id.caseRemarks);
            srStatus=(TextView) view.findViewById(R.id.srStatus);
            srType=(TextView) view.findViewById(R.id.srType);
            srSubType=(TextView) view.findViewById(R.id.srSubType);
            srSubSubType=(TextView) view.findViewById(R.id.srSubSubType);

        }
    }


    public AssignmentAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Order order = orderList.get(position);
        holder.customerId.setText(order.getCustomerID());
        holder.customerName.setText(order.getCustomerName());
        holder.customerEmail.setText(order.getCustomerEmailId());
        holder.customerMobile.setText(order.getCustomerMobile());
        holder.customerAddress.setText(order.getCustomerAddress());
        holder.srNumber.setText(order.getSrNumber());
        holder.slotTime.setText(order.getRoasterDate()+" "+order.getFromtime()+ " - " + order.getTotime());
        holder.caseRemarks.setText(order.getCaseRemarks());
        holder.srStatus.setText(order.getSrStatus());
        holder.srType.setText(order.getSrType());
        holder.srSubType.setText(order.getSrSubType());
        holder.srSubSubType.setText(order.getSrSubSubType());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}