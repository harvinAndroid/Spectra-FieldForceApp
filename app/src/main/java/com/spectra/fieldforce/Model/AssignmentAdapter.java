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
        public TextView customerId, customerName, customerMobile, customerAddress, srNumber, slotTime, caseRemarks,
                srStatus, srType, srSubType, slaClock, slaStatus, customerIP, segment, devicePort, podName;

        public MyViewHolder(View view) {
            super(view);
            customerId=(TextView) view.findViewById(R.id.customerId);
            customerName = (TextView) view.findViewById(R.id.customerName);
            customerMobile = (TextView) view.findViewById(R.id.customerMobile);
            customerAddress = (TextView) view.findViewById(R.id.customerAddress);
            srNumber = (TextView) view.findViewById(R.id.srNumber);
            slotTime = (TextView) view.findViewById(R.id.slotTime);
            caseRemarks=(TextView) view.findViewById(R.id.caseRemarks);
            srStatus=(TextView) view.findViewById(R.id.srStatus);
            srType=(TextView) view.findViewById(R.id.srType);
            srSubType=(TextView) view.findViewById(R.id.srSubType);
            slaClock=(TextView) view.findViewById(R.id.slaClock);
            slaStatus=(TextView) view.findViewById(R.id.slaStatus);
            customerIP =(TextView) view.findViewById(R.id.customerIP);
            segment = (TextView) view.findViewById(R.id.businessSegemnt);
            podName = (TextView) view.findViewById(R.id.podName);
            devicePort = (TextView) view.findViewById(R.id.devicePort);
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
        holder.customerMobile.setText(order.getCustomerMobile());
        holder.customerAddress.setText(order.getCustomerAddress());
        holder.srNumber.setText(order.getSrNumber());
        holder.slotTime.setText(order.getRoasterDate()+" "+order.getFromtime()+ " - " + order.getTotime());
        holder.caseRemarks.setText(order.getCaseRemarks());
        holder.srStatus.setText(order.getSrStatus());
        holder.srType.setText(order.getSrType());
        holder.srSubType.setText(order.getSrSubType());
        holder.slaClock.setText(order.getSlaClock());
        holder.slaStatus.setText(order.getSlaStatus());
        holder.customerIP.setText(order.getCustomerIP());
        holder.segment.setText(order.getSegment());
        holder.podName.setText(order.getPodName());
        holder.devicePort.setText(order.getDeviceName() + " : "+ order.getPortId());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}