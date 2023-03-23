package com.example.drinkingapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun WaitingScreen(
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        NavbarTop(screenName = "Waiting", backButton = false, navController = navController)

        Spacer(modifier = Modifier.height(140.dp))

        Text(text = "WAITING")

        Spacer(modifier = Modifier.height(10.dp))

        OrangeButton(
            navController = navController,
            buttonText = "Bump slow players",
            onClick = {

            }
        )
    }
}