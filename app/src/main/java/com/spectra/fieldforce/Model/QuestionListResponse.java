package com.spectra.fieldforce.Model;

import java.util.ArrayList;

public class QuestionListResponse {
    private String Status;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    private String count;

    private ArrayList<Data> data;

    private String ErrorCode;

    public String getStatus ()
    {
        return Status;
    }

    public void setStatus (String Status)
    {
        this.Status = Status;
    }

    public ArrayList<Data> getData ()
    {
        return data;
    }

    public void setData (ArrayList<Data> data)
    {
        this.data = data;
    }

    public String getErrorCode ()
    {
        return ErrorCode;
    }

    public void setErrorCode (String ErrorCode)
    {
        this.ErrorCode = ErrorCode;
    }


    public class Data {
        private String quesId;

        private String inspection;

        public String getQuesId ()
        {
            return quesId;
        }

        public void setQuesId (String quesId)
        {
            this.quesId = quesId;
        }

        public String getInspection ()
        {
            return inspection;
        }

        public void setInspection (String inspection)
        {
            this.inspection = inspection;
        }


    }

}
