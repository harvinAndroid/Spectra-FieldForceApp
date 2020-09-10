package com.spectra.fieldforce.adapter;

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

import com.spectra.fieldforce.Model.QuestionListResponse;
import com.spectra.fieldforce.Model.SaveQuestionareList.Answer;
import com.spectra.fieldforce.Model.questionAnsResponse.QuestionAnswerList;
import com.spectra.fieldforce.Model.questionAnsResponse.Response;
import com.spectra.fieldforce.R;
import com.spectra.fieldforce.activity.Activity_Resolve;
import com.spectra.fieldforce.listener.OnItemClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class QuestionAnswerAdapter extends RecyclerView.Adapter<QuestionAnswerAdapter.MyViewHolder> {

    private Context context;
    private List<QuestionAnswerList.Response> questionList;
    private OnItemClickListener myClickListener;
    private String listSize;
  //  private String id;
    ArrayList<String> tag,questionid,answer;


    public QuestionAnswerAdapter(Activity_Resolve context, List<QuestionAnswerList.Response> questionList, OnItemClickListener myClickListener) {
        this.context = context;
        this.questionList = questionList;
        this.myClickListener = myClickListener;
    }

   /* public QuestionAnswerAdapter(Context context, ArrayList<QuestionListResponse.Data> questionList, OnItemClickListener myClickListener) {
        this.context = context;
        this.questionList = questionList;
        this.myClickListener = myClickListener;
    }*/


    @NotNull
    @Override
    public QuestionAnswerAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.questionlist_adapter, viewGroup, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_question.setText((questionList.get(position).getQuesId())+"."+" "+ questionList.get(position).getQuestion());

       /* if(questionList.get(position).getAns()!=null){
            switch (questionList.get(position).getAns()) {
                case "0":
                    holder.radioButton1.setChecked(true);
                    break;
                case "1":
                    holder.radioButton2.setChecked(true);
                    break;
                case "-1":
                    holder.radioButton3.setChecked(true);
                    break;
            }
        }

*/


        holder.radioGroup.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) (group, checkedId) -> {
            RadioButton rb = (RadioButton) group.findViewById(checkedId);
            String id = questionList.get(position).getQuesId();
            if (null != rb && checkedId > -1) {
                String ans = rb.getText().toString();
                switch (ans) {
                    case "NA":
                        myClickListener.myOnClick(new Answer(id,"-1"));
                        break;
                    case "NO":
                        myClickListener.myOnClick(new Answer(id,"1"));
                        break;
                    case "YES":
                        myClickListener.myOnClick(new Answer(id,"0"));
                        break;
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return questionList.size();
    }

     class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_question;
        private RadioGroup radioGroup;
        private RadioButton radioButton1, radioButton2, radioButton3;

         MyViewHolder(@NonNull View itemView) {
             super(itemView);
             tv_question = (TextView)itemView.findViewById(R.id.tv_question);
             radioGroup = (RadioGroup) itemView.findViewById(R.id.radioGroup);
             radioButton1 = (RadioButton) itemView.findViewById(R.id.radioButton1);
             radioButton2 = (RadioButton) itemView.findViewById(R.id.radioButton2);
             radioButton3 = (RadioButton) itemView.findViewById(R.id.radioButton3);
        }
    }

}
