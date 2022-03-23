package com.spectra.fieldforce.salesapp.model

data class GetFeasibiltyResponse(
    val Response: FeasResponse?,
    val Status: String?,
    val StatusCode: String?
)

data class FeasResponse(
    val Data: ArrayList<FeasData>?,
    val Message: String?,
    val StatusCode: Int?
)

data class FeasData(
    val ApproveLevel1: String?,
    val ApproveLevel2: String?,
    val Arc: String?,
    val Area: String?,
    val BuildingName: String?,
    val CityCorrect: String?,
    val CustomerName: String?,
    val EstimatedDoneBy: Boolean?,
    val FeasibilityId: String?,
    val FeasibilityStatus: String?,
    val IspName: String?,
    val LastMileType: String?,
    val MobileNo: String?,
    val Opportunity: String?,
    val OpportunityId: String?,
    val Otc: String?,
    val Owner: String?,
    val RedunancyRequired: Boolean?,
    val Remark: String?,
    val RouteType: Boolean?,
    val SubReason: String?,
    val ThirdPartyFeasibilityRequired: Boolean?,
    val TotalCalculatedCost: String?,
    val ApprovalStatus:String?
)

/*

data class GetFeasibiltyResponse(
    val Response: FeasResponse,
    val Status: String,
    val StatusCode: String
)

data class FeasResponse(
    val Data: ArrayList<FeasData>,
    val Message: String,
    val StatusCode: Int
)

data class FeasData(
    val ApproveLevel1: String,
    val ApproveLevel2: String,
    val Area: String,
    val BuildingName: String,
    val CityCorrect: String,
    val CustomerName: String,
    val EstimatedDoneBy: Boolean,
    val FeasibilityId: String,
    val FeasibilityStatus: String,
    val LastMileType: String,
    val MobileNo: String,
    val Opportunity: String,
    val OpportunityId: String,
    val RedunancyRequired: Boolean,
    val Remark: String,
    val RouteType: Boolean,
    val SubReason: String,
    val ThirdPartyFeasibilityRequired: Boolean
)*/
