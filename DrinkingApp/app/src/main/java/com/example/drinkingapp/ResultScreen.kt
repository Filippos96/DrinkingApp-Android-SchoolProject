package com.example.drinkingapp


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

var currentRoundLarge = listOf<Votes>(
    Votes("Johan", 7),
    Votes("Sven", 7),
    Votes("Johan", 3),
    Votes("Sven", 2),
    Votes("Johan", 0),
    Votes("Sven", 7),
    Votes("Johan", 0),
    Votes("Sven", 2),
    Votes("Karin", 1),
    Votes("Johan", 0),
    Votes("Sven", 2),
    Votes("Karin", 1),
    Votes("Johan", 0),
    Votes("Sven", 2),
    Votes("Karin", 1)
)

var currentRound = listOf<Votes>(
    Votes("Johan", 7),
    Votes("Sven", 5),
    Votes("Johan", 3),
)

data class Votes(
    var name: String,
    var votes: Int,
)



var prompt = "to suck a dick!"



@Composable
fun ResultScreen(
    navController: NavController,
    gameRoomViewModel: GameRoomViewModel
){
    var screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var screenHeight = LocalConfiguration.current.screenHeightDp.dp
    // A surface container using the 'background' color from the theme
    Column(
        modifier = Modifier.fillMaxSize().background(color = Color.Red),
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
            data = getVotes(currentRoundLarge), gradientColors = listOf(
                listOf(Color(0xFFFF0000), Color(0xFFFF5A5A)),
                listOf(Color(0xFF008AFF), Color(0xFF99D0FF)),
                listOf(Color(0xFFFF00AD), Color(0xFFFF82D7)),
                listOf(Color(0xFF00FF22), Color(0xFF7AFF8B)),
                listOf(Color(0xFFFFC800), Color(0xFFFFDF6C)),
                listOf(Color(0xFF0700B1), Color(0xFF9B97FF)),
                listOf(Color(0xFF00FFFF), Color(0xFFAFFFFF)),
                listOf(Color(0xB3747474), Color(0xFFC7C7C7)),
                listOf(Color(0xFF640032), Color(0xFFAA6387)),
                listOf(Color(0xB35A4700), Color(0xFF5A4700)),
                listOf(Color(0xB3FFA200), Color(0xFFFFA200)),
                listOf(Color(0xB3008113), Color(0xFF008113)),
                listOf(Color(0xB3C000A8), Color(0xFFC000A8)),
                listOf(Color(0xB36F0000), Color(0xFF6F0000)),
                listOf(Color(0xB3B0FF9A), Color(0xFFB0FF9A))
            )
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
            buttonText = "Continue",
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



@Composable
fun PieChart2(data: List<Float>, gradientColors: List<List<Color>>) {
    val total = data.sum()
    var startAngle = 0f
    var screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var screenHeight = LocalConfiguration.current.screenHeightDp.dp

    Box(modifier = Modifier
        .fillMaxWidth()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val size = Size(screenWidth.toPx() - screenWidth.toPx()/3, screenWidth.toPx()- screenWidth.toPx()/3)
            var currentAngle = 0f
            data.forEachIndexed { index, value ->
                val sweepAngle = value / total * 360f
                val gradient = Brush.radialGradient(
                    colors = gradientColors[index],
                    center = Offset(x = this.center.x, screenHeight.toPx() - screenWidth.toPx()/3),
                    radius = size.width / 2f,
                )

                drawArc(
                    brush = gradient,
                    startAngle = startAngle + currentAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                    topLeft = Offset((screenWidth.toPx()/3) / 2, screenHeight.toPx() - (screenWidth.toPx() - screenWidth.toPx()/3)),
                    size = size,
                    style = Fill,
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
fun displayWinner(list: List<Votes>) {
    var currentLeader = mutableListOf<Votes>( Votes("none", 0))
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

    }

}

