package com.spectra.fieldforce;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.spectra.fieldforce.Model.QuestionListResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class QuestionAnswerAdapter extends RecyclerView.Adapter<QuestionAnswerAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<QuestionListResponse.Data> questionList;
    private OnItemClickListener myClickListener;
  //  private String id;
    ArrayList<String> tag,questionid;

    public QuestionAnswerAdapter(Context context, ArrayList<QuestionListResponse.Data> questionList, OnItemClickListener myClickListener) {
        this.context = context;
        this.questionList = questionList;
        this.myClickListener = myClickListener;
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
        holder.tv_question.setText((questionList.get(position).getQuesId())+"."+" "+  questionList.get(position).getInspection());

       /* int pos = getAdapterPosition();
        if(pos != RecyclerView.NO_POSITION){
            RvDataItem clickedDataItem = dataItems.get(pos);
            Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getName(), Toast.LENGTH_SHORT).show();
        }*/

        holder.radioGroup.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) (group, checkedId) -> {
            RadioButton rb = (RadioButton) group.findViewById(checkedId);
            String id = questionList.get(position).getQuesId();
         //   Toast.makeText(context, id,Toast.LENGTH_LONG).show();


            if (null != rb && checkedId > -1) {
                String ans = rb.getText().toString();
                tag = new ArrayList<>();
                switch (ans) {
                    case "NA":
                        tag.add(ans);
                        break;
                    case "NO":
                        tag.add("0");
                        break;
                    case "YES":
                        tag.add("1");
                        break;
                }

                tag.add(id);
                myClickListener.myOnClick(tag,questionid);
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

         MyViewHolder(@NonNull View itemView) {
            super(itemView);
             tv_question = (TextView)itemView.findViewById(R.id.tv_question);
             radioGroup = (RadioGroup) itemView.findViewById(R.id.radioGroup);
        }
         int position = getAdapterPosition();
    }

}
