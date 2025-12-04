package com.adhamamr.passwordy.ui.common

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SnackbarController(
    private val snackbarHostState: SnackbarHostState,
    private val scope: CoroutineScope
) {
    fun showSnackbar(
        message: String,
        actionLabel: String? = null,
        duration: SnackbarDuration = SnackbarDuration.Short,
        onActionPerformed: (() -> Unit)? = null
    ) {
        scope.launch {
            val result = snackbarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel,
                duration = duration
            )
            if (result == SnackbarResult.ActionPerformed) {
                onActionPerformed?.invoke()
            }
        }
    }

    fun showSuccessSnackbar(message: String) {
        showSnackbar(message = "✓ $message")
    }

    fun showErrorSnackbar(
        message: String,
        actionLabel: String = "Retry",
        onRetry: (() -> Unit)? = null
    ) {
        showSnackbar(
            message = "✗ $message",
            actionLabel = if (onRetry != null) actionLabel else null,
            duration = SnackbarDuration.Long,
            onActionPerformed = onRetry
        )
    }
}