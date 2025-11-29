package com.adhamamr.passwordy.data.network

import com.adhamamr.passwordy.data.model.AuthResponse
import com.adhamamr.passwordy.data.model.GeneratedPasswordResponse
import com.adhamamr.passwordy.data.model.LoginRequest
import com.adhamamr.passwordy.data.model.PasswordGenerationRequest
import com.adhamamr.passwordy.data.model.PasswordRequest
import com.adhamamr.passwordy.data.model.PasswordResponse
import com.adhamamr.passwordy.data.model.RegisterRequest
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("api/password/generate")
    suspend fun generatePassword(@Body request: PasswordGenerationRequest): Response<GeneratedPasswordResponse>

    @GET("api/passwords")
    suspend fun getAllPasswords(@Header("Authorization") token: String): Response<List<PasswordResponse>>

    @POST("api/passwords")
    suspend fun savePassword(
        @Header("Authorization") token: String,
        @Body request: PasswordRequest
    ): Response<PasswordResponse>

    @GET("api/passwords/{id}")
    suspend fun getPasswordById(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<PasswordResponse>

    @POST("api/passwords/{id}/decrypt")
    suspend fun decryptPassword(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<Map<String, String>>

    @PUT("api/passwords/{id}")
    suspend fun updatePassword(
        @Header("Authorization") token: String,
        @Path("id") id: Long,
        @Body request: PasswordRequest
    ): Response<PasswordResponse>

    @DELETE("api/passwords/{id}")
    suspend fun deletePassword(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<Unit>
}