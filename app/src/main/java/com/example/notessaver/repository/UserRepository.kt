package com.example.notessaver.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notessaver.api.UserApi
import com.example.notessaver.models.UserRequest
import com.example.notessaver.models.UserResponse
import com.example.notessaver.utils.Constants.TAG
import com.example.notessaver.utils.NetworkResult
import org.json.JSONObject
import javax.inject.Inject

class UserRepository @Inject constructor(private val userAPI: UserApi) {

    private val _userResponseLiveData = MutableLiveData<NetworkResult<UserResponse>>()
    val userResponseLiveData: LiveData<NetworkResult<UserResponse>>
        get() = _userResponseLiveData




//    suspend fun registerUser(userRequest: UserRequest) {
//        _userResponseLiveData.postValue(NetworkResult.Loading())
//        val response = userAPI.signup(userRequest)
//
//        if (response.isSuccessful && response.body() != null) {
//            _userResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
//        } else {
//            val errorMsg = try {
//                val errorJson = response.errorBody()?.string()
//                Log.e("API_ERROR", "Raw error: $errorJson")
//                val jsonObject = JSONObject(errorJson)
//                jsonObject.getString("message") // Extracts "User Already Exists"
//            } catch (e: Exception) {
//                Log.e("API_ERROR", "Parsing error: ${e.message}")
//                "Something went wrong"
//            }
//
//            _userResponseLiveData.postValue(NetworkResult.Error(message = errorMsg))
//        }
//    }


    suspend fun registerUser(userRequest: UserRequest) {

        _userResponseLiveData.postValue(NetworkResult.Loading())
        val response = userAPI.signup(userRequest)
        if (response.isSuccessful && response.body() != null) {
            Log.d(TAG,response.body().toString())

            _userResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            Log.d(TAG,response.errorBody().toString())
            _userResponseLiveData.postValue(NetworkResult.Error(message = "Email already exists"))
        } else {

            _userResponseLiveData.postValue(NetworkResult.Error(message = "SOMETHING WENT WRONG"))
        }
    }


    suspend fun loginUser(userRequest: UserRequest) {
        _userResponseLiveData.postValue(NetworkResult.Loading())
        val response = userAPI.signin(userRequest)
        if (response.isSuccessful && response.body() != null) {
            _userResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else {
            val errorMsg = response.errorBody()?.string()
            Log.e(TAG, "Login failed: $errorMsg")
            _userResponseLiveData.postValue(NetworkResult.Error("$errorMsg"))
        }

    }
}