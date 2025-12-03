package com.adhamamr.passwordy.ui.passwords

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adhamamr.passwordy.data.repository.PasswordRepository

class PasswordViewModelFactory(
    private val repository: PasswordRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PasswordViewModel::class.java)) {
            return PasswordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}