package com.example.drinkingapp

sealed class Screen(val route: String){
    object GetStarted: Screen(route = "GetStartedScreen")
    object GameMode: Screen(route = "GameModeScreen")
    object LobbyHost: Screen(route = "LobbyHostScreen")
    object LobbyGuest: Screen(route = "LobbyGuestScreen")
    object CreatePrompt: Screen(route = "CreatePromptScreen")
    object LoadingScreen: Screen(route = "LoadingScreen")
    object QuestionScreen: Screen(route = "QuestionScreen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
