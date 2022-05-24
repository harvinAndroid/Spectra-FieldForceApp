package com.spectra.fieldforce.salesapp.model

data class GetAllLanReq(
    val Action: String?,
    val Authkey: String?,
    val lanNo: String?,
    val password: String?,
    val siteId: String?,
    val userName: String?
)