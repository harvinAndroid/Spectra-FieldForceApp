package com.spectra.fieldforce.salesapp.model

data class ResponseBus(
    val Data: ArrayList<SubBusData>,
    val Status: String,
    val Message: String
)