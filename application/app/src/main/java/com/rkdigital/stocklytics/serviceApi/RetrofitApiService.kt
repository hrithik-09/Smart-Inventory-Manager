package com.rkdigital.stocklytics.serviceApi

import com.rkdigital.stocklytics.model.LoginRequest
import com.rkdigital.stocklytics.model.LoginResponse
import com.rkdigital.stocklytics.model.RegisterRequest
import com.rkdigital.stocklytics.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RetrofitApiService {
    @POST("auth/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>
    @POST("auth/signup")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>

    @GET("auth/validate")
    suspend fun validateToken(
        @Header("Authorization") token: String
    ): Response<Void>
}