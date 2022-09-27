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

import com.spectra.fieldforce.model.SaveQuestionareList.Answer;
import com.spectra.fieldforce.model.questionAnsResponse.QuestionAnswerList;
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
    private Test test;
  //  private String id;
    ArrayList<String> tag,questionid,answer;


    public QuestionAnswerAdapter(Activity_Resolve context, List<QuestionAnswerList.Response> questionList,/* OnItemClickListener myClickListener,*/Test test) {
        this.context = context;
        this.questionList = questionList;
        this.myClickListener = myClickListener;
        this.test = test;
    }

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
        if(questionList.get(position).getAns()!=null){
            String ans="";
            switch (questionList.get(position).getAns()) {
                case "0":
                    holder.radioButton1.setChecked(true);
                    ans="-1";
                    break;
                case "1":
                    holder.radioButton2.setChecked(true);
                    ans="1";
                    break;
                case "-1":
                    holder.radioButton3.setChecked(true);
                    ans="0";
                    break;
            }

            if(ans.equals("")) {
            }else{
                test.test(position, new Answer(questionList.get(position).getQuesId(), ans));
            }
        }

        holder.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rb = (RadioButton) group.findViewById(checkedId);
            String id = questionList.get(position).getQuesId();
            if (null != rb && checkedId > -1) {
                String ans = rb.getText().toString();
                switch (ans) {
                    case "NA":
                        test.test(position,new Answer(questionList.get(position).getQuesId(),"-1"));
                        break;
                    case "NO":
                        test.test(position,new Answer(questionList.get(position).getQuesId(),"1"));
                        break;
                    case "YES":
                        test.test(position,new Answer(questionList.get(position).getQuesId(),"0"));
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

    public interface Test{
        void test( int pos ,Answer answer);
    }
}
