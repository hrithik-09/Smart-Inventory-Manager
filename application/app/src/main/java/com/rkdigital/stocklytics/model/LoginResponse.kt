package com.rkdigital.stocklytics.model

data class LoginResponse(
    val token: String,
    val user: User
)
