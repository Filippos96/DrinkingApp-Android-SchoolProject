package com.example.drinkingapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun SetupNavGraph(
    navController: NavHostController
){

    NavHost(
        navController = navController,
        startDestination = Screen.GetStarted.route
    ){
        composable(
            route = Screen.GetStarted.route
        ){
            GetStartedScreen(navController)
        }

        composable(
            route = Screen.GameMode.route
        ){
            GameModeSelectionScreen(navController)
        }

        composable(
            route = Screen.Lobby.route
        ){
            LobbyHostScreen(navController)
        }
    }
    /*
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ){
        composable(
            route = Screen.Home.route
        ){
            HomeScreen(navController)
        }
        composable(
            route = "CreateScreen"
        ){
            CreateScreen(navController)
        }
        composable(
            route = "DetailScreen&userId={userId}",
            arguments = listOf(
                navArgument("userId") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ){ entry ->
            DetailScreen(navController, entry.arguments?.getInt("userId"))
        }
        composable(
            route = "DeleteScreen&userId={userId}",
            arguments = listOf(
                navArgument("userId") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ){ entry ->
            DeleteScreen(navController, entry.arguments?.getInt("userId"))
        }
        composable(
            route = "UpdateScreen&userId={userId}",
            arguments = listOf(
                navArgument("userId") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ){ entry ->
            UpdateScreen(navController, entry.arguments?.getInt("userId"))
        }
    }
     */
}