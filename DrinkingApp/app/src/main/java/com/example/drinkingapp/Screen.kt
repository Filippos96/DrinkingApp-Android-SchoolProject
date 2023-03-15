package com.example.drinkingapp

sealed class Screen(val route: String){
    object StartScreen: Screen(route = "StartScreen")
    object GetStarted: Screen(route = "GetStartedScreen")
    object GameMode: Screen(route = "GameModeScreen")
    object Lobby: Screen(route = "LobbyHostScreen")
    object CreatePrompt: Screen(route = "CreatePromptScreen")
    object ResultScreen: Screen(route = "ResultScreen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
