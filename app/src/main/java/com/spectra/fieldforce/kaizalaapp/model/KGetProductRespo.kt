package com.spectra.fieldforce.kaizalaapp.model

data class KGetProductRespo(
    val Response: MutableList<KProductResponse>,
    val Status: String,
    val StatusCode: Int
)

data class KProductResponse(
    val BusinessSegment: String,
    val ProductDescription: String,
    val ProductID: String,
    val ProductName: String,
    val ProductNumber: String,
    val ProductSegment: String,
    val ProductStructure: String,
    val StateCode: String
)