package com.spectra.fieldforce.utils

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(var context: Context) {

    var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(AppConstants.PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getUserId(): String {
        return sharedPreferences.getString(AppConstants.USER_ID, "5b2beae463b748767db68eb0")!!
    }

    fun setUserId(id: String?) {
        sharedPreferences.edit().putString(AppConstants.USER_ID, id).apply()
    }

}