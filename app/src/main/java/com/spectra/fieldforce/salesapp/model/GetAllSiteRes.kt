package com.spectra.fieldforce.salesapp.model

data class GetAllSiteRes(
    val Response: SiteResponse,
    val Status: String,
    val StatusCode: String
)

data class SiteResponse(
    val Data: ArrayList<SiteData>,
    val Message: String,
    val StatusCode: Int
)

data class SiteData(
    val Address: String,
    val BusinessSegment: String,
    val City: String,
    val Company: String,
    val ContactPersonName: String,
    val CustomerContactNumber: String,
    val CustomerEmailAddress: String,
    val CustomerEmergencyContactNumber: String,
    val CustomerSegment: String,
    val Group: String,
    val NoOfLanPool: String,
    val NoOfWanlinks: String,
    val OpportunityId: String,
    val OpportunityName: String,
    val PinCode: String,
    val PriceList: String,
    val Relationship: String,
    val SiteCategory: String,
    val SiteID: String,
    val SiteStatus: String,
    val SiteType: String,
    val SolutionDeploymentMode: String,
    val State: String,
    val SubBusinessSegment: String,
    val TotalAmount: String,
    val TypeOFOrder: String,
    val OppStatus: String
)