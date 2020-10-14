package com.spectra.fieldforce.Model.ItemConsumption

data class DeleteItemConsumption(
    val Action: String,
    val Authkey: String,
    val EquipID: String,
    val ItemID: String,
    val SrNumber: String
)

