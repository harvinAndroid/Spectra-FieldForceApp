package com.spectra.fieldforce.kaizalaapp.model

data class KContResponse(
    val Response: ConttKResponse,
    val Status: String,
    val StatusCode: Int
)

data class ConttKResponse(
    val Area: String,
    val AreaName: String,
    val Building: String,
    val BuildingName: String,
    val BusSeg: String,
    val CampaignName: String,
    val Channel: String,
    val City: String,
    val CityName: String,
    val CompetitorName: String,
    val ContactId: String,
    val CreationDate: String,
    val Disposition: Int,
    val EmailAddress: String,
    val FirstName: String,
    val FollowupDate: String,
    val LastName: String,
    val MobileNumber1: String,
    val MobileNumber2: String,
    val PlanCategory: String,
    val Remarks: String,
    val Society: String,
    val SocietyName: String,
    val Source: String,
    val SpecifyArea: String,
    val SpecifyBuilding: String,
    val SpecifySociety: String,
    val State: String,
    val StateName: String,
    val StatusName: String,
    val StatusReason: Int
)