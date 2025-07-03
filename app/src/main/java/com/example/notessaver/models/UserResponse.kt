package com.example.notessaver.models

data class UserResponse(
    val token: String,
    val user: User
)

{
    data class User(
        val _id: String,
        val createdAt: String,
        val email: String,
        val password: String,
        val updatedAt: String,
        val username: String
    )
}