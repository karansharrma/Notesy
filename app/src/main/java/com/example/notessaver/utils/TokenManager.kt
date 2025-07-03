package com.example.notessaver.utils

import android.content.Context
import android.util.Log
import com.example.notessaver.utils.Constants.PREFS_TOKEN_FILE
import com.example.notessaver.utils.Constants.USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context) {

    private var prefs = context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        Log.d("NOTEDELETE", token.toString())
        editor.apply()
    }


    fun getToken():String? {
        return prefs.getString(USER_TOKEN,null)
        Log.d("NOTEDELETE", USER_TOKEN.toString())

    }
}