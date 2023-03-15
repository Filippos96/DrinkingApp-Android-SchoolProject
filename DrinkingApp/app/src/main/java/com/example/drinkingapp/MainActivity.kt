package com.example.drinkingapp

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.drinkingapp.ui.theme.DrinkingAppTheme
import com.example.drinkingapp.SetupNavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrinkingAppTheme {

                // Write a message to the database
              //  val database = Firebase.database("https://drinkingapp-e13dc-default-rtdb.europe-west1.firebasedatabase.app/")
              //  val myRef = database.getReference("message")
            //    Log.d(TAG, "Value is: " + myRef)

             //   myRef.setValue("Hello, World!")

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                 //   ChatRoomScreen(viewModel = ChatRoomViewModel())
                 //   GameRoomScreen(viewModel = GameRoomViewModel())
                    AppStarter()
                }

            }
        }
    }
}


@Composable
fun AppStarter() {

    lateinit var navController: NavHostController


    val gameRoomViewModel = GameRoomViewModel()

    // Setting the background
    Surface(
        modifier = Modifier
            .fillMaxSize()
        ,
        color = MaterialTheme.colorScheme.background,
    ) {
        Image(painter = painterResource(id = R.drawable.blackbackground),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        // Here you specify which screen you want to run
        navController = rememberNavController()
        SetupNavGraph(navController = navController, gameRoomViewModel)
    }
}
