package com.example.notessaver.api

import com.example.notessaver.models.UserRequest
import com.example.notessaver.models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {


    @POST("/users/signup")
    suspend fun signup(@Body userRequest: UserRequest): Response<UserResponse>


    @POST("/users/signin")
    suspend fun signin(@Body userRequest: UserRequest): Response<UserResponse>


//
//    @GET("/user")
//    suspend fun getUserDeatils()

}