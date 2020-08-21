package com.spectra.fieldforce;

import com.spectra.fieldforce.Model.CanIdRequest;
import com.spectra.fieldforce.Model.CanIdResponse;
import com.spectra.fieldforce.Model.ChangeBinRequest;
import com.spectra.fieldforce.Model.ChnageBinResponse;
import com.spectra.fieldforce.Model.CommonResponse;
import com.spectra.fieldforce.Model.LoginRequest;
import com.google.gson.JsonElement;
import com.spectra.fieldforce.Model.MRTG;
import com.spectra.fieldforce.Model.MrtgRequest;

import com.spectra.fieldforce.Model.QuestionListRequest;
import com.spectra.fieldforce.Model.QuestionListResponse;
import com.spectra.fieldforce.Model.QuestionareList;
import com.spectra.fieldforce.Model.RCRequest;
import com.spectra.fieldforce.Model.SRRequest;
import com.spectra.fieldforce.Model.SavetokenRequest;
import com.spectra.fieldforce.Model.SendChangeBinRequest;
import com.spectra.fieldforce.Model.SrDeatilsList;
import com.spectra.fieldforce.Model.SrDetailsListResponse;
import com.spectra.fieldforce.Model.StarttimeRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ApiInterface {

    @POST ("index.php")
    Call<JsonElement> performUserLogin ( @Body LoginRequest loginRequest );
   // Call<User> performRegistration(@Query("name") String Name, @Query("user_name") String UserName, @Query("user_password") String UserPass);


    @POST ("index.php")
    Call<QuestionListResponse> getQuestionList (@Body QuestionListRequest questionListRequest );


    @POST ("index.php")
    Call<CommonResponse> sendQuestionare (@Body QuestionareList questionListRequest );


    @POST ("index.php")
    Call<CanIdResponse> getCanIdDetails (@Body CanIdRequest canIdRequest );

    @POST("index.php")
    Call<JsonElement> getSRDetail (@Body SRRequest srRequest );

    @POST ("index.php")
    Call< JsonElement > performOrderStarttime ( @Body StarttimeRequest starttimeRequest );

    @POST ("index.php")
    Call< JsonElement > performSaveToken ( @Body SavetokenRequest savetokenRequest );

    @POST("index.php")
    Call<JsonElement> getRCDetail (@Body RCRequest rcRequest );

    @POST("index.php")
    Call<MRTG> getMrtgRequest (@Body MrtgRequest mrtgRequest );

    @POST("index.php")
    Call<ChnageBinResponse> getChnageBinDetails (@Body ChangeBinRequest changeBinRequest );

    @POST("index.php")
    Call<CommonResponse> sendBinDetails (@Body SendChangeBinRequest sendChangeBinRequest );

    @POST ("index.php")
    Call<SrDetailsListResponse> getSrDetailsList (@Body ChangeBinRequest changeBinRequest );
}
