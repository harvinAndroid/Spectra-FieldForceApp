package com.example.fieldforceapp.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fieldforceapp.R;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.MyViewHolder> {

    private List<Order> orderList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView customerName, customerEmail, customerMobile;

        public MyViewHolder(View view) {
            super(view);
            customerName = (TextView) view.findViewById(R.id.customerName);
            customerEmail = (TextView) view.findViewById(R.id.customerEmail);
            customerMobile = (TextView) view.findViewById(R.id.customerMobile);
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
        holder.customerName.setText(order.getCustomerName());
        holder.customerEmail.setText(order.getCustomerEmailId());
        holder.customerMobile.setText(order.getCustomerMobile());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}