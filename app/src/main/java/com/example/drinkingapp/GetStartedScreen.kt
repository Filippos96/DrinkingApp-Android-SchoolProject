package com.example.drinkingapp

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.zxing.integration.android.IntentIntegrator

@Composable
fun GetStartedScreen(
    navController: NavController,
    gameRoomViewModel: GameRoomViewModel
) {
    // For qr-code
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var lobbyKey by remember { mutableStateOf("") }
    var scannedLobbyKey by remember { mutableStateOf("") }
    var errors by remember { mutableStateOf(listOf<String>()) }

    fun validateUsername(): Boolean {
        val newErrors = mutableListOf<String>()

        if (username.length !in 3..10) {
            newErrors.add("Username must be between 3 and 10 characters.")
        }

        errors = newErrors
        return newErrors.isEmpty()
    }

    // ...
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        NavbarTop(screenName = "Get Started!", backButton = true, navController = navController)

        Spacer(modifier = Modifier.height(70.dp))

        Image(painter = painterResource(id = R.drawable.getstarted),
            contentDescription = "emoji",
            modifier = Modifier
                .size(160.dp)
        )
        Spacer(modifier = Modifier.height(70.dp))

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

        Spacer(modifier = Modifier.height(10.dp))

        errors.forEach { error ->
            Text(text = error, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (validateUsername()) {
                    val integrator = IntentIntegrator(context as Activity)
                    integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                    integrator.setPrompt("Scan a QR code")
                    integrator.setBeepEnabled(false)
                    integrator.initiateScan()
                }
            }
        ) {
            Text(text = "Scan QR Code")
        }

        OrangeButton(
            navController = navController,
            buttonText = "CREATE",
            onClick = {
                if (validateUsername()) {
                    navController.navigate(route = Screen.GameMode.withArgs(username))
                }
            }
        )
        OrangeButton(
            navController = navController,
            buttonText = "JOIN",
            onClick = {
                if (validateUsername()) {
                    val finalLobbyKey = if (scannedLobbyKey.isNotEmpty()) scannedLobbyKey else lobbyKey
                    gameRoomViewModel.joinLobby(username, finalLobbyKey, navController)
                    navController.navigate(route = Screen.LobbyGuest.route)
                }
            }
        )
    }
}