package com.spectra.fieldforce.salesapp.model

data class GetProductListRequest(
    val Action: String?,
    val Authkey: String?,
    val oppid: String?,
    val password: String?,
    val userName: String?,
    val siteId:String?,
    val reason:String?
)
