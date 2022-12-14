package com.spectra.fieldforce.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.spectra.fieldforce.kaizalaapp.model.KCafCreatedRespo;
import com.spectra.fieldforce.kaizalaapp.model.KCafUpdateRes;
import com.spectra.fieldforce.kaizalaapp.model.KCompResponse;
import com.spectra.fieldforce.kaizalaapp.model.KContResponse;
import com.spectra.fieldforce.kaizalaapp.model.KContactRespo;
import com.spectra.fieldforce.kaizalaapp.model.KCreateCafReq;
import com.spectra.fieldforce.kaizalaapp.model.KCreateConatactReq;
import com.spectra.fieldforce.kaizalaapp.model.KCreateLeadReq;
import com.spectra.fieldforce.kaizalaapp.model.KGetAllContact;
import com.spectra.fieldforce.kaizalaapp.model.KGetAllLeadRequest;
import com.spectra.fieldforce.kaizalaapp.model.KGetAreaRes;
import com.spectra.fieldforce.kaizalaapp.model.KGetBuildingReq;
import com.spectra.fieldforce.kaizalaapp.model.KGetBuildingRes;
import com.spectra.fieldforce.kaizalaapp.model.KGetCafReq;
import com.spectra.fieldforce.kaizalaapp.model.KGetCafResp;
import com.spectra.fieldforce.kaizalaapp.model.KGetCityRes;
import com.spectra.fieldforce.kaizalaapp.model.KGetCompanyResponse;
import com.spectra.fieldforce.kaizalaapp.model.KGetContactReq;
import com.spectra.fieldforce.kaizalaapp.model.KGetLeadReq;
import com.spectra.fieldforce.kaizalaapp.model.KGetLeadRes;
import com.spectra.fieldforce.kaizalaapp.model.KGetPlanCatRes;
import com.spectra.fieldforce.kaizalaapp.model.KGetProductReq;
import com.spectra.fieldforce.kaizalaapp.model.KGetProductRespo;
import com.spectra.fieldforce.kaizalaapp.model.KGetRelationReq;
import com.spectra.fieldforce.kaizalaapp.model.KGetRelationRes;
import com.spectra.fieldforce.kaizalaapp.model.KGetSocietyReq;
import com.spectra.fieldforce.kaizalaapp.model.KGetSocietyRes;
import com.spectra.fieldforce.kaizalaapp.model.KUpdateContactRes;
import com.spectra.fieldforce.model.AddBucketListRequest;
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
import com.spectra.fieldforce.model.ValidateUserReq;
import com.spectra.fieldforce.model.ValidateUserResponse;
import com.spectra.fieldforce.model.gpon.request.AccountInfoRequest;
import com.spectra.fieldforce.model.gpon.request.AddItemConsumption;
import com.spectra.fieldforce.model.gpon.request.AddProvisioningRequest;
import com.spectra.fieldforce.model.gpon.request.AssociatedResquest;
import com.spectra.fieldforce.model.gpon.request.BucketListRequest;
import com.spectra.fieldforce.model.gpon.request.CreateWcrIrDocReq;
import com.spectra.fieldforce.model.gpon.request.DeleteItemRequest;
import com.spectra.fieldforce.model.gpon.request.GetDocDetailsReq;
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
import com.spectra.fieldforce.model.gpon.response.GetWcrDocResponse;
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
import com.spectra.fieldforce.salesapp.model.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ApiInterface {

    @POST ("index.php")
    Call<LoginResponse> performUserLogin(@Body LoginRequest loginRequest);


    @POST ("index.php")
    Call<QuestionAnswerList> getQuestionList(@Body QuestionListRequest questionListRequest);

    @POST ("index.php")
    Call<ValidateResponse> validateUserReq(@Body ValidateUserRequest validateUserRequest);

    @POST ("index.php")
    Call<ValidateUserResponse> validateReq(@Body ValidateUserReq validateUserReq);

    @POST ("index.php")
    Call<ValidateSalesResponse> validateSalesUserReq(@Body ValidateUserRequest validateUserRequest);


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
    Call<CreateLeadResponse> createContact(@Body CreateContactRequest createContactRequest);

    @POST ("index.php")
    Call<CreateLeadResponse> updateOppurtunity(@Body UpdateOppurtunity updateOppurtunity);

    @POST ("index.php")
    Call<CreateLeadResponse> createSite(@Body CreateSiteReq createSiteReq);

    @POST ("index.php")
    Call<CreateLeadResponse> createLan(@Body CreateLanReq createLanReq);

    @POST ("index.php")
    Call<CreateLeadResponse> createWan(@Body CreateWanReq createWanReq);

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
    Call<GetContactResponse> getContact(@Body GetContactRequest getContactRequest);

    @POST ("index.php")
    Call<KContResponse> getKContact(@Body KGetContactReq kGetContactReq);

    @POST ("index.php")
    Call<KGetLeadRes> getKLead(@Body KGetLeadReq kGetLeadReq);

    @POST ("index.php")
    Call<KGetCafResp> getKCaf(@Body KGetCafReq kGetCafReq);



    @POST ("index.php")
    Call<GetOppurtunityProductResponse> getOppurtunityProduct(@Body GetOppurtunityProductRequest getOppurtunityProductRequest);

    @POST ("index.php")
    Call<CommonClassResponse> lostOppurtunityRequest(@Body LostOppurtunityRequest lostOppurtunityRequest);

    @POST ("index.php")
    Call<GetAllSiteRes> getSiteDetails(@Body GetSiteDetailReq getSiteDetailReq);

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
    Call<GetRFSResponse> getRFSList(@Body GetDocCafReq getDocCafReq);


    @POST ("index.php")
    Call<GetCafIRResponse> getIRList(@Body GetDocCafReq getDocCafReq);


    @POST ("index.php")
    Call<GetCafNPResponse> getNPList(@Body GetDocCafReq getDocCafReq);


    @POST ("index.php")
    Call<ProdctResponse> won(@Body GetProductListRequest getProductListRequest);

    @POST ("index.php")
    Call<ProdctResponse> createPreTask(@Body CreatePreTaskReq createPreTaskReq);

    @POST ("index.php")
    Call<ProdctResponse> createSafPreTask(@Body CafPreSalesTaskReq cafPreSalesTaskReq);


    @POST ("index.php")
    Call<ProdctResponse> reOpenOpp(@Body GetOppurtunityRequest getOppurtunityRequest);

    @POST ("index.php")
    Call<GetOppurtunityResponse> getOppurtunity(@Body GetOppurtunityRequest getOppurtunityRequest);

    @POST ("index.php")
    Call<CafDetailResponse> getCaf(@Body CafRequest cafRequest);

    @POST ("index.php")
    Call<GetSafRes> getSaf(@Body GetSafReq safReq);

    @POST ("index.php")
    Call<ReportResponse> getSaf(@Body SubmitSafReq cafRequest);

    @POST ("index.php")
    Call<GetDocCafResponse> getDoc(@Body CafRequest cafRequest);

    @POST ("index.php")
    Call<GetWcrDocResponse> getInstallationDoc(@Body GetDocDetailsReq getDocDetailsReq );

    @POST ("index.php")
    Call<GetPdfResponse> getPdf(@Body CafPdfRequest cafPdfRequest);

    @POST ("index.php")
    Call<ReportResponse> shareEmail(@Body CafPdfRequest cafPdfRequest);

    @POST ("index.php")
    Call<DeleteProductResponse> uploadDoc(@Body UploadDocRequest uploadDocRequest);

    @POST ("index.php")
    Call<DeleteProductResponse> uploadWcrDoc(@Body CreateWcrIrDocReq createWcrIrDocReq);


    @POST ("index.php")
    Call<CafDetailResponse> createCaf(@Body CreateCafReqest createCafReqest);

    @POST ("index.php")
    Call<CafDetailResponse> createSaf(@Body CreateSafReq createSafReq);


    @POST ("index.php")
    Call<GetAllLeadResponse> getAllLead(@Body GetAllLeadRequest getAllLeadRequest);

    @POST ("index.php")
    Call<FFASRBarChartResponse> getSrCount(@Body FFASRBarChartReq ffasrBarChartReq);


    @POST ("index.php")
    Call<GetAllContactResponse> getAllContact(@Body GetAllLeadRequest getAllLeadRequest);

    @POST ("index.php")
    Call<GetAllContactResponse> getKAllContact(@Body KGetAllLeadRequest kGetAllLeadRequest);

    @POST ("index.php")
    Call<GetAllCafListResponse> getAllCAF(@Body GetAllLeadRequest getAllLeadRequest);

    @POST ("index.php")
    Call<AllSafRes> getAllSAF(@Body GetAllLeadRequest getAllLeadRequest);


    @POST ("index.php")
    Call<GetAllOppurtunityResponse> getAllOppurtunity(@Body GetAllLeadRequest getAllLeadRequest);

    @POST ("index.php")
    Call<GetFeasibiltyResponse> getAllFeasibility(@Body GetProductListRequest getProductListRequest);

    @POST ("index.php")
    Call<GetApprovalRersponse> getApproval(@Body GetProductListRequest getProductListRequest);

    @POST ("index.php")
    Call<GetPreSaleDetail> getPreTaskList(@Body CreatePreTaskReq createPreTaskReq);

    @POST ("index.php")
    Call<GetPreSaleDetail> getSafPreTaskList(@Body CafPreSalesTaskReq cafPreSalesTaskReq);


    @POST ("index.php")
    Call<GenQuoteResponse> getQuote(@Body GetProductListRequest getProductListRequest);

    @POST ("index.php")
    Call<GetAllSiteRes> getAllSiteDetails(@Body GetAllSiteReq getAllSiteReq);

    @POST ("index.php")
    Call<GetLanRes> getAllLan(@Body GetAllLanReq getAllLanReq);

    @POST ("index.php")
    Call<GetWorkOrderRes> getWorkOrder(@Body GetSafReq getSafReq);

    @POST ("index.php")
    Call<GetWanRes> getAllWan(@Body GetWanReq getWanReq);

    @POST ("index.php")
    Call<GetLeadChannelResponse> getleadChannel(@Body GetLeadChannelRequest getLeadChannelRequest);

    @POST ("index.php")
    Call<GetCompanyResponse> getComapnyList(@Body GetCompanyRequest getCompanyRequest);

    @POST ("index.php")
    Call<KGetCompanyResponse> getKComapnyList(@Body GetCompanyRequest getCompanyRequest);


    @POST ("index.php")
    Call<GetRelationShipResponse> getRelationList(@Body GetCompanyRequest getCompanyRequest);

    @POST ("index.php")
    Call<KGetRelationRes> getKRelationList(@Body KGetRelationReq kGetRelationReq);

    @POST ("index.php")
    Call<GetGroupResponse> getGroup(@Body GetCompanyRequest getCompanyRequest);

    @POST ("index.php")
    Call<CommonClassResponse> updateFlr(@Body UpdateFlrRequest updateFlrRequest);

    @POST ("index.php")
    Call<ProdctResponse> updateOppProduct(@Body UpdateProductRequest updateProductRequest);

    @POST ("index.php")
    Call<GetLeadSourceResp> getLeadSource(@Body GetLeadSourceRequest getLeadSourceRequest);

    @POST ("index.php")
    Call<GetLeadSourceResp> createKaiContact(@Body KCreateLeadReq kCreateLeadReq);

    @POST ("index.php")
    Call<KCafCreatedRespo> createKaiCaf(@Body KCreateCafReq kCreateCafReq);

    @POST ("index.php")
    Call<KCafUpdateRes> updateKaiCaf(@Body KCreateCafReq kCreateCafReq);

    @POST ("index.php")
    Call<KContactRespo> creaKaiContact(@Body KCreateConatactReq kCreateLeadReq);

    @POST ("index.php")
    Call<GetLeadSourceResp> createKaiLead(@Body KCreateConatactReq kCreateConatactReq);


    @POST ("index.php")
    Call<KUpdateContactRes> updateKaiContact(@Body KCreateConatactReq kCreateConatactReq);

    @POST ("index.php")
    Call<GetCompetitorResponse> getCompetitorList(@Body GetLeadSourceRequest getLeadSourceRequest);

    @POST ("index.php")
    Call<KCompResponse> getCompList(@Body GetLeadSourceRequest getLeadSourceRequest);

    @POST ("index.php")
    Call<GetPlanCategoryRes> getPlanCategory(@Body PlanCategoryRequest planCategoryRequest);

    @POST ("index.php")
    Call<KGetPlanCatRes> kgetPlanCat(@Body PlanCategoryRequest planCategoryRequest);

    @POST ("index.php")
    Call<GetLeadAreaRes> getLeadArea(@Body GetLeadAreaRequest getLeadAreaRequest);

    @POST ("index.php")
    Call<KGetAreaRes> getKArea(@Body GetLeadAreaRequest getLeadAreaRequest);

    @POST ("index.php")
    Call<KGetBuildingRes> getKBuilding(@Body KGetBuildingReq kGetBuildingReq);

    @POST ("index.php")
    Call<KGetProductRespo> getKProduct(@Body KGetProductReq kGetProductReq);


    @POST ("index.php")
    Call<KGetSocietyRes> getKSociety(@Body KGetSocietyReq kGetSocietyReq);

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
    Call<KGetCityRes> getKCityList(@Body GetCityRequest getCityRequest);

    @POST ("index.php")
    Call<GetVerticalData> getVertical(@Body GetCityRequest getCityRequest);

    @POST ("index.php")
    Call<CafBillingCityResponse> getBillingCityList(@Body GetCityRequest getCityRequest);

    @POST ("index.php")
    Call<QualifyLeadResponse> qualifyReq(@Body QualifiedLeadRequest qualifiedLeadRequest);

    @POST ("index.php")
    Call<GetDisQualifyResponse> disQualifyReq(@Body DisqualifyLead disqualifyLead);
}
