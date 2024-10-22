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

data class Votes(
    var name: String,
    var votes: Int,
    var color: List<Color>
)

var winnerColor = mutableListOf<Color>(Color.Red, Color.White)

@Composable
fun ResultScreen(
    navController: NavController,
    gameRoomViewModel: GameRoomViewModel,
    prompt: String
){
    var round = mutableListOf<Votes>()
    var votes by remember { mutableStateOf(0) }
    for (player in gameRoomViewModel.lobby.value.players) {
        for (vote in gameRoomViewModel.currentAnswers) {
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

        NavbarTop(screenName = stringResource(R.string.results), backButton = false, navController = navController)
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            displayWinner(round, prompt)
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
        LaunchedEffect(Unit) {
            //gameRoomViewModel.popLastPrompt()
            delay(5000)
            // if gameRoomViewModel.prompts is empty: navigate to BIG result screen
            if (gameRoomViewModel.prompts.isEmpty()) {
                navController.navigate(Screen.FinalResults.route)
            } else {
                navController.navigate(Screen.VotingScreen.route)
                gameRoomViewModel.clearCurrentAnswers()
            }
        }
    }
}





fun getVotes(list: List<Votes>) : MutableList<Float>{
    var listOfVotes = mutableListOf<Float>()
    for (item in list){
        listOfVotes.add(item.votes.toFloat())
    }
    return listOfVotes
}

fun getColors(list: List<Votes>) : List<List<Color>>{
    var listOfColors = mutableListOf<List<Color>>()
    for (item in list)
        listOfColors.add(item.color)
    return  listOfColors
}






@Composable
fun PieChart2(data: List<Float>, gradientColors: List<List<Color>>) {
    val total = data.sum()
    var startAngle = 0f
    var screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var screenHeight = LocalConfiguration.current.screenHeightDp.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val size = Size(
                screenWidth.toPx() - screenWidth.toPx() / 3,
                screenWidth.toPx() - screenWidth.toPx() / 3
            )
            var currentAngle = 0f
            data.forEachIndexed { index, value ->
                val sweepAngle = value / total * 360f
                val gradient = Brush.radialGradient(
                    colors = gradientColors[index],
                    center = Offset(
                        x = this.center.x,
                        screenHeight.toPx() - screenWidth.toPx() / 3
                    ),
                    radius = size.width / 2f,
                )

                drawArc(
                    brush = gradient,
                    startAngle = startAngle + currentAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                    topLeft = Offset(
                        (screenWidth.toPx() / 3) / 2,
                        screenHeight.toPx() - (screenWidth.toPx() - screenWidth.toPx() / 4.5f)
                    ),
                    size = size,
                    style = Fill,
                )
                drawArc(
                    color = Color.White,
                    startAngle = startAngle + currentAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                    topLeft = Offset(
                        (screenWidth.toPx() / 3) / 2,
                        screenHeight.toPx() - (screenWidth.toPx() - screenWidth.toPx() / 4.5f)
                    ),
                    size = size,
                    style = Stroke(2.dp.toPx())
                )

                currentAngle += sweepAngle
            }
        }
    }
}



@Composable
fun scoreBoard(list: List<Votes>){
    val clearedList = mutableListOf<Votes>()
    for (item in list) {
        if (item.votes > 0)
            clearedList.add(item)
    }
    val chunks = clearedList.chunked(3)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        for (chunk in chunks){
            Row(verticalAlignment = Alignment.CenterVertically) {

                for (item in chunk) {
                    Text(text = item.name + ": " + item.votes.toString(),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        color = Color.White)
                }
            }
        }
    }
}



@Composable
fun displayWinner(list: List<Votes>, prompt: String) {
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
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 75.dp, vertical = 30.dp)
        ) {
            Text(text = stringResource(R.string.with) + currentLeader[0].votes.toString() + " " + stringResource(R.string.votes_is_most_likely_to) + prompt,
                fontSize = 20.sp,
                color = Color.White,
                textAlign = TextAlign.Center)
        }
        winnerColor = currentLeader[0].color as MutableList<Color>

    }
}

fun getColorFromPlayer(colorName: String): List<Color>{
    return when (colorName) {
        "color0" -> colorP0
        "color1" -> colorP1
        "color2" -> colorP2
        "color3" -> colorP3
        "color4" -> colorP4
        "color5" -> colorP5
        "color6" -> colorP6
        "color7" -> colorP7
        "color8" -> colorP8
        "color9" -> colorP9
        "color10" -> colorP10
        "color11" -> colorP11
        "color12" -> colorP12
        "color13" -> colorP13
        "color14" -> colorP14
        else -> listOf(Color(0xFFFFE600), Color(0xFF000000))
    }

}