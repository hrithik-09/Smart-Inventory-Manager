package com.rkdigital.stocklytics.serviceApi

import com.rkdigital.stocklytics.Model.LoginRequest
import com.rkdigital.stocklytics.Model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitApiService {
    @POST("auth/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>
}