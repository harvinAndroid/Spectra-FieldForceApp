package com.spectra.fieldforce.Model.AddItemDetails

data class AddConsumptionItem(
        val Action: String,
        val Authkey: String,
        val canId: String,
        val srNumber: String,
        val equipment: ArrayList<Equipment>,
        val item: ArrayList<Item>
)