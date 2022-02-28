package com.spectra.fieldforce.salesapp.model

data class GetCompetitorResponse(
    val Response:CompetitorResponse,
    val Status: String,
    val StatusCode: String
)

data class CompetitorResponse(
    val Data: ArrayList<CompetitorData>,
    val Message: String,
    val StatusCode: Int
)

data class CompetitorData(
    val Name: String
)