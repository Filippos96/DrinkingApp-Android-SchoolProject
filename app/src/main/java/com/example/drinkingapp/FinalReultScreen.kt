package com.example.drinkingapp


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.collection.mutableVectorOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.drinkingapp.ui.theme.*
import kotlinx.coroutines.delay



@Composable
fun FinalResultScreen(
    navController: NavController,
    gameRoomViewModel: GameRoomViewModel
){
    var round = mutableListOf<Votes>()
    var votes by remember { mutableStateOf(0) }
    for (player in gameRoomViewModel.lobby.value.players) {
        for (vote in gameRoomViewModel.allAnswers) {
            if (vote == player.username) {
                votes += 1
            }
        }

        round.add(Votes(player.username, votes, getColorFromPlayer(player.color)))

        votes = 0
    }

    var screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var screenHeight = LocalConfiguration.current.screenHeightDp.dp



    // A surface container using the 'background' color from the theme
    Column(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.linearGradient(
                colors = winnerColor,
                start = Offset.Zero,
                end = Offset.Infinite
            )
        ),) {
        Column(modifier = Modifier.fillMaxSize().background(Color(0x4A000000))) {

        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)

    ) {

        NavbarTop(screenName = stringResource(R.string.final_results), backButton = false, navController = navController)
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            displayFinalWinner(round)
            Row() {
                scoreBoard(list = round)
            }
        }
    }
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .offset(y = -100.dp)
    ) {
        PieChart2(
            data = getVotes(round), gradientColors = getColors(round)
        )
    }
    Row(
        Modifier
            .fillMaxSize()
            .offset(y = -screenHeight / 30),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        OrangeButton(
            navController = navController,
            buttonText = stringResource(R.string.play_again),
            onClick = {
                if (gameRoomViewModel.host.value) {
                    gameRoomViewModel.disbandLobby()
                }
                navController.popBackStack(Screen.GetStarted.route, inclusive = false)
            }
        )
    }

}


@Composable
fun displayFinalWinner(list: List<Votes>) {
    var currentLeader = mutableListOf<Votes>( Votes("none", 0, listOf(Color.Black)))

    for (item in list)
    {
        if (currentLeader[0].votes < item.votes)
            currentLeader = mutableListOf<Votes>(item)
        else if (currentLeader[0].votes == item.votes)
            currentLeader.add(item)
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 75.dp, vertical = 30.dp)
        ) {
            Text(text = stringResource(R.string.the_most_likely_person_to_do_anything_is),
                fontSize = 20.sp,
                color = Color.White,
                textAlign = TextAlign.Center)
        }

        if (currentLeader.size == 1)
            Text(text = currentLeader[0].name,
                fontSize = 60.sp,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 75.dp, vertical = 10.dp)
            )
        else {
            var combinedWinnerText = ""
            for ((index, item) in currentLeader.withIndex()) {
                combinedWinnerText += item.name
                if (index != currentLeader.lastIndex) {
                    combinedWinnerText += ", "
                }
            }
            Text(
                text = combinedWinnerText,
                fontSize = 35.sp,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 75.dp, vertical = 10.dp)
            )
            winnerColor = currentLeader[0].color as MutableList<Color>
        }
    }
}
