package com.spectra.fieldforce.utils

interface AppConstants {
    companion object {
        const val PERMISSION_REQUEST_CODE_CALL_PHONE = 1001
        const val PERMISSION_REQUEST_CODE_LOCATION = 1002
        const val PERMISSION_REQUEST_CODE_READ_WRITE = 1000
        const val REQUEST_CODE_READ_WRITE_CAMERA_PERMISSION = 2000
        const val REQUEST_CAMERA_PERMISSION_ONE = 1
        const val REQUEST_CAMERA_PERMISSION_TWO = 2
        const val REQUEST_CAMERA_PERMISSION_THREE = 3
        const val REQUEST_CAMERA_PERMISSION_FOUR = 4
         const val PERMISSION_REQUEST_CODE = 200
        const val REQUEST_CODE_ONE = 101
        const val REQUEST_CODE_TWO = 102
        const val REQUEST_CODE_THREE = 103
        const val REQUEST_CODE_FOUR = 104
        const val SELECT_STATUS = "Select Status"
        const val HOLD = "Hold"
        const val UNHOLD = "Unhold"
        const val RESOLVE = "Resolve"
        const val YES = "Yes"
        const val NO = "No"
        const val TRUE = "True"
        const val FALSE = "False"
        const val EMPTY = "Empty"
        const val SELECT_FIBRE_TYPE = "Select Fibre Type"
        const val SELECT_MANHOLE_TYPE = "Select Manhole Type"
        const val prefrence_key = "SpectraPref"
        const val User_name = "User_name"
        const val User_email = "User_email"
        const val VENDOR_BUCKET = " Master Bucket"
        const val ENGINEER_BUCKET = "My Bucket"
        const val VERSION_NAME = "Build Version"
        const val ITEM_CONSUMPTION = "Item Consumption"
        const val SERVICE_CONSUMPTION = "Service Consumption"
        const val PLEASE_ENTER_USERNAME = "Please Enter UserName"
        const val PLEASE_ENTER_PASSWORD = "Please Enter Password"
        const val SUCCESS = "Success"
        const val LEAD_DETAILS = "Lead Details"
        const val CONTACT_MANAGEMENT = "Contact Management"
        const val OPPURTUNUTY = "Opportunity"
        const val FLR = "FLR"
        const val ALL_LEADS = "Leads"
        const val ALL_CONTACT = "Contacts"
        const val ALL_OPPURTUNITY = "Opportunity"
        const val  DASHBOARD = "My Dashboard"
        const val UPDATE_LEAD = "Update Lead"
        const val UPDATE_CONTACT = "Update Contact"
        const val FEASIBILITY ="Feasbility"
        const val UPDATEPRODUCT = "Update Product"
        const val SITE = "Site"
        const val GENERATEQUOTE ="Generate Quote"
        const val APPROVAL="DOA Approval"
        const val CAF = "CAF"
        const val Caf = "CAF"
        const val BUSINESS="Business"
        const val ADD_WAN ="Add Wan"
        const val ADD_LAN="Add Lan"
        const val CREATE_PRETASK ="Create Pre Sales"
        const val ALL_SAF="SAF"
        const val SAF="SAF"
        const val COUNTRY_CODE="10001"
        const val PREFERENCE_NAME = "preference_name"
        const val USER_ID = "user_id"
        const val DATE_FORMAT="yyyy-MM-dd"
        const val DATE_CURRENT ="yyyy-MM-01"
        const val COUNT_IN_CURRENT_MONTH = "Count In Current Month"
        const val SRCOUNT="SR Count"
        const val SDWAN ="SDWAN"
        const val USERNAME="UserName"
        const val PASSWORD="Password"
        const val UName="UName"
        const val MENU ="Menu"
        const val BIN_MOVEMENT="Bin Movement"
        const val SELECT_OPTION="Select Option"
        const val RTGS="RTGS"
        const val CHEQUE="Cheque"
        const val DEMAND_DRAFT="Demand draft"
        const val CREDIT_CARD="Credit Card"
        const val NEFT="NEFT"
        const val DEBIT_CARD="Debit Card"
        const val EZETAP="Ezetap"
        const val EZETAP_CHEQUE="Ezetap-Cheque"
        const val PREVIOUS_SCREEN="Do you want to go back to the previous screen?"

        val holdReason = arrayOf("Hold Reason","Customer Appointment not received","Under observation by Customer",
                "Massoutage","Customer not Contactable","Customer Response Awaited","Under observation by Spectra",
                "Permission Issue","Force majeure - COVID","Customer House Locked","Force majeure - Flood",
                "Notice Period Extended on Customer Request")

        val holdReasonValue = arrayOf("0","111260000","111260001","111260002","111260003","111260004",
            "111260005","111260006","111260007","111260008","111260009","111260010")

        val contact = arrayOf("SELECT_STATUS","YES","NO")




    }
}