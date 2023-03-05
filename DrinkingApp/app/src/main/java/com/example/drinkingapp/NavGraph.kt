package com.example.drinkingapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    gameRoomViewModel: GameRoomViewModel
){

    NavHost(
        navController = navController,
        startDestination = Screen.StartScreen.route
    ){
        composable(
            route = Screen.StartScreen.route
        ){
            StartScreen(navController, gameRoomViewModel)
        }

        composable(
            route = Screen.GetStarted.route
        ){
            GetStartedScreen(navController, gameRoomViewModel)
        }

        composable(
            route = Screen.GameMode.route + "/{lobbyKey}",
            arguments = listOf(
                navArgument("lobbyKey") {
                    type = NavType.StringType
                }
            )
        ){ entry ->
            entry.arguments?.getString("lobbyKey")
                ?.let { GameModeSelectionScreen(navController, gameRoomViewModel, lobbyKey = it) }
        }

        composable(
            route = Screen.Lobby.route + "/{lobbyKey}",
            arguments = listOf(
                navArgument("lobbyKey") {
                    type = NavType.StringType
                }
            )
        ){ entry ->
            entry.arguments?.getString("lobbyKey")
                ?.let { LobbyHostScreen(navController, gameRoomViewModel, lobbyKey = it) }
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