package com.spectra.fieldforce.salesapp.model

data class GetCafNPResponse(
    val Response: NPResponse,
    val Status: String,
    val StatusCode: String
)

data class NPResponse(
    val Data: ArrayList<NPData>,
    val Message: String,
    val StatusCode: Int
)

data class NPData(
    val NpId: String,
    val Ntw_Technology: String,
    val PodCode: String,
    val PodName: String,
    val ProvisioningStatus: String,
    val RouteType: String,
    val Zone: String
)