package com.adhamamr.passwordy.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.adhamamr.passwordy.ui.auth.LoginScreen
import com.adhamamr.passwordy.ui.auth.RegisterScreen
import com.adhamamr.passwordy.ui.passwords.AddEditPasswordScreen
import com.adhamamr.passwordy.ui.passwords.PasswordDetailScreen
import com.adhamamr.passwordy.ui.passwords.PasswordListScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object PasswordList : Screen("password_list")
    object AddPassword : Screen("add_password")
    object EditPassword : Screen("edit_password/{passwordId}") {
        fun createRoute(passwordId: Long) = "edit_password/$passwordId"
    }
    object PasswordDetail : Screen("password_detail/{passwordId}") {
        fun createRoute(passwordId: Long) = "password_detail/$passwordId"
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.PasswordList.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.PasswordList.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.PasswordList.route) {
            PasswordListScreen(
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onAddPassword = {
                    navController.navigate(Screen.AddPassword.route)
                },
                onPasswordClick = { passwordId ->
                    navController.navigate(Screen.PasswordDetail.createRoute(passwordId))
                }
            )
        }

        composable(Screen.AddPassword.route) {
            AddEditPasswordScreen(
                passwordId = null, // null = add mode
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.EditPassword.route,
            arguments = listOf(
                navArgument("passwordId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val passwordId = backStackEntry.arguments?.getLong("passwordId")
            AddEditPasswordScreen(
                passwordId = passwordId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.PasswordDetail.route,
            arguments = listOf(
                navArgument("passwordId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val passwordId = backStackEntry.arguments?.getLong("passwordId") ?: return@composable
            PasswordDetailScreen(
                passwordId = passwordId,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToEdit = { id ->
                    navController.navigate(Screen.EditPassword.createRoute(id))
                }
            )
        }
    }
}