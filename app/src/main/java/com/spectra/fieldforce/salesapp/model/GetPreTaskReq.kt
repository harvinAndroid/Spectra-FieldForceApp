package com.spectra.fieldforce.salesapp.model

data class GetPreTaskReq(
    val Action: String,
    val Authkey: String,
    val oppId: String,
    val password: String,
    val preSalesId: String,
    val userName: String
)