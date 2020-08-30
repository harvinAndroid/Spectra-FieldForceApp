package com.spectra.fieldforce.Model.questionAnsResponse;
import java.util.List;

public class QuestionareResponse {
    private Integer Status;
    private Response Response;

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer Status) {
        this.Status = Status;
    }

    public Response getResponse() {
        return Response;
    }

    public void setResponse(Response Response) {
        this.Response = Response;
    }

    public class Response {

        private List<question> question = null;
        private ans ans;

        public List<question> getQuestion() {
            return question;
        }

        public void setQuestion(List<question> question) {
            this.question = question;
        }

        public ans getAns() {
            return ans;
        }

        public void setAns(ans ans) {
            this.ans = ans;
        }

    }

    public class ans {

        private String quesId_1;

        public String getQuesId_1() {
            return quesId_1;
        }

        public void setQuesId_1(String quesId_1) {
            this.quesId_1 = quesId_1;
        }

        public String getQuesId_2() {
            return quesId_2;
        }

        public void setQuesId_2(String quesId_2) {
            this.quesId_2 = quesId_2;
        }

        public String getQuesId_3() {
            return quesId_3;
        }

        public void setQuesId_3(String quesId_3) {
            this.quesId_3 = quesId_3;
        }

        public String getQuesId_4() {
            return quesId_4;
        }

        public void setQuesId_4(String quesId_4) {
            this.quesId_4 = quesId_4;
        }

        public String getQuesId_5() {
            return quesId_5;
        }

        public void setQuesId_5(String quesId_5) {
            this.quesId_5 = quesId_5;
        }

        public String getQuesId_6() {
            return quesId_6;
        }

        public void setQuesId_6(String quesId_6) {
            this.quesId_6 = quesId_6;
        }

        private String quesId_2;
        private String quesId_3;
        private String quesId_4;
        private String quesId_5;
        private String quesId_6;


    }
    public class question {
        private String inspection;

        public String getInspection() {
            return inspection;
        }

        public void setInspection(String inspection) {
            this.inspection = inspection;
        }

    }
}








