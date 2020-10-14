package com.spectra.fieldforce.Model.ItemConsumption

data class Equipment(
    val ConsumptionType: Any,
    val ItemGUID: String,
    val ItemType: String,
    val Itemcode: String,
    val MacId: String,
    val Quantity: String,
    val SerialNumber: String,
    val SubitemGUID: String
)