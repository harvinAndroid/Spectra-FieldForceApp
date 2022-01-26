package com.spectra.fieldforce.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.spectra.fieldforce.model.ArtifactRequest;
import com.spectra.fieldforce.model.AssignmentRequest;
import com.spectra.fieldforce.model.CanIdRequest;
import com.spectra.fieldforce.model.CanIdResponse;
import com.spectra.fieldforce.model.ChangeBinRequest;
import com.spectra.fieldforce.model.ChangeBinResponse;
import com.spectra.fieldforce.model.CommonMessageResponse;
import com.spectra.fieldforce.model.CommonResponse;
import com.spectra.fieldforce.model.EndtimeRequest;
import com.spectra.fieldforce.model.LoginResponse;
import com.spectra.fieldforce.model.gpon.request.AccountInfoRequest;
import com.spectra.fieldforce.model.gpon.request.AddBucketListRequest;
import com.spectra.fieldforce.model.gpon.request.AddItemConsumption;
import com.spectra.fieldforce.model.gpon.request.AddProvisioningRequest;
import com.spectra.fieldforce.model.gpon.request.AssociatedResquest;
import com.spectra.fieldforce.model.gpon.request.BucketListRequest;
import com.spectra.fieldforce.model.gpon.request.DeleteItemRequest;
import com.spectra.fieldforce.model.gpon.request.GetINSRequest;
import com.spectra.fieldforce.model.gpon.request.GetMaxCap;
import com.spectra.fieldforce.model.gpon.request.HoldWcrRequest;
import com.spectra.fieldforce.model.gpon.request.IRCompleteRequest;
import com.spectra.fieldforce.model.gpon.request.ItemConsumptionById;
import com.spectra.fieldforce.model.gpon.request.ReleaseMyBucket;
import com.spectra.fieldforce.model.gpon.request.ResendActivationCodeRequest;
import com.spectra.fieldforce.model.gpon.request.ResendCodeIRRequest;
import com.spectra.fieldforce.model.gpon.request.ResendNavRequest;
import com.spectra.fieldforce.model.gpon.request.SubmitApprovalRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateAppointmentRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateCpeMacRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateCustomerNetwork;
import com.spectra.fieldforce.model.gpon.request.UpdateFmsRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateGeneralDetails;
import com.spectra.fieldforce.model.gpon.request.UpdateIREngineer;
import com.spectra.fieldforce.model.gpon.request.UpdateManHoleRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateQualityParamIRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateQualityParamRequest;
import com.spectra.fieldforce.model.gpon.request.UpdateWcrEnggRequest;
import com.spectra.fieldforce.model.gpon.request.UploadWcrImgRequest;
import com.spectra.fieldforce.model.gpon.request.WcrCompleteRequest;
import com.spectra.fieldforce.model.gpon.response.AccInfoResponse;
import com.spectra.fieldforce.model.gpon.response.AccountDetailsResponse;
import com.spectra.fieldforce.model.gpon.response.CommonClassResponse;
import com.spectra.fieldforce.model.gpon.response.GetAllBucketList;
import com.spectra.fieldforce.model.gpon.response.GetFibreCable;
import com.spectra.fieldforce.model.gpon.response.GetFmsListResponse;
import com.spectra.fieldforce.model.gpon.response.GetInsResponse;
import com.spectra.fieldforce.model.gpon.response.GetItemConumptionByIdResponse;
import com.spectra.fieldforce.model.gpon.response.GetItemListResponse;
import com.spectra.fieldforce.model.gpon.response.GetManholeById;
import com.spectra.fieldforce.model.gpon.response.GetMaxCapResponse;
import com.spectra.fieldforce.model.gpon.response.GetMyBucketList;
import com.spectra.fieldforce.model.gpon.response.GetServiceResponse;
import com.spectra.fieldforce.model.gpon.response.GetSubItemListResponse;
import com.spectra.fieldforce.model.gpon.response.HoldReasonResponse;
import com.spectra.fieldforce.model.gpon.response.IrInfoResponse;
import com.spectra.fieldforce.model.gpon.response.WCRHoldCategoryResponse;
import com.spectra.fieldforce.model.ItemConsumption.DeleteItemConsumption;
import com.spectra.fieldforce.model.ItemConsumption.GetItemConsumption;
import com.spectra.fieldforce.model.ItemConsumption.GetItemConsumptionRequest;
import com.spectra.fieldforce.model.ItemConsumption.ItemConsumptionDetails;
import com.spectra.fieldforce.model.ItemConsumption.ItemConsumptionRequest;
import com.spectra.fieldforce.model.ItemConsumption.ItemResponse;
import com.spectra.fieldforce.model.ItemConsumption.ItemStatus;
import com.spectra.fieldforce.model.ItemConsumption.NrgpDetails;
import com.spectra.fieldforce.model.LoginRequest;
import com.spectra.fieldforce.model.MRTG;
import com.spectra.fieldforce.model.MrtgRequest;
import com.spectra.fieldforce.model.QuestionList.QuestionListRequest;
import com.spectra.fieldforce.model.RCRequest;
import com.spectra.fieldforce.model.SRRequest;
import com.spectra.fieldforce.model.SaveQuestionareList.SaveQuestionareList;
import com.spectra.fieldforce.model.SavetokenRequest;
import com.spectra.fieldforce.model.SendChangeBinRequest;
import com.spectra.fieldforce.model.SrDetailsListResponse;
import com.spectra.fieldforce.model.StarttimeRequest;
import com.spectra.fieldforce.model.gpon.response.WcrResponse;
import com.spectra.fieldforce.model.questionAnsResponse.QuestionAnswerList;
import com.spectra.fieldforce.salesapp.model.AddProductRequest;
import com.spectra.fieldforce.salesapp.model.BankListResponse;
import com.spectra.fieldforce.salesapp.model.CafDetailResponse;
import com.spectra.fieldforce.salesapp.model.CafRequest;
import com.spectra.fieldforce.salesapp.model.CreateCafReqest;
import com.spectra.fieldforce.salesapp.model.CreateLeadRequest;
import com.spectra.fieldforce.salesapp.model.CreateLeadResponse;
import com.spectra.fieldforce.salesapp.model.CreateQuoteRequest;
import com.spectra.fieldforce.salesapp.model.DeleteProductResponse;
import com.spectra.fieldforce.salesapp.model.DisqualifyLead;
import com.spectra.fieldforce.salesapp.model.GenQuoteResponse;
import com.spectra.fieldforce.salesapp.model.GetAllCafListResponse;
import com.spectra.fieldforce.salesapp.model.GetAllLeadRequest;
import com.spectra.fieldforce.salesapp.model.GetAllLeadResponse;
import com.spectra.fieldforce.salesapp.model.GetAllOppurtunityResponse;
import com.spectra.fieldforce.salesapp.model.GetApprovalRersponse;
import com.spectra.fieldforce.salesapp.model.GetBusinessSubSegRequest;
import com.spectra.fieldforce.salesapp.model.GetCafIRResponse;
import com.spectra.fieldforce.salesapp.model.GetCafNPResponse;
import com.spectra.fieldforce.salesapp.model.GetCafWCRResponse;
import com.spectra.fieldforce.salesapp.model.GetCityRequest;
import com.spectra.fieldforce.salesapp.model.GetCityResponse;
import com.spectra.fieldforce.salesapp.model.GetCompanyRequest;
import com.spectra.fieldforce.salesapp.model.GetCompanyResponse;
import com.spectra.fieldforce.salesapp.model.GetDisQualifyResponse;
import com.spectra.fieldforce.salesapp.model.GetDocCafReq;
import com.spectra.fieldforce.salesapp.model.GetDocCafResponse;
import com.spectra.fieldforce.salesapp.model.GetFeasibiltyResponse;
import com.spectra.fieldforce.salesapp.model.GetFirmTypeRequest;
import com.spectra.fieldforce.salesapp.model.GetFirmTypeResponse;
import com.spectra.fieldforce.salesapp.model.GetGroupResponse;
import com.spectra.fieldforce.salesapp.model.GetIndustryTypeResponse;
import com.spectra.fieldforce.salesapp.model.GetLeadAreaRequest;
import com.spectra.fieldforce.salesapp.model.GetLeadAreaRes;
import com.spectra.fieldforce.salesapp.model.GetLeadBuildingRequest;
import com.spectra.fieldforce.salesapp.model.GetLeadBuildingResponse;
import com.spectra.fieldforce.salesapp.model.GetLeadChannelRequest;
import com.spectra.fieldforce.salesapp.model.GetLeadChannelResponse;
import com.spectra.fieldforce.salesapp.model.GetLeadRequest;
import com.spectra.fieldforce.salesapp.model.GetLeadSourceRequest;
import com.spectra.fieldforce.salesapp.model.GetLeadSourceResp;
import com.spectra.fieldforce.salesapp.model.GetOppurtunityProductRequest;
import com.spectra.fieldforce.salesapp.model.GetOppurtunityProductResponse;
import com.spectra.fieldforce.salesapp.model.GetOppurtunityRequest;
import com.spectra.fieldforce.salesapp.model.GetOppurtunityResponse;
import com.spectra.fieldforce.salesapp.model.GetPriceListResponse;
import com.spectra.fieldforce.salesapp.model.GetPriceListRequest;
import com.spectra.fieldforce.salesapp.model.GetProductItemListRes;
import com.spectra.fieldforce.salesapp.model.GetProductListRequest;
import com.spectra.fieldforce.salesapp.model.GetRelationShipResponse;
import com.spectra.fieldforce.salesapp.model.GetServProvResponse;
import com.spectra.fieldforce.salesapp.model.GetStateResponse;
import com.spectra.fieldforce.salesapp.model.GetSubBusinessResponse;
import com.spectra.fieldforce.salesapp.model.LeadResponsee;
import com.spectra.fieldforce.salesapp.model.LeadSalutationRequest;
import com.spectra.fieldforce.salesapp.model.LostOppurtunityRequest;
import com.spectra.fieldforce.salesapp.model.ProdctResponse;
import com.spectra.fieldforce.salesapp.model.ProductListResponse;
import com.spectra.fieldforce.salesapp.model.QualifiedLeadRequest;
import com.spectra.fieldforce.salesapp.model.QualifyLeadResponse;
import com.spectra.fieldforce.salesapp.model.UpdateFlrRequest;
import com.spectra.fieldforce.salesapp.model.UpdateOppurtunity;
import com.spectra.fieldforce.salesapp.model.UpdateProductRequest;
import com.spectra.fieldforce.salesapp.model.UploadDocRequest;
import com.spectra.fieldforce.salesapp.model.ValidateResponse;
import com.spectra.fieldforce.salesapp.model.ValidateSalesResponse;
import com.spectra.fieldforce.salesapp.model.ValidateUserRequest;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ApiInterface {

    @POST ("index.php")
    Call<LoginResponse> performUserLogin(@Body LoginRequest loginRequest);
   // Call<User> performRegistration(@Query("name") String Name, @Query("user_name") String UserName, @Query("user_password") String UserPass);


    @POST ("index.php")
    Call<QuestionAnswerList> getQuestionList(@Body QuestionListRequest questionListRequest);

    @POST ("index.php")
    Call<ValidateResponse> validateUserReq(@Body ValidateUserRequest questionListRequest);

    @POST ("index.php")
    Call<ValidateSalesResponse> validateSalesUserReq(@Body ValidateUserRequest questionListRequest);


    @POST ("index.php")
    Call<CommonResponse> sendQuestionare(@Body SaveQuestionareList saveQuestionareList);


    @POST ("index.php")
    Call<CanIdResponse> getCanIdDetails(@Body CanIdRequest canIdRequest);

    @POST("index.php")
    Call<JsonElement> getSRDetail(@Body SRRequest srRequest);

    @POST ("index.php")
    Call< JsonElement > performOrderStarttime(@Body StarttimeRequest starttimeRequest);

    @POST ("index.php")
    Call< JsonElement > performSaveToken(@Body SavetokenRequest savetokenRequest);

    @POST("index.php")
    Call<JsonElement> getRCDetail(@Body RCRequest rcRequest);

    @POST("index.php")
    Call<MRTG> getMrtgRequest(@Body MrtgRequest mrtgRequest);

    @POST("index.php")
    Call<JsonElement> getChnageBinDetails(@Body ChangeBinRequest changeBinRequest);

    @POST("index.php")
    Call<ChangeBinResponse> sendBinDetails(@Body SendChangeBinRequest sendChangeBinRequest);

    @POST ("index.php")
    Call<SrDetailsListResponse> getSrDetailsList(@Body ChangeBinRequest changeBinRequest);

    @POST ("index.php")
    Call<JsonElement> UploadArtifacts(@Body ArtifactRequest artifactRequest);
/*

    @POST ("index.php")
    Call<QuestionareListResponse> getSaveQuestionAnsList(@Body GetQuestionAnsList getQuestionAnsList);
*/

    @POST ("index.php")
    Call< JsonElement > performUserAssignment ( @Body AssignmentRequest assignmentRequest );

    @POST ("index.php")
    Call< JsonElement > performOrderEndtime ( @Body EndtimeRequest endtimeRequest );

    @POST ("index.php")
    Call<GetItemConsumption> getItemConsumption (@Body ItemConsumptionRequest itemConsumptionRequest );

    @POST("index.php")
    Call<CommonResponse> setUnholdStatus(@Body CanIdRequest canIdRequest);

    @POST ("index.php")
    Call<ItemResponse> addItemConsumption (@Body JsonObject object );

    @POST ("index.php")
    Call<ItemResponse> editItemConsumption (@Body JsonObject object );

    @POST ("index.php")
    Call<ItemConsumptionDetails> getItemConsumptionDetails (@Body GetItemConsumptionRequest getItemConsumptionRequest );

    @POST ("index.php")
    Call<ItemResponse> deleteItemConsumptionDetails (@Body DeleteItemConsumption deleteItemConsumption );

    @POST ("index.php")
    Call<CommonResponse> getStatus (@Body ChangeBinRequest changeBinRequest );


    @POST ("index.php")
    Call<CommonResponse> saveStatus (@Body ChangeBinRequest changeBinRequest );

    @POST ("index.php")
    Call<ItemStatus> getMaterialStatus (@Body ChangeBinRequest changeBinRequest );

    @POST ("index.php")
    Call<NrgpDetails> getNrgpData (@Body AssignmentRequest itemConsumptionRequest );


      @POST("index.php")
    Call<AccInfoResponse> getAccountInfo(@Body AccountInfoRequest accountInfoRequest);

    @POST ("index.php")
    Call<WcrResponse> getWcrInfo(@Body AccountInfoRequest accountInfoRequest);

    @POST ("index.php")
    Call<IrInfoResponse> getIrInfo(@Body AccountInfoRequest accountInfoRequest);

    @POST ("index.php")
    Call<WCRHoldCategoryResponse> getHoldCategory(@Body AccountInfoRequest accountInfoRequest);


    @POST ("index.php")
    Call<GetItemListResponse> getConsumptionItemList(@Body AccountInfoRequest accountInfoRequest);


    @POST ("index.php")
    Call<GetFmsListResponse> getFmsList(@Body AccountInfoRequest accountInfoRequest);


    @POST ("index.php")
    Call<GetItemListResponse> getItemListName(@Body AccountInfoRequest accountInfoRequest);

    @POST ("index.php")
    Call<GetSubItemListResponse> getSubItem(@Body ItemConsumptionById itemConsumptionById);

    @POST ("index.php")
    Call<GetFibreCable> getFibreList(@Body AccountInfoRequest accountInfoRequest);

    @POST ("index.php")
    Call<CommonClassResponse> addItemConsumption(@Body AddItemConsumption addItemConsumption);

    @POST ("index.php")
    Call<CommonClassResponse> updateAssociateDetails(@Body AssociatedResquest associatedResquest);

    @POST ("index.php")
    Call<CommonClassResponse> updsteFmsDetails(@Body UpdateFmsRequest updateFmsRequest);

    @POST ("index.php")
    Call<CommonClassResponse> updateWcrEng(@Body UpdateWcrEnggRequest updateWcrEnggRequest);

    @POST ("index.php")
    Call<GetItemConumptionByIdResponse> getItemDetailsById(@Body ItemConsumptionById itemConsumptionById);

    @POST ("index.php")
    Call<CommonResponse> wcrEnggDetails(@Body UpdateWcrEnggRequest updateWcrEnggRequest);

    @POST ("index.php")
    Call<CommonClassResponse> updateHoldCategory(@Body HoldWcrRequest holdWcrRequest);

    @POST ("index.php")
    Call<CommonClassResponse> wcrComplete(@Body WcrCompleteRequest wcrCompleteRequest);

    @POST ("index.php")
    Call<CommonClassResponse> resendCodeWcr(@Body ResendActivationCodeRequest resendActivationCodeRequest);

    @POST ("index.php")
    Call<CommonClassResponse> updateQualityParamIR(@Body UpdateQualityParamIRequest updateQualityParamIRequest);

    @POST("index.php")
    Call<AccountDetailsResponse> getAccountDetails(@Body AccountInfoRequest accountInfoRequest);

    @POST("index.php")
    Call<CommonClassResponse> addProvisioning(@Body AddProvisioningRequest addProvisioningRequest);

    @POST("index.php")
    Call<CommonClassResponse> addManholeItem(@Body UpdateManHoleRequest updateManHoleRequest);

    @POST("index.php")
    Call<GetAllBucketList> getAllBucketList(@Body BucketListRequest bucketListRequest);


    @POST("index.php")
    Call<CommonClassResponse> deleteItemById(@Body DeleteItemRequest deleteItemRequest);


    @POST("index.php")
    Call<GetManholeById> editManholeById(@Body UpdateManHoleRequest updateManHoleRequest);

    @POST("index.php")
    Call<CommonClassResponse> getResendCodeIR(@Body ResendCodeIRRequest resendActivationCodeRequest);

    @POST("index.php")
    Call<CommonClassResponse> updateGeneralDetails(@Body UpdateGeneralDetails updateGeneralDetails);


    @POST("index.php")
    Call<CommonClassResponse> updateIrEnginer(@Body UpdateIREngineer updateIREngineer);

    @POST("index.php")
    Call<CommonClassResponse> updateCpeMac(@Body UpdateCpeMacRequest updateCpeMacRequest);

    @POST("index.php")
    Call<CommonClassResponse> updateIR(@Body   IRCompleteRequest irCompleteRequest);

    @POST("index.php")
    Call<CommonClassResponse> addItemToBucket(@Body AddBucketListRequest addBucketListRequest);

    @POST("index.php")
    Call<GetMyBucketList> getMyBucketList(@Body BucketListRequest bucketListRequest);

    @POST("index.php")
    Call<CommonClassResponse> releaseMyBucket(@Body ReleaseMyBucket releaseMyBucket);

    @POST ("index.php")
    Call<GetMaxCapResponse> getMaxCapValue(@Body GetMaxCap getMaxCap);

    @POST ("index.php")
    Call<CommonClassResponse> updateCustomerNetwork(@Body UpdateCustomerNetwork updateCustomerNetwork);

    @POST ("index.php")
    Call<CommonClassResponse> updateQualityParamReq(@Body UpdateQualityParamRequest updateQualityParamRequest);

    @POST ("index.php")
    Call<CommonClassResponse> submitApproval(@Body SubmitApprovalRequest submitApprovalRequest);

    @POST ("index.php")
    Call<CommonClassResponse> updateAppointmentDate(@Body UpdateAppointmentRequest updateAppointmentRequest);

    @POST ("index.php")
    Call<GetInsResponse> getInsDetails(@Body GetINSRequest getINSRequest);

    @POST ("index.php")
    Call<CommonClassResponse> UploadWcr(@Body UploadWcrImgRequest uploadWcrImgRequest);

    @POST ("index.php")
    Call<HoldReasonResponse> getHoldReason(@Body AccountInfoRequest accountInfoRequest);

    @POST ("index.php")
    Call<CommonClassResponse> submitNavWcr(@Body ResendNavRequest resendNavRequest);

    @POST ("index.php")
    Call<GetServiceResponse> getServiceSubItem(@Body ItemConsumptionById itemConsumptionById);


    @POST ("index.php")
    Call<CommonMessageResponse> getItemCodeStatus(@Body ResendActivationCodeRequest resendActivationCodeRequest);

    @POST ("index.php")
    Call<CreateLeadResponse> createLead(@Body CreateLeadRequest createLeadRewuest);

    @POST ("index.php")
    Call<CreateLeadResponse> updateOppurtunity(@Body UpdateOppurtunity updateOppurtunity);

    @POST ("index.php")
    Call<GetLeadBuildingResponse> getleadBuilding(@Body GetLeadBuildingRequest getLeadBuildingRequest);

    @POST ("index.php")
    Call<GetPriceListResponse> getPriceList(@Body GetPriceListRequest getProductListRequest);

    @POST ("index.php")
    Call<ProductListResponse> getProductList(@Body GetProductListRequest getProductListRequest);


    @POST ("index.php")
    Call<GetIndustryTypeResponse> getIndustry(@Body GetLeadBuildingRequest getLeadBuildingRequest);

    @POST ("index.php")
    Call<BankListResponse> getBankList(@Body CafRequest cafRequest);


    @POST ("index.php")
    Call<LeadResponsee> getLead(@Body GetLeadRequest getLeadRequest);

    @POST ("index.php")
    Call<GetOppurtunityProductResponse> getOppurtunityProduct(@Body GetOppurtunityProductRequest getOppurtunityProductRequest);

    @POST ("index.php")
    Call<CommonClassResponse> lostOppurtunityRequest(@Body LostOppurtunityRequest lostOppurtunityRequest);

    @POST ("index.php")
    Call<ProdctResponse> addProduct(@Body AddProductRequest addProductRequest);

    @POST ("index.php")
    Call<ProdctResponse> addQuote(@Body CreateQuoteRequest createQuoteRequest);


    @POST ("index.php")
    Call<DeleteProductResponse> deleteProduct(@Body AddProductRequest addProductRequest);

    @POST ("index.php")
    Call<GetProductItemListRes> addProductItem(@Body GetProductListRequest getProductListRequest);

    @POST ("index.php")
    Call<GetCafWCRResponse> getWCRList(@Body GetDocCafReq getDocCafReq);


    @POST ("index.php")
    Call<GetCafIRResponse> getIRList(@Body GetDocCafReq getDocCafReq);


    @POST ("index.php")
    Call<GetCafNPResponse> getNPList(@Body GetDocCafReq getDocCafReq);


    @POST ("index.php")
    Call<ProdctResponse> won(@Body GetProductListRequest getProductListRequest);

    @POST ("index.php")
    Call<GetOppurtunityResponse> getOppurtunity(@Body GetOppurtunityRequest getOppurtunityRequest);

    @POST ("index.php")
    Call<CafDetailResponse> getCaf(@Body CafRequest cafRequest);

    @POST ("index.php")
    Call<GetDocCafResponse> getDoc(@Body CafRequest cafRequest);

    @POST ("index.php")
    Call<DeleteProductResponse> uploadDoc(@Body UploadDocRequest uploadDocRequest);

    @POST ("index.php")
    Call<CafDetailResponse> createCaf(@Body CreateCafReqest createCafReqest);


    @POST ("index.php")
    Call<GetAllLeadResponse> getAllLead(@Body GetAllLeadRequest getAllLeadRequest);

    @POST ("index.php")
    Call<GetAllCafListResponse> getAllCAF(@Body GetAllLeadRequest getAllLeadRequest);


    @POST ("index.php")
    Call<GetAllOppurtunityResponse> getAllOppurtunity(@Body GetAllLeadRequest getAllLeadRequest);

    @POST ("index.php")
    Call<GetFeasibiltyResponse> getAllFeasibility(@Body GetProductListRequest getProductListRequest);

    @POST ("index.php")
    Call<GetApprovalRersponse> getApproval(@Body GetProductListRequest getProductListRequest);

    @POST ("index.php")
    Call<GenQuoteResponse> getQuote(@Body GetProductListRequest getProductListRequest);

    @POST ("index.php")
    Call<GetLeadChannelResponse> getleadChannel(@Body GetLeadChannelRequest getLeadChannelRequest);

    @POST ("index.php")
    Call<GetCompanyResponse> getComapnyList(@Body GetCompanyRequest getCompanyRequest);


    @POST ("index.php")
    Call<GetRelationShipResponse> getRelationList(@Body GetCompanyRequest getCompanyRequest);

    @POST ("index.php")
    Call<GetGroupResponse> getGroup(@Body GetCompanyRequest getCompanyRequest);

    @POST ("index.php")
    Call<CommonClassResponse> updateFlr(@Body UpdateFlrRequest updateFlrRequest);

    @POST ("index.php")
    Call<ProdctResponse> updateOppProduct(@Body UpdateProductRequest updateProductRequest);

    @POST ("index.php")
    Call<GetLeadSourceResp> getLeadSource(@Body GetLeadSourceRequest getLeadSourceRequest);

    @POST ("index.php")
    Call<GetLeadAreaRes> getLeadArea(@Body GetLeadAreaRequest getLeadAreaRequest);

    @POST ("index.php")
    Call<GetSubBusinessResponse> getSubBusSeg(@Body GetBusinessSubSegRequest getBusinessSubSegRequest);

    @POST ("index.php")
    Call<GetFirmTypeResponse> getFirmType(@Body GetFirmTypeRequest getFirmTypeRequest);

    @POST ("index.php")
    Call<GetServProvResponse> getServProv(@Body LeadSalutationRequest leadSalutationRequest);

    @POST ("index.php")
    Call<GetStateResponse> getStateList(@Body LeadSalutationRequest leadSalutationRequest);

    @POST ("index.php")
    Call<GetCityResponse> getCityList(@Body GetCityRequest getCityRequest);

    @POST ("index.php")
    Call<QualifyLeadResponse> qualifyReq(@Body QualifiedLeadRequest qualifiedLeadRequest);

    @POST ("index.php")
    Call<GetDisQualifyResponse> disQualifyReq(@Body DisqualifyLead disqualifyLead);


}
