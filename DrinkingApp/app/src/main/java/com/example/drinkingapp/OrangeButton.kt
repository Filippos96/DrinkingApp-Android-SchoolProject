package com.example.drinkingapp

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun OrangeButton(
    navController: NavController,
    buttonText: String,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(40),
        modifier = Modifier
            .size(width = 200.dp, height = 60.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFFA500), // set the background color of the button to bright orange
            contentColor = Color.White // set the text color to white
        )
    ) {
        Text(text = buttonText) // set the text displayed on the button
    }
}