package com.spectra.fieldforce.model.gpon.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IRHoldCategoryResponse {

    @SerializedName("IR_Hold_Categories")
    private List<IRHoldCategory> iRHoldCategories = null;

    public List<IRHoldCategory> getIRHoldCategories() {
        return iRHoldCategories;
    }

    public void setIRHoldCategories(List<IRHoldCategory> iRHoldCategories) {
        this.iRHoldCategories = iRHoldCategories;
    }



    public class IRHoldCategory {

        @SerializedName("ID")
        private String id;
        @SerializedName("IRCategory")
        private String iRCategory;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIRCategory() {
            return iRCategory;
        }

        public void setIRCategory(String iRCategory) {
            this.iRCategory = iRCategory;
        }
    }
}
