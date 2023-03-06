package com.example.drinkingapp

import android.graphics.Paint.Align
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.drinkingapp.ui.theme.DrinkingAppTheme
import com.google.firebase.annotations.concurrent.Background
import kotlin.math.round

@Composable
fun StartScreen(
    navController: NavController,
    gameRoomViewModel: GameRoomViewModel
) {
    var popupControl by remember {
        mutableStateOf((true))
    }

    Column(
        modifier = Modifier
            .blur(50.dp)//This does not work as intended
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(177.dp))

        Image(
            painter = painterResource(id = R.drawable.getstarted),
            contentDescription = "beer",
            modifier = Modifier.size(160.dp)
        )
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            "Welcome To Drinking Game!",
            color = Color.White,
            style = TextStyle(fontSize = 20.sp)
        )

        Spacer(modifier = Modifier.height(100.dp))

        OrangeButton(
            navController = navController,
            buttonText = "START",
            onClick = {
                navController.navigate(Screen.GetStarted.route)
            })

    }

    if (popupControl) {
        Column() {

            Spacer(modifier = Modifier.height(250.dp))
            Box(

                modifier = Modifier
                    .padding(30.dp)
                    .background(
                        color = Color(0xFFFFA500),
                        shape = RoundedCornerShape(16.dp)
                    )

            ) {
                Box(
                    modifier = Modifier
                        .padding(30.dp)
                ) {
                    Column() {
                        Text(
                            "Please drink responsibly!",
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            "If you continue you agree that you (and only you) are responsible for any consequences that may result from playing this game!",
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(5.dp))


                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Spacer(modifier = Modifier.width(85.dp))
                            Button(
                                onClick = {
                                    popupControl = false
                                },
                                shape = RoundedCornerShape(40),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.Black,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier.width(100.dp)
                            ) {
                                Text(text = "OK")
                            }
                        }
                    }
                }
            }
        }

    }
}