package com.spectra.fieldforce.salesapp.model

data class GetProductItemListRes(
    val Response: ItemResponse?,
    val Status: String?,
    val StatusCode: String?
)

data class ItemResponse(
    val Data: ArrayList<ItemData>?,
    val Message: String?,
    val StatusCode: Int?
)

data class ItemData(
    val Discount: String?,
    val ExtendedAmount: String?,
    val PricePerUnit: String?,
    val ProductId: String?,
    val Quantity: String?,
    val Unit: String?,
    val SiteId:String?
)