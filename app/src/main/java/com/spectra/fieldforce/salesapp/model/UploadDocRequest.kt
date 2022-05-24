package com.spectra.fieldforce.salesapp.model

data class UploadDocRequest(
    val Action: String?,
    val Authkey: String?,
    val cafNo: String?,
    val companyId: String?,
    val documentRequired: DocumentRequired?,
    val groupId: String?,
    val password: String?,
    val relationshipId: String?,
    val userName: String?,
    val safNo:String?
)

data class DocumentRequired(
    val addressProof: Boolean?,
    val apnic: Boolean?,
    val cafNo: Boolean?,
    val childIDNew: Boolean?,
    val coiAndPan: Boolean?,
    val documentData: ArrayList<DocumentData>?,
    val industryType: String?,
    val listOfBoardofDirectors: String?,
    val networkDiagram: Boolean?,
    val nonOSPdeclaratrion: Boolean?,
    val panCardNo: Boolean?,
    val parentIDNew: Boolean?,
    val photoIdProofofAuthSign: Boolean?,
    val poLock: String?,
    val poNext: String?,
    val purchaseOrderpo: String?,
    val tanno: Boolean?,
    val tinNo: Boolean?
)

data class DocumentData(
    val fileContent: String?,
    val fileNameWithExtension: String?
)