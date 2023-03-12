package com.example.drinkingapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
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
fun LoadingScreen(
    navController: NavController,
    gameRoomViewModel: GameRoomViewModel,
    promptViewModel: PromptViewModel,
    lobbyKey: String
) {

    var prompt by remember { mutableStateOf("") }
    val currentLobby = gameRoomViewModel.lobbies.find { it.lobbyKey == lobbyKey }
    if ( currentLobby != null ) {
        for (player in currentLobby.players) {

        }
        val amountOfPlayers = currentLobby.players.count()


        val database = FirebaseDatabase.getInstance()
        val messagesRef = database.getReference("messages")
        val clickedRef = messagesRef.child("clicked")

        // Initialize clicked count to 0
        clickedRef.setValue(0)

        // Add event listener to track changes in clicked count
        val clickedListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val clickedCount = snapshot.getValue(Int::class.java) ?: 0
                if (clickedCount == currentLobby.players.count()) {
                    navController.navigate("")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        }
        clickedRef.addValueEventListener(clickedListener)
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        NavbarTop(screenName = "Create Prompt", backButton = true, navController = navController)

        Spacer(modifier = Modifier.height(140.dp))

        Text(text = "WAITING")

        Spacer(modifier = Modifier.height(10.dp))

        OrangeButton(
            navController = navController,
            buttonText = "Bump slow players",
            onClick = {

            }
        )
    }
}