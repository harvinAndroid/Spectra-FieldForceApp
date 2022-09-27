package com.spectra.fieldforce.kaizalaapp.model

data class KCompResponse(
    val Response: MutableList<CompResponse>,
    val Status: String,
    val StatusCode: String
)

data class CompResponse(
    val CompetitorName: String
)