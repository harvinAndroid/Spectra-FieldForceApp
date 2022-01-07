package com.spectra.fieldforce.salesapp.model

data class GetFirmTypeResponse(
    val Response: FirmResponse,
    val Status: String,
    val StatusCode: String
)

data class FirmResponse(
    val Data: List<FirmData>,
    val Message: String,
    val StatusCode: Int
)

data class FirmData(
    val Text: String,
    val Value: Int
)