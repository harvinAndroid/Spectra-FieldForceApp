package com.spectra.fieldforce.salesapp.model

data class PriceResponse(
        val Data: ArrayList<PriceData>,
        val Message: String,
        val StatusCode: Int
)