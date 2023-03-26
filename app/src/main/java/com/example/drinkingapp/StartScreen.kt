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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
        mutableStateOf(true)
    }
    var blurControl by remember {
        mutableStateOf(10)
    }

    Column(
        modifier = Modifier
            .blur(blurControl.dp)//This does not work as intended
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
            stringResource(R.string.welcome_message),
            color = Color.White,
            style = TextStyle(fontSize = 20.sp)
        )

        Spacer(modifier = Modifier.height(100.dp))

        OrangeButton(
            navController = navController,
            buttonText = stringResource(R.string.start),
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
                    .border(width = 3.dp, shape = RoundedCornerShape(16.dp), color = Color.Black)
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
                            stringResource(R.string.drink_responsibly),
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            stringResource(R.string.responsibility_warning),
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(5.dp))


                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Spacer(modifier = Modifier.width(85.dp))
                            Button(
                                onClick = {
                                    popupControl = false
                                    blurControl = 0
                                },
                                shape = RoundedCornerShape(40),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Black,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier.width(100.dp)
                            ) {
                                Text(stringResource(R.string.ok), color = Color.White)

                            }
                        }
                    }
                }
            }
        }

    }
}