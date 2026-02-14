package com.example.mobile.api

import com.example.mobile.model.AuthResponse
import com.example.mobile.model.LoginRequest
import com.example.mobile.model.RegisterRequest
import com.example.mobile.model.UserDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("auth/logout")
    suspend fun logout(): Response<AuthResponse>

    @GET("user/me")
    suspend fun getCurrentUser(): Response<UserDTO>
}
