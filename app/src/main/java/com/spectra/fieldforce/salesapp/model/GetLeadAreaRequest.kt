package com.spectra.fieldforce.salesapp.model

data class GetLeadAreaRequest(
    val Action: String?,
    val Authkey: String?,
    val cityCode: String?,
    val cityName: String?,
    val stateCode: String?,
    val userName: String?,
    val password: String?,
    val restrictedarea:Boolean?
)