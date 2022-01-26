package com.spectra.fieldforce.salesapp.model

data class GetCafIRResponse(
    val Response: IRResponse,
    val Status: String,
    val StatusCode: String
)

data class IRResponse(
    val Data: ArrayList<IRData>,
    val Message: String,
    val StatusCode: Int
)

data class IRData(
    val IRId: String,
    val InstallationDate: String,
    val IrStatus: String,
    val Ntw_Technology: String,
    val PodCode: String,
    val PodName: String,
    val RouteType: String,
    val Zone: String
)