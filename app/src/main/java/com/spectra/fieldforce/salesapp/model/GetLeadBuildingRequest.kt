package com.spectra.fieldforce.salesapp.model

data class GetLeadBuildingRequest(
    val Action: String,
    val Authkey: String,
    val areaCode: String,
    val areaName: String,
    val password: String,
    val userName: String
)