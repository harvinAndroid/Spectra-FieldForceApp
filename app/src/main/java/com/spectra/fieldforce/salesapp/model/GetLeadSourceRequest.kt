package com.spectra.fieldforce.salesapp.model

data class GetLeadSourceRequest(
    val Action: String,
    val Authkey: String,
    val channelName: String,
    val userName:String,
    val password:String
)