package com.spectra.fieldforce.Model.questionAnsResponse;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class QuestionAnswerList {
    @SerializedName("Status")
    private Integer status;
    @SerializedName("Response")
    private ArrayList<Response> response;
    @SerializedName("count")
    private Integer count;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ArrayList<Response> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<Response> response) {
        this.response = response;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


    public class Response {

        @SerializedName("quesId")
        private String quesId;
        @SerializedName("question")
        private String question;
        @SerializedName("ans")
        private String ans;

        public String getQuesId() {
            return quesId;
        }

        public void setQuesId(String quesId) {
            this.quesId = quesId;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getAns() {
            return ans;
        }

        public void setAns(String ans) {
            this.ans = ans;
        }

    }
}
