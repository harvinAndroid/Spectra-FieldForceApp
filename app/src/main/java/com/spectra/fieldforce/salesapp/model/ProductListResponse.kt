package com.spectra.fieldforce.salesapp.model

data class ProductListResponse(
    val Response: ProductRes,
    val Status: String,
    val StatusCode: String
)

data class ProductRes(
    val Data: ArrayList<ProData>,
    val Message: String,
    val StatusCode: Int
)

data class ProData(
    val ProductId: String
)