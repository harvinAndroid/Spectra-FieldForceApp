package com.spectra.fieldforce.salesapp.model

data class CafBillingCityResponse(
    val Response: BillResponse,
    val Status: String,
    val StatusCode: String
)

data class BillResponse(
    val Data: ArrayList<BillData>,
    val Message: String,
    val StatusCode: Int
)

data class BillData(
    val CityCode: String,
    val CityName: String,
    val IsOperationalCity: Boolean
)