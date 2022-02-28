package com.spectra.fieldforce.salesapp.model

data class GetRelationShipResponse(
    val Response: RelationshipResponse?,
    val Status: String?,
    val StatusCode: String?
)

data class RelationshipResponse(
    val Data: ArrayList<RelationshipData>?,
    val Message: String?,
    val StatusCode: Int?
)

data class RelationshipData(
    val Company_ID: String?,
    val Company_Name: String?,
    val Relationship_ID: String?,
    val Relationship_Name: String?,
    val City_Name:String?,
    val City_ID:String?,
    val State_Id:String?,
    val State_Name:String?

)