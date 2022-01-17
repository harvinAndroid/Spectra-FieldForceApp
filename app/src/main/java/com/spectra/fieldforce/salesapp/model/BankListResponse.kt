package com.spectra.fieldforce.salesapp.model

data class BankListResponse(
    val Response: BankResponse,
    val Status: String,
    val StatusCode: String
)

data class BankResponse(
    val Data: ArrayList<BankData>,
    val Message: String,
    val StatusCode: Int
)

data class BankData(
    val BankId: String,
    val Bankname: String
)