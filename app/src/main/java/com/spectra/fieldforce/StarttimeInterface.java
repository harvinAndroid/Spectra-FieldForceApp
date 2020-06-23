package com.spectra.fieldforce;
import com.spectra.fieldforce.Model.StarttimeRequest;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface StarttimeInterface {
    @POST ("index.php")
    Call< JsonElement > performOrderStarttime ( @Body StarttimeRequest starttimeRequest );
}
