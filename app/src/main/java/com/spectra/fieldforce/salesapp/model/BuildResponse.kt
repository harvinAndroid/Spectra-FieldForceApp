package com.spectra.fieldforce.salesapp.model

data class BuildResponse(
    val Data: ArrayList<BuildData>?,
    val Message: String?,
    val StatusCode: Int?
)