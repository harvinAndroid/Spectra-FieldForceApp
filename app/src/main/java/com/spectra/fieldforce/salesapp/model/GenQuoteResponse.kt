package com.spectra.fieldforce.salesapp.model

data class GenQuoteResponse(
    val Response: QuoteResponse,
    val Status: String,
    val StatusCode: String
)

data class QuoteResponse(
    val Data: ArrayList<QuoteData>,
    val Message: String,
    val StatusCode: Int
)

data class QuoteData(
    val OpportunityName: String,
    val Quoteid: String,
    val Status: String
)