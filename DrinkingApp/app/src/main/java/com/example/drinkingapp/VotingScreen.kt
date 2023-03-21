package com.example.drinkingapp

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.collection.mutableVectorOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.drinkingapp.ui.theme.*
import kotlinx.coroutines.delay




var testLobby = listOf(
    Player("Johan", "color0"),
    Player("Adolf", "color1"),
    Player("Katarina", "color2"),
    Player("Sara", "color3"))

var testLobby6 = listOf(
    Player("Johan", "color0"),
    Player("Adolf", "color1"),
    Player("Katarina", "color2"),
    Player("Sara", "color3"),
    Player("Marina", "color4"),
    Player("Max", "color5"))

var testLobby8 = listOf(
    Player("Johan", "color0"),
    Player("Adolf", "color1"),
    Player("Katarina", "color2"),
    Player("Sara", "color3"),
    Player("Marina", "color4"),
    Player("Max", "color5"),
    Player("Karl", "color6"),
    Player("Peter", "color7"))

var testLobby10 = listOf(
    Player("Johan", "color0"),
    Player("Adolf", "color1"),
    Player("Katarina", "color2"),
    Player("Sara", "color3"),
    Player("Marina", "color4"),
    Player("Max", "color5"),
    Player("Karl", "color6"),
    Player("Peter", "color7"),
    Player("Adam", "color8"),
    Player("Svensson", "color9"))

var testLobby12 = listOf(
    Player("Johan", "color0"),
    Player("Adolf", "color1"),
    Player("Katarina", "color2"),
    Player("Sara", "color3"),
    Player("Marina", "color4"),
    Player("Max", "color5"),
    Player("Karl", "color6"),
    Player("Peter", "color7"),
    Player("Adam", "color8"),
    Player("Svensson", "color9"),
    Player("Patrik", "color10"),
    Player("Amanda", "color11"),
    Player("Amanda", "color11"))

var testLobby100 = listOf(
    Player("Johan", "color0"),
    Player("Adolf", "color1"),
    Player("Katarina", "color2"),
    Player("Sara", "color3"),
    Player("Marina", "color4"),
    Player("Max", "color5"),
    Player("Karl", "color6"),
    Player("Peter", "color7"),
    Player("Adam", "color8"),
    Player("Svensson", "color9"),
    Player("Patrik", "color10"),
    Player("Amanda", "color11"),
    Player("Amanda", "color11"),
    Player("Max", "color5"),
    Player("Karl", "color6"),
    Player("Peter", "color7"),
    Player("Adam", "color8"),
    Player("Svensson", "color9"),
    Player("Patrik", "color10"),
    Player("Amanda", "color11"),
    Player("Amanda", "color11"))

var testPrompt = "To have a sex?"

