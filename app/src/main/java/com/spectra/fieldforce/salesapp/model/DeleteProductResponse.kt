package com.spectra.fieldforce.salesapp.model

data class DeleteProductResponse(
    val Response: DeleteResponse,
    val Status: String,
    val StatusCode: String
)

data class DeleteResponse(
    val Message: String,
    val StatusCode: Int
)