package com.example.drinkingapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CreatePrompt(
    navController: NavController,
    gameRoomViewModel: GameRoomViewModel
) {

    var prompt by remember { mutableStateOf("") }
    var errors by remember { mutableStateOf(listOf<String>()) }
    var error = stringResource(R.string.prompt_length_error)
    fun validatePrompt(): Boolean {
        val newErrors = mutableListOf<String>()

        if (prompt.length !in 4..100) {

            newErrors.add(error)
        }

        errors = newErrors
        return newErrors.isEmpty()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        NavbarTop(screenName = stringResource(R.string.create_prompt), backButton = false, navController = navController)

        Spacer(modifier = Modifier.height(70.dp))

        Image(
            painter = painterResource(id = R.drawable.getstarted),
            contentDescription = "Drink",
            modifier = Modifier.size(160.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = stringResource(R.string.whos_most_likely_to))

        OutlinedTextField(
            value = prompt,
            onValueChange = { prompt = it },
            label = { Text(text = stringResource(R.string.prompt)) },
            placeholder = { Text(text = stringResource(R.string.enter_your_prompt)) }
        )

        Spacer(modifier = Modifier.height(10.dp))

        errors.forEach { error ->
            Text(text = error, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(20.dp))

        OrangeButton(
            navController = navController,
            buttonText = stringResource(R.string.submit),
            onClick = {
                if (validatePrompt()) {
                    gameRoomViewModel.createNewPrompt(prompt, navController)
                }
            }
        )
    }
}