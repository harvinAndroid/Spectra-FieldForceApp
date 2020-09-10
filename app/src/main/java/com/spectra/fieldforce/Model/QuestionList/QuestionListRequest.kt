package com.spectra.fieldforce.Model.QuestionList

data class QuestionListRequest(
    val Action: String,
    val Authkey: String,
    val QuesType: String,
    val SrNumber: String
)