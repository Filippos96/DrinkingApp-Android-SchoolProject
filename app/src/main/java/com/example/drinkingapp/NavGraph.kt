package com.example.drinkingapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    gameRoomViewModel: GameRoomViewModel
) {

    NavHost(
        navController = navController,
        startDestination = Screen.StartScreen.route
    ) {
        composable(
            route = Screen.StartScreen.route
        ) {
            StartScreen(navController, gameRoomViewModel)
        }

        composable(
            route = Screen.GetStarted.route
        ) {
            GetStartedScreen(navController, gameRoomViewModel)
        }


        composable(
            route = Screen.GameMode.route + "/{username}",
            arguments = listOf(
                navArgument("username") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val username = entry.arguments?.getString("username") ?: ""
            GameModeSelectionScreen(navController, gameRoomViewModel, username)
        }



        composable(
            route = Screen.LobbyHost.route,
        ) {
            LobbyHostScreen(navController, gameRoomViewModel)
        }

        composable(
            route = Screen.LobbyGuest.route,
        ) {
            LobbyGuestScreen(navController, gameRoomViewModel)
        }

        composable(
            route = Screen.LobbyScreen.route,
        ) {
            LobbyScreen(navController, gameRoomViewModel)
        }

        composable(
            route = Screen.CreatePrompt.route,
        ) {
            CreatePrompt(navController, gameRoomViewModel)
        }

        composable(
            route = Screen.Waiting.route,
        ) {
            WaitingScreen(navController)
        }

        composable(
            route = Screen.Questions.route,
        ) {
            QuestionScreen(navController, gameRoomViewModel)
        }

        composable(
            route = Screen.VotingScreen.route
        ) {
            VotingScreen(navController, gameRoomViewModel)
        }

        composable(
            route = Screen.Result.route + "/{prompt}",
            arguments = listOf(
                navArgument("prompt"){
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val prompt = entry.arguments?.getString("prompt") ?: ""
            ResultScreen(navController, gameRoomViewModel, prompt)

        }

        composable(
            route = Screen.FinalResults.route,
        ) {
            FinalResultScreen(navController, gameRoomViewModel)
        }
    }
}
