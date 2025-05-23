package com.rkdigital.stocklytics.repository

import com.rkdigital.stocklytics.model.LoginRequest
import com.rkdigital.stocklytics.model.LoginResponse
import com.rkdigital.stocklytics.model.RegisterRequest
import com.rkdigital.stocklytics.serviceApi.RetrofitApiService
import com.rkdigital.stocklytics.serviceApi.RetrofitInstance
import retrofit2.Response

class AuthRepository(private val apiService: RetrofitApiService) {
    suspend fun login(email: String, password: String): Response<LoginResponse> {
        val request = LoginRequest(email, password)
        return RetrofitInstance.apiService.loginUser(request)
    }
    suspend fun validateToken(token: String): Result<Unit> {
        return try {
            val response = apiService.validateToken("Bearer $token")
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Token invalid or expired"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun register(name: String, email: String, password: String, role: String): Result<Unit> {
        return try {
            val response = apiService.registerUser(RegisterRequest(name, email, password, role))
            if (response.isSuccessful && response.body()?.success == true) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.body()?.message ?: "Registration failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}