package com.spectra.fieldforce;

import com.google.gson.JsonElement;
import com.spectra.fieldforce.Model.AssignmentRequest;
import com.spectra.fieldforce.Model.SRRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SRInterface {
    @POST("index.php")
    Call<JsonElement> getSRDetail (@Body SRRequest srRequest );
}
