package com.spectra.fieldforce.salesapp.model

data class GetIndustryTypeResponse(
    val Response: ArrayList<IndustryResponse>,
    val Status: String,
    val StatusCode: String
)

