package com.example.notessaver.viewmodel

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notessaver.models.UserRequest
import com.example.notessaver.models.UserResponse
import com.example.notessaver.repository.UserRepository
import com.example.notessaver.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class AuthViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val userResponseLiveData: LiveData<NetworkResult<UserResponse>>
        get() = userRepository.userResponseLiveData


    fun registerUser(userRequest: UserRequest) {
        viewModelScope.launch {
            userRepository.registerUser(userRequest)
        }
    }

    fun loginUser(userRequest: UserRequest) {
        viewModelScope.launch {
            userRepository.loginUser(userRequest)
        }
    }

    fun validateCredentials(
        emailAddress: String,
        password: String,
        userName: String,
        isLoggedIn: Boolean
    ): Pair<Boolean, String> {
        return when {
            (!isLoggedIn && TextUtils.isEmpty(userName)) -> {
                Pair(false, "Please provide username")
            }
            TextUtils.isEmpty(emailAddress) || TextUtils.isEmpty(password) -> {
                Pair(false, "Please provide all fields")
            }
            !Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches() -> {
                Pair(false, "Please provide valid email address")
            }
            password.length < 8 -> {
                Pair(false, "Password should be at least 8 characters long")
            }
            else -> Pair(true, "")
        }
    }

}