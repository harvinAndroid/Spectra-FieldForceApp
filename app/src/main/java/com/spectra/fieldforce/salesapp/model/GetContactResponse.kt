package com.spectra.fieldforce.salesapp.model

data class GetContactResponse(
    val Response: CntactResponse,
    val Status: String,
    val StatusCode: Int
)

data class CntactResponse(
    val Data: ArrayList<CntactData>,
    val Message: String,
    val StatusCode: Int
)

data class CntactData(
    val Area: String,
    val Building: String,
    val BusinessSegment: String,
    val CallAttempted: String,
    val CampaignName: String,
    val Channel: String,
    val City: String,
    val CompetitorName: String,
    val ContactId: String,
    val Disposition: String,
    val DncNumber: String,
    val EmailAddress: String,
    val FirstName: String,
    val FollowupDate: String,
    val FullName: String,
    val LastName: String,
    val MobileNumber: String,
    val MobileNumber2: String,
    val PlanCategory: String,
    val Remark: String,
    val Source: String,
    val SpecifyArea: String,
    val Specifybuilding: String,
    val State: String,
    val StatusReason: String,
    val AreaId:String,
    val BuildingId:String
)