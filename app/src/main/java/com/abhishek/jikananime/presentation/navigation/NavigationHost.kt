package com.abhishek.jikananime.presentation.navigation

import android.util.Log
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.abhishek.jikananime.core.tempTag
import com.abhishek.jikananime.presentation.screens.animedetails.AnimeDetailRoot
import com.abhishek.jikananime.presentation.screens.home.HomeScreenRoot

@Composable
fun NavigationHost(navController: NavHostController, startDestination: String) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        // home screen
        composable(Screen.Home.route) {
            HomeScreenRoot { animeId ->
                Log.i(tempTag(), "navigating to anime $animeId")
                navController.navigate(Screen.AnimeDetails.createRoute(animeId))
            }
        }

        // anime details screen
        composable(
            Screen.AnimeDetails.route,
            arguments = listOf(navArgument(Screen.AnimeDetails.ARG_ANIME_ID) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            AnimeDetailRoot()
        }
    }
}