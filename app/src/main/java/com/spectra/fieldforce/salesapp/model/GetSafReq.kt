package com.spectra.fieldforce.salesapp.model

data class GetSafReq(
    val Action: String?,
    val Authkey: String?,
    val oppId: String?,
    val password: String?,
    val safNo: String?,
    val userName: String?
)