package com.example.drinkingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.drinkingapp.ui.theme.DrinkingAppTheme

@Composable
fun StartScreen(
    navController: NavController,
    gameRoomViewModel: GameRoomViewModel
) {



}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DrinkingAppTheme {
    }
}