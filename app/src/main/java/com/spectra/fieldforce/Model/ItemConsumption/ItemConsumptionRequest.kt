package com.spectra.fieldforce.Model.ItemConsumption

data class ItemConsumptionRequest(
    val Action: String,
    val Authkey: String,
    val CanId: String,
    val SrNumber: String,
    val Master:String
)