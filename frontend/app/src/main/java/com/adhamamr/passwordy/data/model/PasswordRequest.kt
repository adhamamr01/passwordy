package com.adhamamr.passwordy.data.model

data class PasswordRequest(
    val label: String,
    val password: String,
    val username: String?,
    val url: String?,
    val notes: String?
)