package com.spectra.fieldforce.salesapp.model

data class GetLeadChannelResponse(
    val Response: ChnlResponse,
    val Status: String,
    val StatusCode: String
)

data class ChnlResponse(
    val Data: List<ChnlData>,
    val Message: String,
    val StatusCode: Int
)

data class ChnlData(
    val ChannelName: String
)