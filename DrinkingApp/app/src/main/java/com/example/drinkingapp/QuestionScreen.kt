package com.example.drinkingapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Composable
fun QuestionScreen(
    navController: NavController,
    gameRoomViewModel: GameRoomViewModel,
    lobbyKey: String
) {



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        NavbarTop(screenName = "Who's Most likely to..", backButton = true, navController = navController)

        Spacer(modifier = Modifier.height(140.dp))

        Text(text = gameRoomViewModel.prompts.last())

        Spacer(modifier = Modifier.height(10.dp))

        for ( player in gameRoomViewModel.lobby.value.players ) {
            Button(
                onClick = {
                    gameRoomViewModel.submitAnswer(player, lobbyKey, navController)
                    gameRoomViewModel.addToTotal(player, lobbyKey)
                    navController.navigate(Screen.Waiting.withArgs(lobbyKey))
                }
            ) {
                Text(text = player)
            }
        }
// We save all prompts in an array. When we click on the button we clear the current one being displayed. Then we can
        // keep showing index 0 all the time and get updates. save everyones answer in an array.
        // Maybe two arrays. One for all question answers and one for current question answers
    }
}