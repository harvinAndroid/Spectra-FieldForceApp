package com.spectra.fieldforce.Model;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.spectra.fieldforce.R;
import com.spectra.fieldforce.SRDetail;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.MyViewHolder> {
    private List<Order> orderList;
    private Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView srNumber, customerId, customerName, customerMobile, slotTime, customerAddress;
        public Button btnView;

        public MyViewHolder(View view) {
            super(view);
            customerId = (TextView) view.findViewById(R.id.customerId);
            customerName = (TextView) view.findViewById(R.id.customerName);
            customerMobile = (TextView) view.findViewById(R.id.customerMobile);
            customerAddress = (TextView) view.findViewById(R.id.customerAddress);
            srNumber = (TextView) view.findViewById(R.id.srNumber);
            slotTime = (TextView) view.findViewById(R.id.slotTime);
            btnView = (Button) view.findViewById(R.id.btnView);
        }
    }

    public AssignmentAdapter(Activity activity, List<Order> orderList) {
        this.activity = activity;
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
        final Order order = orderList.get(position);
        holder.customerId.setText(order.getCustomerID());
        holder.customerName.setText(order.getCustomerName());
        holder.customerMobile.setText(order.getCustomerMobile());
        holder.customerAddress.setText(order.getCustomerAddress());
        holder.srNumber.setText(order.getSrNumber());
        holder.slotTime.setText(order.getRoasterDate() + " " + order.getFromtime() + " - " + order.getTotime());
        holder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity1 = (AppCompatActivity)activity;
                Bundle b = new Bundle();
                b.putString("srNumber", order.getSrNumber());
                activity1.getSupportFragmentManager().beginTransaction().add(R.id.fregment_container, new SRDetail().newInstance(order.getSrNumber(),""),SRDetail.class.getSimpleName()).addToBackStack(null
                ).commit();
                holder.btnView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}