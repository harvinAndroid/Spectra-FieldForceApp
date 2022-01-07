package com.spectra.fieldforce.salesapp.model

data class GetServProvResponse(
    val Response: ServResponse,
    val Status: String,
    val StatusCode: String
)

data class ServResponse(
    val Data: List<ServData>,
    val Message: String,
    val StatusCode: Int
)

data class ServData(
    val Text: String,
    val Value: Int
)