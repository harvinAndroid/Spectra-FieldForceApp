package com.spectra.fieldforce.model.ItemConsumption

data class DeleteItemConsumption(
    val Action: String,
    val Authkey: String,
    val EquipID: String,
    val ItemID: String,
    val SrNumber: String
)

