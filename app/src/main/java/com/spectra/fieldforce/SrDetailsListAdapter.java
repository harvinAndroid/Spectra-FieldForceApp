package com.spectra.fieldforce;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.spectra.fieldforce.Model.QuestionListResponse;
import com.spectra.fieldforce.Model.SrDetailsListResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SrDetailsListAdapter extends RecyclerView.Adapter<SrDetailsListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<SrDetailsListResponse.Note> SrDetailsList;


  /*  public SrDetailsListAdapter(Context context, ArrayList<QuestionListResponse.Data> questionList, OnItemClickListener myClickListener) {
        this.context = context;
        this.questionList = questionList;
        this.myClickListener = myClickListener;
    }*/

    public SrDetailsListAdapter(Context context, ArrayList<SrDetailsListResponse.Note> srDetailsList) {
        this.context=context;
        this.SrDetailsList=srDetailsList;
    }


    @NotNull
    @Override
    public SrDetailsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sr_details_list_adapter, viewGroup, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_title.setText("TITLE: "+(SrDetailsList.get(position).getTitle()));
        holder.tv_description.setText(("DESCRIPTION: "+SrDetailsList.get(position).getDescription()));
    }


    @Override
    public int getItemCount() {
        return SrDetailsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_description,tv_title;
        private RadioGroup radioGroup;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = (TextView)itemView.findViewById(R.id.tv_title);
            tv_description = (TextView) itemView.findViewById(R.id.tv_description);
        }
    }

}
