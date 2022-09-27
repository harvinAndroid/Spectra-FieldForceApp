package com.spectra.fieldforce.kaizalaapp.model

data class KGetCityRes(
    val Response: MutableList<KCityResponse>,
    val Status: String,
    val StatusCode: String
)

data class KCityResponse(
    val CityCode: String,
    val CityName: String,
    val StateCode: String
)