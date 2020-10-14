package com.spectra.fieldforce.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.spectra.fieldforce.R;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<String> {

    LayoutInflater flater;

    public SpinnerAdapter(@NonNull Activity context, int resource, int textViewResourceId, @NonNull List<String> objects) {
        super(context, resource, textViewResourceId, objects);
        flater = context.getLayoutInflater();

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        @SuppressLint("ViewHolder") View rowview = flater.inflate(R.layout.spinner_item,null,true);

        TextView textView = rowview.findViewById(R.id.tv_item_name);
        textView.setText(getItem(position));
        return  rowview;
    }
}
