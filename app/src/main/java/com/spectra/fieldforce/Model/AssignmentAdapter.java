package com.spectra.fieldforce.Model;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.spectra.fieldforce.R;

import java.net.URI;
import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.MyViewHolder> {

    private List<Order> orderList;
    private Activity activity;
    private Button btnStartTime;
    private TextView startTime;
    private Button btnEndTime;
    private TextView endTime;
    private Location location;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView customerId, customerName, customerMobile, customerAddress, srNumber, slotTime, caseRemarks,
                srStatus, srType, srSubType, slaClock, slaStatus, customerIP, segment, devicePort, podName,
                btnStartTime, btnEndTime, startTime, endTime;

        public MyViewHolder(View view) {
            super(view);
            customerId = (TextView) view.findViewById(R.id.customerId);
            customerName = (TextView) view.findViewById(R.id.customerName);
            customerMobile = (TextView) view.findViewById(R.id.customerMobile);
            customerAddress = (TextView) view.findViewById(R.id.customerAddress);
            srNumber = (TextView) view.findViewById(R.id.srNumber);
            slotTime = (TextView) view.findViewById(R.id.slotTime);
            caseRemarks = (TextView) view.findViewById(R.id.caseRemarks);
            srStatus = (TextView) view.findViewById(R.id.srStatus);
            srType = (TextView) view.findViewById(R.id.srType);
            srSubType = (TextView) view.findViewById(R.id.srSubType);
            slaClock = (TextView) view.findViewById(R.id.slaClock);
            slaStatus = (TextView) view.findViewById(R.id.slaStatus);
            customerIP = (TextView) view.findViewById(R.id.customerIP);
            segment = (TextView) view.findViewById(R.id.businessSegemnt);
            podName = (TextView) view.findViewById(R.id.podName);
            devicePort = (TextView) view.findViewById(R.id.devicePort);
            btnStartTime = (Button) view.findViewById(R.id.btnStartTime);
            btnEndTime = (Button) view.findViewById(R.id.btnEndTime);
            startTime = (TextView) view.findViewById(R.id.startTime);
            endTime = (TextView) view.findViewById(R.id.endTime);
        }
    }

    public AssignmentAdapter(Activity activity, List<Order> orderList) {
        this.activity = activity;
        this.orderList = orderList;
    }

    public void getLatLong(TextView startTime) {
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
            return;
        }
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        double longi = location.getLongitude();
        double lati = location.getLatitude();
        String message = longi + " " + lati;
        startTime.setText(message);
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
        holder.slotTime.setText(order.getRoasterDate() + " " + order.getFromtime() + " - " + order.getTotime());
        holder.caseRemarks.setText(order.getCaseRemarks());
        holder.srStatus.setText(order.getSrStatus());
        holder.srType.setText(order.getSrType());
        holder.srSubType.setText(order.getSrSubType());
        holder.slaClock.setText(order.getSlaClock());
        holder.slaStatus.setText(order.getSlaStatus());
        holder.customerIP.setText(order.getCustomerIP());
        holder.segment.setText(order.getSegment());
        holder.podName.setText(order.getPodName());
        holder.devicePort.setText(order.getDeviceName() + " : " + order.getPortId());
        holder.btnStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLatLong(holder.startTime);
            }
        });
        holder.btnEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLatLong(holder.endTime);
            }
        });
        holder.customerMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri number = Uri.parse("tel:" + holder.customerMobile.getText().toString());
                call_action(number);
            }
        });
    }

    public void call_action(Uri number) {
        Intent intent = new Intent(Intent.ACTION_DIAL, number);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}