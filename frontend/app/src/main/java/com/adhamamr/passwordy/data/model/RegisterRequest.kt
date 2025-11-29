package com.adhamamr.passwordy.data.model

data class RegisterRequest(
    val username: String,
    val email: String,
    val masterPassword: String
)