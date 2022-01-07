package com.spectra.fieldforce.salesapp.model

data class LeadResponsee(
        val Response: ArrayList<LeadData>,
        val Status: String,
        val StatusCode: Int
)