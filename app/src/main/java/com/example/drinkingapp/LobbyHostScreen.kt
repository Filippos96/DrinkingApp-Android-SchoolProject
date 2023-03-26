package com.example.drinkingapp

import android.widget.ImageView
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.Image
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Brush


@Composable
fun LobbyHostScreen(
    navController: NavController,
    gameRoomViewModel: GameRoomViewModel
) {
    LaunchedEffect(gameRoomViewModel.lobbyKey.value) {
        //gameRoomViewModel.generateQRCode(gameRoomViewModel.lobbyKey.value)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NavbarTop(screenName = "Lobby", backButton = false, navController = navController)

        Spacer(modifier = Modifier.height(100.dp))

        Text(
            text = "Waiting...",
            fontSize = 50.sp,
            color = Color.White
        )
        Text(
            text = "for more players to join",
            fontSize = 20.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(100.dp))

        for (player in gameRoomViewModel.lobby.value.players){
            Surface(
                modifier = Modifier
                    .width(300.dp)
                    .height(75.dp)
                    .border(
                        width = 2.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(20.dp)
                    ).background(brush = Brush.horizontalGradient(colors = getColorFromPlayer(player.color)), shape = RoundedCornerShape(20.dp)),
                color = Color.Transparent
            ) {
                Box(
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Text(text = player.username,
                        modifier = Modifier
                            .padding(start = 50.dp),
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        Text(text = "Lobby code is: ${gameRoomViewModel.lobbyKey.value}")

        // Add this line to display the QR code image
        Image(bitmap = gameRoomViewModel.qrCodeBitmap, contentDescription = "QR Code")

        Spacer(modifier = Modifier.height(20.dp))

        OrangeButton(
            navController = navController,
            buttonText = "START GAME",
            onClick = {
                gameRoomViewModel.startGame()
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        OrangeButton(
            navController = navController,
            buttonText = "DISBAND GROUP",
            onClick = {
                gameRoomViewModel.disbandLobby()
                navController.popBackStack()
                navController.popBackStack()
            }
        )
    }
}
