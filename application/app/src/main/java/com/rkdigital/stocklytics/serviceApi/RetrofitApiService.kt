package com.rkdigital.stocklytics.serviceApi

import com.rkdigital.stocklytics.model.LoginRequest
import com.rkdigital.stocklytics.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RetrofitApiService {
    @POST("auth/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>

    @GET("auth/validate")
    suspend fun validateToken(
        @Header("Authorization") token: String
    ): Response<Void>
}