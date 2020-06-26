package com.spectra.fieldforce;
import com.spectra.fieldforce.Model.EndtimeRequest;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface EndtimeInterface {
    @POST ("index.php")
    Call< JsonElement > performOrderEndtime ( @Body EndtimeRequest endtimeRequest );
        }
