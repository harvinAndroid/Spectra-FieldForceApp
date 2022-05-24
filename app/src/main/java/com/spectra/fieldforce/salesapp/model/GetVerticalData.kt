package com.spectra.fieldforce.salesapp.model

data class GetVerticalData(
    val Response: VerticalResponse,
    val Status: String,
    val StatusCode: String
)

data class VerticalResponse(
    val Data: ArrayList<VerticalData>,
    val Message: String,
    val StatusCode: Int
)

data class VerticalData(
    val VerticalId: String,
    val VerticalName: String
)