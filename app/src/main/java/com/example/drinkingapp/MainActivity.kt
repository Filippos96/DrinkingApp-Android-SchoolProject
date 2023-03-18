package com.example.drinkingapp

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.drinkingapp.ui.theme.DrinkingAppTheme
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private lateinit var gameRoomViewModel: GameRoomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gameRoomViewModel = GameRoomViewModel()

        setContent {
            DrinkingAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppStarter()
                }
            }
        }
    }

    @Composable
    fun AppStarter() {
        // Setting the background
        Surface(
            modifier = Modifier
                .fillMaxSize()
            ,
            color = MaterialTheme.colorScheme.background,
        ) {
            Image(
                painter = painterResource(id = R.drawable.blackbackground),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            // Here you specify which screen you want to run
            navController = rememberNavController()
            SetupNavGraph(navController = navController, gameRoomViewModel)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (scanResult != null) {
            if (scanResult.contents == null) {
                Log.d(TAG, "Cancelled scan")
            } else {
                Log.d(TAG, "Scanned: ${scanResult.contents}")
                // Replace with your actual username.
                val username = "YourUsername"
                gameRoomViewModel.joinLobby(username, scanResult.contents, navController)
                navController.navigate("lobbyGuestScreen/${scanResult.contents}") // Replace with your actual destination route.
            }
        }
    }
}