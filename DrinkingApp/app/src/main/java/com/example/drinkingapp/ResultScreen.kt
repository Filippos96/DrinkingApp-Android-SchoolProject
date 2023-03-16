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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

var currentRoundLarge = listOf<Votes>(
    Votes("Johan", 7, listOf(Color(0xFFFF0000), Color(0xFFFF5A5A))),
    Votes("Sven", 8,listOf(Color(0xFF008AFF), Color(0xFF00559C))),
    Votes("Johan", 3,listOf(Color(0xFFFF00AD), Color(0xFFFF82D7))),
    Votes("Sven", 2,listOf(Color(0xFF00FF22), Color(0xFF7AFF8B))),
    Votes("Johan", 0,listOf(Color(0xFFFFC800), Color(0xFFFFDF6C))),
    Votes("Sven", 7,listOf(Color(0xFF0700B1), Color(0xFF9B97FF))),
    Votes("Johan", 0,listOf(Color(0xFF00FFFF), Color(0xFFAFFFFF))),
    Votes("Sven", 2,listOf(Color(0xB3747474), Color(0xFFC7C7C7))),
    Votes("Karin", 1,listOf(Color(0xFF640032), Color(0xFFAA6387))),
    Votes("Johan", 0,listOf(Color(0xB35A4700), Color(0xFF5A4700))),
    Votes("Sven", 2,listOf(Color(0xB3FFA200), Color(0xFFFFA200))),
    Votes("Karin", 1,listOf(Color(0xB3008113), Color(0xFF008113))),
    Votes("Johan", 0,listOf(Color(0xB3C000A8), Color(0xFFC000A8))),
    Votes("Sven", 1,listOf(Color(0xB36F0000), Color(0xFFB60000))),
    Votes("Karin", 1,listOf(Color(0xB3B0FF9A), Color(0xFFB0FF9A)))
)



var currentRound = listOf<Votes>(
    Votes("Johan", 7,listOf(Color(0xFFFF0000), Color(0xFFFF5A5A))),
    Votes("Sven", 5, listOf(Color(0xFF008AFF), Color(0xFF99D0FF))),
    Votes("Johan", 3,listOf(Color(0xFFFF00AD), Color(0xFFFF82D7))),
)

data class Votes(
    var name: String,
    var votes: Int,
    var color: List<Color>
)



var prompt = "to suck a dick!"
var winnerColor = mutableListOf<Color>(Color.Red, Color.White)


@Composable
fun ResultScreen(
    navController: NavController,
    gameRoomViewModel: GameRoomViewModel
){
    var screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var screenHeight = LocalConfiguration.current.screenHeightDp.dp



    // A surface container using the 'background' color from the theme
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = winnerColor,
                    start = Offset.Zero,
                    end = Offset.Infinite
                )
            ),
    ) {
        NavbarTop(screenName = "Results!", backButton = true, navController = navController)
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            displayWinner(currentRoundLarge)
            Row() {
                scoreBoard(list = currentRoundLarge)
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
            data = getVotes(currentRoundLarge), gradientColors = getColors(currentRoundLarge)
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
            buttonText = "Next Question",
            onClick = {
                navController.navigate(Screen.GetStarted.route)
            })
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
                        screenHeight.toPx() - (screenWidth.toPx() - screenWidth.toPx() / 3)
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
                        screenHeight.toPx() - (screenWidth.toPx() - screenWidth.toPx() / 3)
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
fun PieChart(data: List<Float>, colors: List<Color>) {
    val total = data.sum()
    var startAngle = -90f

}

@Composable
fun displayWinner(list: List<Votes>) {
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
                fontSize = 35.sp,
                color = Color.White
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
                color = Color.White
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 75.dp, vertical = 0.dp)
        ) {
            Text(text = "With " + currentLeader[0].votes.toString() + " votes is most likely " + prompt,
                fontSize = 20.sp,
                color = Color.White,
                textAlign = TextAlign.Center)
        }
        winnerColor = currentLeader[0].color as MutableList<Color>

    }

}

