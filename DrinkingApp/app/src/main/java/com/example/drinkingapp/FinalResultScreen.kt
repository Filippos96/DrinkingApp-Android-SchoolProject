package com.example.drinkingapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun FinalResultScreen(
    navController: NavController,
    gameRoomViewModel: GameRoomViewModel
) {
    var votes by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        NavbarTop(screenName = "Final Result", backButton = false, navController = navController)

        Spacer(modifier = Modifier.height(140.dp))

        Text(text = "Results")

        Spacer(modifier = Modifier.height(10.dp))

        // This is logic. Should probably be a function in the viewModel or something.
        for (player in gameRoomViewModel.lobby.value.players) {
            for (vote in gameRoomViewModel.allAnswers) {
                if (vote == player.username) {
                    votes += 1
                }
            }
            Text(text = player.username)
            Text(text = votes.toString())
            votes = 0
        }

        OrangeButton(
            navController = navController,
            buttonText = "PLAY AGAIN",
            onClick = {
                if (gameRoomViewModel.host.value) {
                    gameRoomViewModel.disbandLobby()
                }
                navController.popBackStack(Screen.GetStarted.route, inclusive = false)
            }
        )

    }
}