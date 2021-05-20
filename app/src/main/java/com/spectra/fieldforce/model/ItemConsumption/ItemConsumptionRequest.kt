package com.spectra.fieldforce.model.ItemConsumption

data class ItemConsumptionRequest(
    val Action: String,
    val Authkey: String,
    val CanId: String,
    val SrNumber: String,
    val Master:String
)