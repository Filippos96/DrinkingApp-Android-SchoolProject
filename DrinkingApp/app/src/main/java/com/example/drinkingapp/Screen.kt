package com.example.drinkingapp

sealed class Screen(val route: String){
    object GetStarted: Screen(route = "GetStartedScreen")
    object GameMode: Screen(route = "GameModeScreen")
    object Lobby: Screen(route = "LobbyHostScreen")
}
