package com.spectra.fieldforce.utils

interface SalesConstant {
    companion object{
        var list_of_salutation = arrayOf("Select Salutation","Mr.", "Mrs.", "Miss")
        var list_of_salutation_id = arrayOf("","1","2","3")
        var list_of_option = arrayOf("Select Option","Yes","No")
        var list_of_mediavalue = arrayOf("","1","2")
        var list_of_channel = arrayOf("Select Channel","Call/SMS-Inbound","Caretel","CM Outbound","Email/Email Campaigns","Inside Sales","Inside Sales-QC","Kaizala","NetOps Channel","Online CAF","Outbound Call",
            "Paid Campaign/Activity","Promotion/BTL/ATL/Events/Sponsorship/Visibility Activity","Self Care Portal","Self Lead","Unify Churned","Web Campaign")
        var list_of_subBusSegment = arrayOf("Select Sub Business Segment","Connectivity Solution", "Data Centre Products", "Internet Service","SDWAN","SIP-Trunk","VOIP")
        var list_of_cust_segment = arrayOf("Select Customer Segment","SDWAN","SMB","Media","LA","SP")
        var list_cust_seg_value = arrayOf("0","111260004","111260000","111260001","111260002","111260003")
        var list_firm_type = arrayOf("Select Firm type","Proprietorship","Partnership","Pvt Ltd","Ltd","Trust","Individual")

        var list_firm_type_value = arrayOf("","1","2","3","4","5","6")
        var list_of_state = arrayOf("Select State","Andhra Pradesh","Bihar","Delhi"
            ,"Gujarat","Haryana","Jammu and Kashmir","Karnataka"
            ,"Kerala", "Madhya Pradesh","Maharashtra","Odisha", "Other*",
            "Punjab","Rajasthan","Tamil Nadu", "Telangana","Uttar Pradesh"
            ,"Uttarakhand","West Bengal")

        var list_of_boolean = arrayOf("Select Option","Yes","No")
        var list_of_booleanvalue = arrayOf("","True","False")

        var list_state_code = arrayOf("","100009","100021","100004", "100015","100008",
            "100011","100007", "100012","100014","100002","100026",
            "100017","100025", "100010", "100003","100023","100006",
            "100016","100013")

        var country_name = arrayOf("India")

        var ext_serv_one = arrayOf("Select Existing Service Provider","Jio", "ACT Fibernet","N.A",
            "Others","Airtel","Aircel","BSNL", "Hathway","MTNL","Nextra",
            "Reliance Communications","Sify","Tata Communications","Tata DOCOMO",
            "Tikona Infinet","Vodafone")

        var ext_serv_one_value = arrayOf("0","111260000",
            "569480014","569480012","569480013","569480000","569480002",
            "569480003","569480004","569480005","569480006","569480007",
            "569480008","569480009","569480010","569480011","569480001")

        var ext_serv = arrayOf("Select Existing Service Provider",
            "Internet", "Data Center Services","VOIP Services","Other Services")
        private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        var list_of_media = arrayOf("Select Media","Fibre","RF")

        var ext_serv_val = arrayOf("0","569480000","569480001","569480002","569480003")

        var disqualifyCode = arrayOf("569480000","569480002","569480003","569480004","569480007","569480008",
            "569480010","569480011","569480012","569480014","569480015","569480016")

        var disqualify = arrayOf("Disqualify","Not Interested","Delay in Response","Pricing issue","Location Is Non-RFS",
            "Existing Customer","Customer Is defaulter","Broadband/Home user","Duplicate Lead","Incomplete information",
            "Wrong Number","Not Contactable","Language Barrier")

          var list_of_redundancy = arrayOf("Select Option","Yes","No")
        var list_of_redundancy_value = arrayOf("","1","0")
        var list_of_media_value = arrayOf("0","1","2")

        var list_of_selfpo = arrayOf("Select Option","Yes","No")
        var list_of_selfpo_value = arrayOf("","1","2")


        var lostCode = arrayOf("569480006","4","569480007","569480008","569480009","569480010","569480011",
            "569480012","569480000","569480001","569480002","569480003","569480004",
            "569480005","5")

        var lost = arrayOf("Lost Opportunity","Customer Found Our Product Costly","cancelled","Location Is Non-RFS",
            "Customer Looking For Promotional Offers","Customer Wanted Information Only",
            "Existing Customer"," Customer Do Not Have Required Docuemnts","Customer Is defaulter",
            "Duplicate Lead","Incorrect Demographic Details","Broadband-Home User",
            "Customer Not Interested","Customer Moved To Other Service Provider","Delay in Response","Out-Sold")

        var GPON="GPON"
        var NON_GPON="NON-GPON"
        var turnOver = arrayOf("Select Option","Diamond (>250Cr)","Emerald (100Cr-250Cr)","Ruby (10Cr-100Cr)","Bronze (1Cr-10Cr)")
        var turnOverVal = arrayOf("0","111260000","111260001","111260002","111260003")

    }
}