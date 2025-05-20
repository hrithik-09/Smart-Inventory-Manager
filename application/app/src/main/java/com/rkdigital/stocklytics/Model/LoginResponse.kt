package com.rkdigital.stocklytics.Model

data class LoginResponse(
    val token: String,
    val userId: Int,
    val name: String,
    val role: String
)
