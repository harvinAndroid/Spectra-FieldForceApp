package com.spectra.fieldforce.salesapp.model

data class ProdctResponse(
    val Response: ProResponse,
    val Status: String,
    val StatusCode: String
)

data class ProResponse(
    val Message: String,
    val StatusCode: Int
)