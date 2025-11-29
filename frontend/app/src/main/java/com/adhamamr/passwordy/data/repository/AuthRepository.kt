package com.adhamamr.passwordy.data.repository

import com.adhamamr.passwordy.data.model.AuthResponse
import com.adhamamr.passwordy.data.model.LoginRequest
import com.adhamamr.passwordy.data.model.RegisterRequest
import com.adhamamr.passwordy.data.network.RetrofitInstance
import retrofit2.Response

class AuthRepository {

    private val api = RetrofitInstance.api

    suspend fun register(username: String, email: String, masterPassword: String): Response<AuthResponse> {
        val request = RegisterRequest(username, email, masterPassword)
        return api.register(request)
    }

    suspend fun login(username: String, masterPassword: String): Response<AuthResponse> {
        val request = LoginRequest(username, masterPassword)
        return api.login(request)
    }
}