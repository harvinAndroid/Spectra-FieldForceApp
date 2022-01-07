package com.spectra.fieldforce.salesapp.model

data class CityResponse(
    val Data: ArrayList<CityData>,
    val Message: String,
    val StatusCode: Int
)