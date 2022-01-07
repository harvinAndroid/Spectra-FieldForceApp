package com.spectra.fieldforce.salesapp.model

data class AllLeadResponse(
        val Data: ArrayList<AllLeadData>,
        val Message: String,
        val StatusCode: Int
)