package com.spectra.fieldforce.model.ItemConsumption

data class GetItemConsumptionRequest(
    val Action: String,
    val Authkey: String,
    val SrNumber:String,
    val CanId:String,
    val Master:String
)