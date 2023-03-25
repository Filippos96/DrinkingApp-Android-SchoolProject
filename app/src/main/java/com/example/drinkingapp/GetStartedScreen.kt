package com.example.drinkingapp

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.zxing.integration.android.IntentIntegrator
import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.DisposableEffect

@Composable
fun GetStartedScreen(
    navController: NavController,
    gameRoomViewModel: GameRoomViewModel,
) {

    val context = LocalContext.current
    var username  by remember { mutableStateOf("") }
    var lobbyKey by remember { mutableStateOf("") }
    var scannedLobbyKey by remember { mutableStateOf("") }
    var errors by remember { mutableStateOf(listOf<String>()) }

    fun validUsername(context: Context): Boolean {
        val newErrors = mutableListOf<String>()

        if (username.length !in 3..10) {
            newErrors.add(context.getString(R.string.username_length_error))
        }

        errors = newErrors
        return newErrors.isEmpty()
    }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
        val scanResult = IntentIntegrator.parseActivityResult(result.resultCode, result.data)
        if (scanResult != null && scanResult.contents != null) {
            scannedLobbyKey = scanResult.contents
            gameRoomViewModel.validateLobbyKey(scannedLobbyKey, onError = { error ->
                errors = listOf(error)
            }, onSuccess = { isValid ->
                if (isValid) {
                    gameRoomViewModel.joinLobbyAndNavigate(username, scannedLobbyKey, navController)
                    navController.navigate(route = Screen.LobbyGuest.route)
                }
            })
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        NavbarTop(screenName = stringResource(id = R.string.get_started), backButton = true, navController = navController)

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
            label = { Text(text = stringResource(id = R.string.username)) },
            placeholder = { Text(text = stringResource(id = R.string.enter_your_username)) }

        )
        OutlinedTextField(
            value = lobbyKey,
            onValueChange = { lobbyKey = it },
            label = { Text(text = stringResource(id = R.string.lobby_code)) },
            placeholder = { Text(text = stringResource(id = R.string.enter_the_lobby_code)) }
        )

        Spacer(modifier = Modifier.height(10.dp))

        errors.forEach { error ->
            Text(text = error, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (validUsername(context)) {
                    val integrator = IntentIntegrator(context as Activity)
                    integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                    integrator.setPrompt("Scan a QR code")
                    integrator.setBeepEnabled(false)
                    integrator.setOrientationLocked(true)
                    launcher.launch(integrator.createScanIntent()) // Replace initiateScan() with this line
                }
            }
        ) {
            Text(text = "Scan QR Code")
        }

        OrangeButton(
            navController = navController,
            buttonText = stringResource(id = R.string.create),
            onClick = {
                if (validUsername(context)) {
                    navController.navigate(route = Screen.GameMode.withArgs(username))
                }
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        OrangeButton(
            navController = navController,
            buttonText = stringResource(id = R.string.join),
            onClick = {
                if (validUsername(context)) {
                    if (lobbyKey.isEmpty()) {
                        errors = listOf(context.getString(R.string.invalid_lobby_key_error))
                    } else {
                        gameRoomViewModel.validateLobbyKey(lobbyKey, onError = { error ->
                            errors = listOf(error)
                        }, onSuccess = { isValid ->
                            if (isValid) {
                                val finalLobbyKey = if (scannedLobbyKey.isNotEmpty()) scannedLobbyKey else lobbyKey
                                gameRoomViewModel.joinLobby(username, finalLobbyKey, navController)
                                navController.navigate(route = Screen.LobbyGuest.route)
                            }
                        })
                    }
                }
            }
        )
    }
}
