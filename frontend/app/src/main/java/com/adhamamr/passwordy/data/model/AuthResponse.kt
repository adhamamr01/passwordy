package com.adhamamr.passwordy.data.model

data class AuthResponse(
    val token: String,
    val username: String,
    val email: String,
    val message: String
)