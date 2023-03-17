package com.example.drinkingapp

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.graphics.Brush
import com.example.drinkingapp.ui.theme.colorP0

@Composable
fun LobbyScreen(
    navController: NavController,
    gameRoomViewModel: GameRoomViewModel
) {

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
                    ),
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

        Spacer(modifier = Modifier.height(20.dp))

        if (gameRoomViewModel.host.value) {
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
        } else {
            OrangeButton(
                navController = navController,
                buttonText = "LEAVE",
                onClick = {
                    gameRoomViewModel.removePlayerFromLobby()
                    navController.popBackStack()
                }
            )
        }


    }
}