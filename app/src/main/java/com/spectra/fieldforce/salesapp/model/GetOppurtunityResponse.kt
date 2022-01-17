package com.spectra.fieldforce.salesapp.model

data class GetOppurtunityResponse(
    val Response: OppurtunityResponse,
    val Status: String,
    val StatusCode: String
)

data class OppurtunityResponse(
    val Data: OppurtunityData,
    val Message: String,
    val StatusCode: Int
)

data class OppurtunityData(
    val Area: String,
    val BuildingNo: String,
    val Buildingname: String,
    val Businesssegment: String?,
    val City: String,
    val Company: String,
    val CompanySelf: String,
    val Companyname: String,
    val ContactPerson: String,
    val Country: String,
    val CustomerSegment: String,
    val Emailaddress: String,
    val Existingprovider: String,
    val Firewall: String,
    val FirewallAwc: String,
    val Firmtype: String,
    val Floor: String,
    val Group: String,
    val Industry: String,
    val LastMileType: String,
    val Lco: String,
    val LeadId: String,
    val Media: String,
    val Mobilephone: String,
    val OppId: String,
    val OriginatingLead: String,
    val PoLock: String,
    val PoNext: String,
    val PostalCode: String,
    val Price: String,
    val PriceList: Any,
    val Redunancy: Boolean,
    val Relationship: String,
    val Salutation: String,
    val State: String,
    val Status: String,
    val Subbusinesssegment: String,
    val Topic: String,
    val UpTimesla: String,
    val leadname: String,
    val Building_BuildingStatus:String,
    val CreateAreaOrBuilding:String,
    val TPFeasibilty:String,
    val Reason:String
)