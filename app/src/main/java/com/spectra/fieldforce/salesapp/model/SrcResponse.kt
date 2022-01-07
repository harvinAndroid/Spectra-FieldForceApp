package com.spectra.fieldforce.salesapp.model

data class SrcResponse(
    val Data: MutableList<SrcData>,
    val Message: String,
    val StatusCode: Int
)