package com.adhamamr.passwordy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.adhamamr.passwordy.ui.navigation.AppNavigation
import com.adhamamr.passwordy.ui.theme.PasswordyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PasswordyTheme {
                AppNavigation()
            }
        }
    }
}