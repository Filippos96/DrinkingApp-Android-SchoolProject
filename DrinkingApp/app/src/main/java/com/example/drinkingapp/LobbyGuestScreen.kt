package com.example.drinkingapp

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun LobbyGuestScreen(
    navController: NavController,
    gameRoomViewModel: GameRoomViewModel,
    lobbyKey: String,
    username: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NavbarTop(screenName = "Lobby", backButton = true, navController = navController)

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

    }
}

@Preview
@Composable
fun LobbyGuestPreview() {

    Surface(
        modifier = Modifier
            .fillMaxSize()
        ,
        color = MaterialTheme.colorScheme.background,
    ) {
        Image(painter = painterResource(id = R.drawable.blackbackground),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //NavbarTop(screenName = "Lobby", backButton = true, navController = navController)

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

            Surface(
                modifier = Modifier
                    .width(300.dp)
                    .height(75.dp)
                    .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(20.dp)),
                color = Color.Transparent

            ) {
                Box(
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(text = "Username 1",
                        modifier = Modifier
                            .padding(start = 50.dp),
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Surface(
                modifier = Modifier
                    .width(300.dp)
                    .height(75.dp)
                    .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(20.dp)),
                color = Color.Transparent

            ) {
                Box(
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(text = "Username 1",
                        modifier = Modifier
                            .padding(start = 50.dp),
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Surface(
                modifier = Modifier
                    .width(300.dp)
                    .height(75.dp)
                    .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(20.dp)),
                color = Color.Transparent

            ) {
                Box(
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(text = "Username 1",
                        modifier = Modifier
                            .padding(start = 50.dp),
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))


            Button(
                onClick = { /* do something */ },
                shape = RoundedCornerShape(40),
                modifier = Modifier
                    .size(width = 200.dp, height = 60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFA500), // set the background color of the button to bright orange
                    contentColor = Color.White // set the text color to white
                )
            ) {
                Text(text = "CREATE") // set the text displayed on the button
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { /* do something */ },
                shape = RoundedCornerShape(40),
                modifier = Modifier
                    .size(width = 200.dp, height = 60.dp), // set the size of the button
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFA500), // set the background color of the button to bright orange
                    contentColor = Color.White // set the text color to white
                )
            ) {
                Text(text = "JOIN") // set the text displayed on the button
            }

        }
    }
}