@Composable
fun VotingScreen(
    navController: NavController,
    gameRoomViewModel: GameRoomViewModel
){
    var screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var screenHeight = LocalConfiguration.current.screenHeightDp.dp
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        NavbarTop(screenName = "Vote!", backButton = false, navController = navController)
        Spacer(modifier = Modifier.height(screenHeight/20))
        Text(
            text = "Who's most likely to ",
            fontSize = 30.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 0.dp, horizontal = screenWidth/10))
        Text(
            text = gameRoomViewModel.prompts.last(),
            fontSize = 30.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 0.dp, horizontal = screenWidth/10))



        if(gameRoomViewModel.lobby.value.players.count() <= 4){
            Spacer(modifier = Modifier.height(screenHeight / 2 - 50.dp))
            val lobbyChunked = gameRoomViewModel.lobby.value.players.chunked(2)
            for (chunk in lobbyChunked) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (player in chunk) {
                        playerBoxBig(player, navController, gameRoomViewModel)
                    }
                }
            }
        }

        else if(gameRoomViewModel.lobby.value.players.count() <= 6){
            Spacer(modifier = Modifier.height(screenHeight / 3))
            val lobbyChunked = gameRoomViewModel.lobby.value.players.chunked(2)
            for (chunk in lobbyChunked) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (player in chunk) {
                        playerBoxBig(player, navController, gameRoomViewModel)
                    }
                }
            }
        }

        else if(gameRoomViewModel.lobby.value.players.count() <= 8){
            Spacer(modifier = Modifier.height(screenHeight / 7))
            val lobbyChunked = gameRoomViewModel.lobby.value.players.chunked(2)
            for (chunk in lobbyChunked) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (player in chunk) {
                        playerBoxBig(player, navController, gameRoomViewModel)
                    }
                }
            }
        }

        else if(gameRoomViewModel.lobby.value.players.count() <= 10){
            val lobbyChunked = gameRoomViewModel.lobby.value.players.chunked(2)
            for (chunk in lobbyChunked) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (player in chunk) {
                        playerBoxBig(player, navController, gameRoomViewModel)
                    }
                }
            }
        }


        else if(gameRoomViewModel.lobby.value.players.count() <= 12){
            Spacer(modifier = Modifier.height(screenHeight / 10))
            val lobbyChunked = gameRoomViewModel.lobby.value.players.chunked(3)
            for (chunk in lobbyChunked) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (player in chunk) {
                        playerBoxMedium(player, navController, gameRoomViewModel)
                    }
                }
            }
        }
        else if (gameRoomViewModel.lobby.value.players.count() <= 18){
            Spacer(modifier = Modifier.height(screenHeight / 7,))
            val lobbyChunked = gameRoomViewModel.lobby.value.players.chunked(3)
            for (chunk in lobbyChunked) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (player in chunk) {
                        playerBoxSmall(player, navController, gameRoomViewModel)
                    }
                }
            }
        }
        else{
            val lobbyChunked = gameRoomViewModel.lobby.value.players.chunked(3)
            for (chunk in lobbyChunked) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (player in chunk) {
                        playerBoxSmall(player, navController, gameRoomViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun playerBoxBig(
    player: Player,
    navController: NavController,
    gameRoomViewModel: GameRoomViewModel)
{
    var screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var goldenRatio = 1.618f
    Surface(
        modifier = Modifier
            .padding(5.dp)
            .width(screenWidth / 2 - 35.dp)
            .height((screenWidth / 2 - 35.dp) / goldenRatio)
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(20.dp)
            )
            .background(
                brush = Brush.horizontalGradient(colors = getColorFromPlayer(player.color)),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable {
                gameRoomViewModel.submitAnswer(player.username, navController)
                navController.navigate(Screen.Waiting.route)
            }
        ,
        color = Color.Transparent

    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = player.username,
                fontSize = 20.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun playerBoxMedium(
    player: Player,
    navController: NavController,
    gameRoomViewModel: GameRoomViewModel)
{
    var screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var goldenRatio = 1.618f
    Surface(
        modifier = Modifier
            .padding(5.dp)
            .width(screenWidth / 3 - 20.dp)
            .height(screenWidth / 3 - 20.dp)
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(20.dp)
            )
            .background(
                brush = Brush.horizontalGradient(colors = getColorFromPlayer(player.color)),
                shape = RoundedCornerShape(20.dp)
            )
        ,
        color = Color.Transparent

    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = player.username,
                fontSize = 20.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}
@Composable
fun playerBoxSmall(
    player: Player,
    navController: NavController,
    gameRoomViewModel: GameRoomViewModel)
{
    var screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var goldenRatio = 1.618f
    Surface(
        modifier = Modifier
            .padding(5.dp)
            .width(screenWidth / 3 - 20.dp)
            .height((screenWidth / 3 - 20.dp) / goldenRatio)
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(20.dp)
            )
            .background(
                brush = Brush.horizontalGradient(colors = getColorFromPlayer(player.color)),
                shape = RoundedCornerShape(20.dp)
            )
        ,
        color = Color.Transparent

    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = player.username,
                fontSize = 20.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}