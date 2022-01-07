package com.spectra.fieldforce.salesapp.model

data class GetOppurtunityProductResponse(
    val Response: ProductResponse,
    val Status: String,
    val StatusCode: String
)

data class ProductResponse(
    val Data: List<ProductData>,
    val Message: String,
    val StatusCode: Int
)

data class ProductData(
    val ProductCount: Any,
    val ProductId: String
)