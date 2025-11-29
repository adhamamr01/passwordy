package com.adhamamr.passwordy.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.adhamamr.passwordy.data.local.TokenManager
import com.adhamamr.passwordy.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AuthRepository()
    private val tokenManager = TokenManager(application)

    // UI State
    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Initial)
    val uiState: StateFlow<AuthUiState> = _uiState

    fun register(username: String, email: String, masterPassword: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading

            try {
                val response = repository.register(username, email, masterPassword)

                if (response.isSuccessful && response.body() != null) {
                    val authResponse = response.body()!!
                    tokenManager.saveToken(authResponse.token)
                    tokenManager.saveUsername(authResponse.username)
                    _uiState.value = AuthUiState.Success(authResponse.message)
                } else {
                    _uiState.value = AuthUiState.Error(response.message() ?: "Registration failed")
                }
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Network error")
            }
        }
    }

    fun login(username: String, masterPassword: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading

            try {
                val response = repository.login(username, masterPassword)

                if (response.isSuccessful && response.body() != null) {
                    val authResponse = response.body()!!
                    tokenManager.saveToken(authResponse.token)
                    tokenManager.saveUsername(authResponse.username)
                    _uiState.value = AuthUiState.Success(authResponse.message)
                } else {
                    _uiState.value = AuthUiState.Error(response.message() ?: "Login failed")
                }
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Network error")
            }
        }
    }

    fun resetState() {
        _uiState.value = AuthUiState.Initial
    }
}

// UI State sealed class
sealed class AuthUiState {
    object Initial : AuthUiState()
    object Loading : AuthUiState()
    data class Success(val message: String) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}