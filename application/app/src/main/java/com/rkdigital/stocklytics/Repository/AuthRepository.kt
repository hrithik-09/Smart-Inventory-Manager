package com.rkdigital.stocklytics.Repository

import com.rkdigital.stocklytics.Model.LoginRequest
import com.rkdigital.stocklytics.Model.LoginResponse
import com.rkdigital.stocklytics.serviceApi.RetrofitInstance
import retrofit2.Response

class AuthRepository {
    suspend fun login(email: String, password: String): Response<LoginResponse> {
        val request = LoginRequest(email, password)
        return RetrofitInstance.apiService.loginUser(request)
    }
}