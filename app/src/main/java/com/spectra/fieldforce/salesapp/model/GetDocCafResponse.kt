package com.spectra.fieldforce.salesapp.model

data class GetDocCafResponse(
    val Response: DocResponse,
    val Status: String,
    val StatusCode: String
)

data class DocResponse(
    val Data: DocData,
    val Message: String,
    val StatusCode: Int,
    val attachDocs: ArrayList<AttachDoc>
)

data class DocData(
    val Account: String,
    val CafId: String,
    val SafNo:String,
    val ChildIDNew: String,
    val Company: String,
    val DocumentId: String,
    val FirmType: String,
    val Group: String,
    val IndustryType: String,
    val OpportunityId: String,
    val ParentIDNew: String,
    val Payment: String,
    val PoLock: String,
    val PoNext: String,
    val Relationship: String,
    val Remark: String,
    val VerificationStatus: String,
    val accordingtoFirmType: AccordingtoFirmType,
    val docAttach: Any
)

data class AttachDoc(
    val Attachment: String,
    val Filename: String
)

data class AccordingtoFirmType(
    val AddressProofCom: String,
    val Apnic: String,
    val CafNo: String,
    val Coi: String,
    val Lco: String,
    val ListDirector: String,
    val Memorendum: String,
    val NtwDiagram: String,
    val OspDeclaration: String,
    val PanCard: String,
    val PhotoId: String,
    val PropAddressProof: String,
    val PurchaseOrder: String,
    val Remark: String,
    val TanNo: String,
    val TinNo: String
)

