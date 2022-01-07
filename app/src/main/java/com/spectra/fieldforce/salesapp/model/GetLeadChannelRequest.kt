package com.spectra.fieldforce.salesapp.model

data class GetLeadChannelRequest(
    val Action: String,
    val Authkey: String,
    val channelName: Any,
    val password: String,
    val userName: String
)