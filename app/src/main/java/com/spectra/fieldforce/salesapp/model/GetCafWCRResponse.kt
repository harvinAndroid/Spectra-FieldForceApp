package com.spectra.fieldforce.salesapp.model

data class GetCafWCRResponse(
    val Response: WCRResponse,
    val Status: String,
    val StatusCode: String
)

data class WCRResponse(
    val Data: ArrayList<WCRData>,
    val Message: String,
    val StatusCode: Int
)

data class WCRData(
    val CreatedOn: String,
    val PodCode: String,
    val PodName: String,
    val RouteType: String,
    val WcrID: String,
    val WcrStatus: String,
    val Zone: String,
    val Owner:String
)