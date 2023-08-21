package com.csanad.littlelemon

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.csanad.littlelemon.composable.Home
import com.csanad.littlelemon.composable.Onboarding
import com.csanad.littlelemon.composable.Profile

@Composable
fun Navigation(navController: NavHostController, prefs: SharedPreferences) {
    NavHost(
        navController = navController,
        startDestination = (if (prefs.getBoolean("logged", false)) Home.route else Onboarding.route)
    ) {
        composable(Onboarding.route) {
            Onboarding(navController, prefs)
        }
        composable(Home.route) {
            Home(navController)
        }
        composable(Profile.route) {
            Profile(navController, prefs)
        }
    }
}