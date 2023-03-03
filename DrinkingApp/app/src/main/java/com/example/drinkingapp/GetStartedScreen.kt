package com.example.drinkingapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.drinkingapp.R

@Composable
fun GetStartedScreen(
    navController: NavController
) {
    var username  by remember { mutableStateOf("") }
    var lobbyKey by remember { mutableStateOf("") }

    val gameRoomViewModel = GameRoomViewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        NavbarTop(screenName = "Get Started!", backButton = true, navController = navController)

        Spacer(modifier = Modifier.height(140.dp))

        Image(painter = painterResource(id = R.drawable.getstarted),
            contentDescription = "emoji",
            modifier = Modifier
                .size(160.dp)
        )

        Spacer(modifier = Modifier.height(120.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "Username") },
            placeholder = { Text(text = "Enter your username") }

        )
        OutlinedTextField(
            value = lobbyKey,
            onValueChange = { lobbyKey = it },
            label = { Text(text = "Lobby Code") },
            placeholder = { Text(text = "Enter the Lobby Code") }
        )

        Spacer(modifier = Modifier.height(50.dp))

        OrangeButton(
            navController = navController,
            buttonText = "CREATE",
            onClick = {
                gameRoomViewModel.createNewLobby(username)
                navController.navigate(route = Screen.GameMode.route)
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        OrangeButton(
            navController = navController,
            buttonText = "JOIN",
            onClick = {
                gameRoomViewModel.joinLobby(username, lobbyKey)
                navController.navigate(route = Screen.Lobby.route)
            }
        )

        Spacer(modifier = Modifier.height(100.dp))
    }

}
