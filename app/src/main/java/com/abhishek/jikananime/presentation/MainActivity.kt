package com.abhishek.jikananime.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.abhishek.jikananime.presentation.navigation.NavigationHost
import com.abhishek.jikananime.presentation.navigation.Screen
import com.abhishek.jikananime.presentation.screens.home.HomeScreenRoot
import com.abhishek.jikananime.presentation.theme.JikanAnimeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JikanAnimeTheme {
                val navController = rememberNavController()

                NavigationHost(
                    navController = navController,
                    startDestination = Screen.Home.route
                )
            }
        }
    }
}

