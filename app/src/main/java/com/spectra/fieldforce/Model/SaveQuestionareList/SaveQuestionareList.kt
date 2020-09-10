package com.spectra.fieldforce.Model.SaveQuestionareList

import java.util.ArrayList

data class SaveQuestionareList(
        val Action: String,
        val Answers: List<Answer>,
        val Authkey: String,
        val EmailId: String,
        val Source: String,
        val SrNumber: String,
        val SrType: String
)
/*
 val Answers: List<Answer>,
val Answers: MutableList<ArrayList<String>>,*/
