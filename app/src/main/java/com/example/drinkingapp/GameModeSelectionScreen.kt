package com.example.drinkingapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.drinkingapp.ui.theme.*

@Composable
fun GameModeSelectionScreen(
    navController: NavController,
    gameRoomViewModel: GameRoomViewModel,
    username: String
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        NavbarTop(screenName = stringResource(R.string.game_mode), backButton = true, navController = navController)

        Spacer(modifier = Modifier.height(140.dp))

        GradientButton(
            text = stringResource(R.string.whos_most_likely),
            textColor = Color.White,
            gradient = Brush.horizontalGradient(
                colors = listOf(
                    greenButtonColor2,
                    greenButtonColor1
                )
            ),
            onClick = {
                gameRoomViewModel.createNewLobby(username, navController)
                navController.navigate(Screen.LobbyScreen.route)
            }
        )


        Spacer(modifier = Modifier.height(20.dp))

        GradientButton(
            text = stringResource(R.string.would_you_rather),
            textColor = Color.White,
            gradient = Brush.horizontalGradient(
                colors = listOf(
                    purpleButtonColor2,
                    purpleButtonColor1
                )
            )
        ) {

        }

        Spacer(modifier = Modifier.height(20.dp))

        GradientButton(
            text = stringResource(R.string.music_quiz),
            textColor = Color.White,
            gradient = Brush.horizontalGradient(
                colors = listOf(
                    redButtonColor2,
                    redButtonColor1
                )
            )
        ) {

        }

        Spacer(modifier = Modifier.height(160.dp))

        Text(text = stringResource(R.string.please_select_game_mode))


    }
}


