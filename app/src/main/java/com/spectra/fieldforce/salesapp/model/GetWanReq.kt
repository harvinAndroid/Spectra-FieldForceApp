package com.spectra.fieldforce.salesapp.model

data class GetWanReq(
    val Action: String?,
    val Authkey: String?,
    val password: String?,
    val siteId: String?,
    val userName: String?,
    val wanNo: String?
)