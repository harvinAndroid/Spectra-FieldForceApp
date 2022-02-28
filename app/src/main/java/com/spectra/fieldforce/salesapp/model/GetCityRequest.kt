package com.spectra.fieldforce.salesapp.model

data class GetCityRequest(
    val Action: String?,
    val Authkey: String?,
    val password: String?,
    val stateCode: String?,
    val userName: String?
)