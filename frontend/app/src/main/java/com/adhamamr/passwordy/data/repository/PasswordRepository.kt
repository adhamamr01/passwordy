package com.adhamamr.passwordy.data.repository

import com.adhamamr.passwordy.data.local.TokenManager
import com.adhamamr.passwordy.data.model.*
import com.adhamamr.passwordy.data.network.ApiService
import kotlinx.coroutines.flow.first

class PasswordRepository(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) {

    private suspend fun getAuthToken(): String {
        return tokenManager.token.first() ?: throw Exception("No authentication token found")
    }

    suspend fun getAllPasswords(): List<PasswordResponse> {
        val token = getAuthToken()
        val response = apiService.getAllPasswords("Bearer $token")
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            throw Exception("Failed to fetch passwords: ${response.message()}")
        }
    }

    suspend fun getPasswordById(id: Long): PasswordResponse {
        val token = getAuthToken()
        val response = apiService.getPasswordById("Bearer $token", id)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            throw Exception("Failed to fetch password: ${response.message()}")
        }
    }

    suspend fun savePassword(request: PasswordRequest): PasswordResponse {
        val token = getAuthToken()
        val response = apiService.savePassword("Bearer $token", request)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            throw Exception("Failed to save password: ${response.message()}")
        }
    }

    suspend fun updatePassword(id: Long, request: PasswordRequest): PasswordResponse {
        val token = getAuthToken()
        val response = apiService.updatePassword("Bearer $token", id, request)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            throw Exception("Failed to update password: ${response.message()}")
        }
    }

    suspend fun deletePassword(id: Long) {
        val token = getAuthToken()
        val response = apiService.deletePassword("Bearer $token", id)
        if (!response.isSuccessful) {
            throw Exception("Failed to delete password: ${response.message()}")
        }
    }

    suspend fun decryptPassword(id: Long): String {
        val token = getAuthToken()
        val response = apiService.decryptPassword("Bearer $token", id)
        if (response.isSuccessful && response.body() != null) {
            val body = response.body()!!
            // Backend returns "password" key with the decrypted password
            return body["password"]
                ?: throw Exception("Password key not found in decrypt response")
        } else {
            throw Exception("Failed to decrypt password: ${response.message()}")
        }
    }

    suspend fun generatePassword(request: PasswordGenerationRequest): GeneratedPasswordResponse {
        val response = apiService.generatePassword(request)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            throw Exception("Failed to generate password: ${response.message()}")
        }
    }

    suspend fun generatePin(request: PinGenerationRequest): GeneratedPinResponse {
        val response = apiService.generatePin(request)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            throw Exception("Failed to generate PIN: ${response.message()}")
        }
    }

    suspend fun getCategories(): List<String> {
        val response = apiService.getCategories()
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            throw Exception("Failed to fetch categories: ${response.message()}")
        }
    }
}