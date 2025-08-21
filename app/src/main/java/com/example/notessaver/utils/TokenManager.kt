package com.example.notessaver.utils

import android.content.Context
import android.util.Log
import com.example.notessaver.models.UserResponse
import com.example.notessaver.utils.Constants.PREFS_TOKEN_FILE
import com.example.notessaver.utils.Constants.USER_TOKEN
import com.example.notessaver.utils.Constants.UserEmial
import com.example.notessaver.utils.Constants.UserName
import com.example.notessaver.utils.Constants.UserPassword
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Response
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context) {

    private var prefs = context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }


    fun saveUserData(name:String,email:String,password:String){
        val editor=prefs.edit()
        editor.putString(UserName,name)
        editor.putString(UserEmial,email)
        editor.putString(UserPassword,password)
        editor.apply()
    }

    fun getUserData(): UserResponse.User{
        val name = prefs.getString(UserName, null)
        val email = prefs.getString(UserEmial, null)
        val password = prefs.getString(UserPassword, null)
        return  UserResponse.User("","",email!! ,password!!,"",name!!)
    }

    fun clearData() {
        prefs.edit().clear().apply()
    }

    fun getToken():String? {
        return prefs.getString(USER_TOKEN,null)
    }
}