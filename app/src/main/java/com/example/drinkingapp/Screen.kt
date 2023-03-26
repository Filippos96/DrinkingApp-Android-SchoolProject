package com.example.drinkingapp

sealed class Screen(val route: String){
    object GetStarted: Screen(route = "GetStartedScreen")
    object GameMode: Screen(route = "GameModeScreen")
    object LobbyHost: Screen(route = "LobbyHostScreen")
    object LobbyGuest: Screen(route = "LobbyGuestScreen")
    object LobbyScreen: Screen(route = "LobbyScreen")
    object CreatePrompt: Screen(route = "CreatePromptScreen")
    object Waiting: Screen(route = "WaitingScreen")
    object Questions: Screen(route = "QuestionScreen")
    object Result: Screen(route = "ResultScreen")
    object StartScreen: Screen(route = "StartScreen")
    object FinalResults: Screen(route = "FinalResultScreen")

    object VotingScreen: Screen(route = "VotingScreen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
