package com.adhamamr.passwordy.data.model

data class PasswordResponse(
    val id: Long,
    val label: String,
    val value: String,
    val username: String?,
    val url: String?,
    val notes: String?,
    val category: String?,
    val createdAt: String,
    val updatedAt: String
)