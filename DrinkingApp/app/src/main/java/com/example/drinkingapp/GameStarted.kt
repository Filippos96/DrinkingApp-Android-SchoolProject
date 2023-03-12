package com.example.drinkingapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class GameStarted : ViewModel() {
    val gameHasStarted = mutableStateOf(false)

    
}