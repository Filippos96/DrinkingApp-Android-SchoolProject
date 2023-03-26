package com.example.drinkingapp

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.BoxScopeInstance.align
//import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import com.example.drinkingapp.ui.theme.colorP0

@Composable
fun LobbyScreen(
    navController: NavController,
    gameRoomViewModel: GameRoomViewModel
) {
    var screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var screenHeight = LocalConfiguration.current.screenHeightDp.dp
    var screenWidthInt = LocalConfiguration.current.screenWidthDp.toInt()
    var screenHeightInt = LocalConfiguration.current.screenHeightDp.toInt()

    LaunchedEffect(gameRoomViewModel.lobbyKey.value) {
        gameRoomViewModel.generateQRCode(gameRoomViewModel.lobbyKey.value, (screenWidthInt*10), screenWidthInt*10)
    }
    var blurControl by remember {
        mutableStateOf(0)
    }

    Column(
        modifier = Modifier
            .blur(blurControl.dp)
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NavbarTop(screenName = stringResource(R.string.lobby), backButton = false, navController = navController)


        Spacer(modifier = Modifier.height(100.dp))

        Text(
            text = stringResource(R.string.waiting),
            fontSize = 50.sp,
            color = Color.White
        )
        Text(
            text = stringResource(R.string.for_more_players_to_join),
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
                    )
                    .background(
                        brush = Brush.horizontalGradient(colors = getColorFromPlayer(player.color)),
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

        Text(text = stringResource(R.string.lobby_code_is) + "${gameRoomViewModel.lobbyKey.value}")




        Spacer(modifier = Modifier.height(20.dp))

        if (gameRoomViewModel.host.value) {
            OrangeButton(
                navController = navController,
                buttonText = stringResource(R.string.start_game),
                onClick = {
                    gameRoomViewModel.startGame()
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            OrangeButton(
                navController = navController,
                buttonText = stringResource(R.string.disband_group),
                onClick = {
                    gameRoomViewModel.disbandLobby()
                    navController.popBackStack()
                    navController.popBackStack()
                }
            )
        } else {
            OrangeButton(
                navController = navController,
                buttonText = stringResource(R.string.leave),
                onClick = {
                    gameRoomViewModel.removePlayerFromLobby()
                    navController.popBackStack()
                }
            )
        }
    }

    //QR Popup Window



    var popupControl by remember {
        mutableStateOf(false)
    }
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.End

    ) {
        Button(
            onClick =
            {
                if(popupControl) {
                    popupControl = false
                    blurControl = 10
                }
                else{
                    popupControl = true
                    blurControl = 0
                }
            },

            shape = RoundedCornerShape(40),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White
            ))

        {
            Text(text = "QR", style = MaterialTheme.typography.headlineMedium)
        }
    }


    if(popupControl) {

        Box(

            modifier = Modifier
                .padding(30.dp, screenHeight / 2 - screenWidth / 2)
                .border(width = 3.dp, shape = RoundedCornerShape(16.dp), color = Color.Black)
                .background(
                    color = Color(0x77000000),
                    shape = RoundedCornerShape(16.dp)
                )
                .size(screenWidth - screenWidth / 10, screenWidth - screenWidth / 10)


        ) {
            Box(
                modifier = Modifier
                    .padding(30.dp)
            ) {
                Column() {


                    Image(bitmap = gameRoomViewModel.qrCodeBitmap,  contentDescription = "QR Code")

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Spacer(modifier = Modifier.width(85.dp))

                    }
                }
            }
            Button(
                onClick = {
                    popupControl = false
                    blurControl = 0
                },
                shape = RoundedCornerShape(40),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFA500),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .width(100.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text(text = stringResource(R.string.close))
            }
        }
    }
}


