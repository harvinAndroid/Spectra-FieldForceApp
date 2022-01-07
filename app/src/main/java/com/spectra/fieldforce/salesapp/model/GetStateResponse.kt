package com.spectra.fieldforce.salesapp.model

data class GetStateResponse(
    val Response: StateResponse,
    val Status: String,
    val StatusCode: String
)

data class StateResponse(
    val Data: List<StateData>,
    val Message: String,
    val StatusCode: Int
)

data class StateData(
    val StateCode: String,
    val StateName: String
)