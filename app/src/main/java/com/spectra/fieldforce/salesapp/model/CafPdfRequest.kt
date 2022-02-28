package com.spectra.fieldforce.salesapp.model

data class CafPdfRequest(
    val Action: String,
    val Authkey: String,
    val no: String?,
    val password: String?,
    val report: String?,
    val userName: String?
)