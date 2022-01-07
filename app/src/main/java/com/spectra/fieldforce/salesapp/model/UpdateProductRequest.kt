package com.spectra.fieldforce.salesapp.model

data class UpdateProductRequest(
    val Action: String,
    val Authkey: String,
    val discount: String,
    val oppid: String,
    val password: String,
    val priceperunit: String,
    val pricing: String,
    val productid: String,
    val quantity: String,
    val userName: String
)