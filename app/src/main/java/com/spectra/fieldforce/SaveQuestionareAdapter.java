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
import androidx.recyclerview.widget.RecyclerView;

import com.spectra.fieldforce.Model.questionAnsResponse.Ans;
import com.spectra.fieldforce.Model.questionAnsResponse.Question;
import com.spectra.fieldforce.Model.questionAnsResponse.QuestionareResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SaveQuestionareAdapter extends RecyclerView.Adapter<SaveQuestionareAdapter.MyViewHolder> {

    private Context context;
    List<Question>question;
    List<Ans> ans;

    private OnItemClickListener myClickListener;
    ArrayList<String> tag,questionid;

    public SaveQuestionareAdapter(Activity_Resolve context, List<Question> question, List<Ans> ans, OnItemClickListener myClickListener) {
        this.context = context;
        this.question=question;
        this.ans=ans;

    }

    @NotNull
    @Override
    public SaveQuestionareAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.questionlist_adapter, viewGroup, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_question.setText( question.get(position).getInspection());
        if(ans.get(position).getQuesId_1().equals("-1")){
            holder.radioButton3.setChecked(true);
        }else if(ans.get(position).getQuesId_1().equals("1")){
            holder.radioButton2.setChecked(true);
        }else if(ans.get(position).getQuesId_1().equals("0")){
            holder.radioButton1.setChecked(true);
        }

        if(ans.get(position).getQuesId_1().equals("-1")){
            holder.radioButton3.setChecked(true);
        }else if(ans.get(position).getQuesId_1().equals("1")){
            holder.radioButton2.setChecked(true);
        }else if(ans.get(position).getQuesId_1().equals("0")){
            holder.radioButton1.setChecked(true);
        }


        if(ans.get(position).getQuesId_1().equals("-1")){
            holder.radioButton3.setChecked(true);
        }else if(ans.get(position).getQuesId_1().equals("1")){
            holder.radioButton2.setChecked(true);
        }else if(ans.get(position).getQuesId_1().equals("0")){
            holder.radioButton1.setChecked(true);
        }


       /* String ans = responses.get(position).getAns();
        switch (ans) {
            case "NA":
                tag.add("-1");
                break;
            case "NO":
                tag.add("0");
                break;
            case "YES":
                tag.add("1");
                break;
        }*/

        holder.radioGroup.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) (group, checkedId) -> {
            RadioButton rb = (RadioButton) group.findViewById(checkedId);
          //  String id = question.get(position).g();
            if (null != rb && checkedId > -1) {
                String ans = rb.getText().toString();
                tag = new ArrayList<>();
                switch (ans) {
                    case "NA":
                        tag.add("-1");
                        break;
                    case "NO":
                        tag.add("0");
                        break;
                    case "YES":
                        tag.add("1");
                        break;
                }

                //tag.add(id);
                myClickListener.myOnClick(tag,questionid);
            }
        });
    }


    @Override
    public int getItemCount() {
        return ans.size();

    }

     class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_question;
        private RadioGroup radioGroup;
        private RadioButton radioButton1;
         private RadioButton radioButton2,radioButton3;

         MyViewHolder(@NonNull View itemView) {
            super(itemView);
             tv_question = itemView.findViewById(R.id.tv_question);
             radioGroup = itemView.findViewById(R.id.radioGroup);
             radioButton1 =  itemView.findViewById(R.id.radioButton1);
             radioButton2 =itemView.findViewById(R.id.radioButton2);
             radioButton3 =  itemView.findViewById(R.id.radioButton3);
        }
         //int position = getAdapterPosition();
    }

}
