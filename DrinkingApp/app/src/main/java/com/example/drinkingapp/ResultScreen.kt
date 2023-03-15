package com.example.drinkingapp

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun ResultScreen(
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

        NavbarTop(screenName = "Result", backButton = false, navController = navController)

        Spacer(modifier = Modifier.height(140.dp))

        Text(text = "Result")

        Spacer(modifier = Modifier.height(10.dp))


        // This is logic. Should probably be a function in the viewModel or something.
        for (player in gameRoomViewModel.lobby.value.players) {
            for (vote in gameRoomViewModel.currentAnswers) {
                if (vote == player) {
                    votes += 1
                }
            }
            Text(text = player)
            Text(text = votes.toString())
            votes = 0
        }

        LaunchedEffect(Unit) {
            gameRoomViewModel.popLastPrompt()
            delay(5000)
            // if gameRoomViewModel.prompts is empty: navigate to BIG result screen
            Log.d("Hej", "There is: " + gameRoomViewModel.prompts.size.toString() + " i result screen")
            if (gameRoomViewModel.prompts.isEmpty()) {
                Log.d("Hej", "is empty")
                navController.navigate(Screen.FinalResults.route)
            } else {
                Log.d("Hej", "is not empty")
                navController.navigate(Screen.Questions.route)
                gameRoomViewModel.clearCurrentAnswers()
            }
        }
    }
}