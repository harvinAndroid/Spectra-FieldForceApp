package com.spectra.fieldforce.salesapp.model


data class LeadData(
        val BusinessSegment: String,
        val CAFId: String,
        val CompanyId: String,
        val CompanyNameCompany:String,
        val ContactPersonName: String,
        val CreationDate: String,
        val EMS: String,
        val EmailId: String,
        val FirstName: String,
        val GUIDLEAD: String,
        val GroupId: String,
        val GroupName:String,
        val LastName: String,
        val LeadChannel: String,
        val LeadId: String,
        val LeadSource: String,
        val LeadTopic: String,
        val MobileNo: String,
        val ProductName: String,
        val RelationshipId: String,
        val Remark: String,
        val Leadstatus:String,
        val Status:String,
        val FLRstatus:String,
        val Estimatedclosure:String,
        val SalutationId: String,
        val SpecifyLeadSource: String,
        val SubBusinessSegment: String,
        val CustomerSegment:String,
        val companyDetail: Company_Detail,
        val companyName: String,
        val contactAddress: UpdateContact_Address,
        val installationAddress: Installation_Address,
        val otherDetail: Other_Detail,
        val VerticalID:String,
        val sdwan:SDWANRes


)
