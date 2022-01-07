package com.spectra.fieldforce.salesapp.model

data class GetGroupResponse(
    val Response: GroupResponse,
    val Status: String,
    val StatusCode: String
)

data class GroupResponse(
    val Data: ArrayList<GroupData>,
    val Message: String,
    val StatusCode: Int
)

data class GroupData(
    val Group_ID: String,
    val Group_Name: String
)