package com.example.mobile.model

data class AuthResponse(
    val token: String?,
    val message: String?,
    val user: UserDTO?
)
