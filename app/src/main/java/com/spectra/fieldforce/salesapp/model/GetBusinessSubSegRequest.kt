package com.spectra.fieldforce.salesapp.model

data class GetBusinessSubSegRequest(
    val Action: String,
    val Authkey: String,
    val bussSegment: String
)