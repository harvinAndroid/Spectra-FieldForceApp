package com.spectra.fieldforce.salesapp.model

data class AreaResponse(
    val Data: ArrayList<AreaData>,
    val Message: String,
    val StatusCode: Int
)