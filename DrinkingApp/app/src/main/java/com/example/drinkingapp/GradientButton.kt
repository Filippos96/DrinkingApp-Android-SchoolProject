package com.example.drinkingapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.drinkingapp.ui.theme.greenButtonColor1
import com.example.drinkingapp.ui.theme.greenButtonColor2

@Composable
fun GradientButton(
    text: String,
    textColor: Color,
    gradient: Brush,
    onClick: () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(30.dp),
        contentPadding = PaddingValues(),
        onClick = { onClick() }
    ) {
        Box(modifier = Modifier.background(gradient)
            .width(340.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = text,
                color = textColor,
                fontSize = 20.sp
            )
        }
    }
}

@Preview
@Composable
fun GradientButtonPreview(){
    GradientButton(
        text = "Button",
        textColor = Color.White,
        gradient = Brush.horizontalGradient(
            colors = listOf(
                greenButtonColor2,
                greenButtonColor1
            )
        )
    ) {
        
    }
}