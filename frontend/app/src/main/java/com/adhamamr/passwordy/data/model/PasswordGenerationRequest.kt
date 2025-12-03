package com.adhamamr.passwordy.data.model

data class PasswordGenerationRequest(
    val length: Int = 16,
    val includeSymbols: Boolean = true
)