package com.spectra.fieldforce.salesapp.model

data class GetPdfResponse(
    val Response: PdfResponse,
    val Status: String,
    val StatusCode: String
)

data class PdfResponse(
    val Data: String,
    val Message: String,
    val StatusCode: Int
)