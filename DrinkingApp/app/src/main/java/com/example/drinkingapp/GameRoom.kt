package com.example.drinkingapp

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

data class Lobby(
    val lobbyKey: String = "",
    var players: List<String> = emptyList()
)

class GameRoomViewModel : ViewModel() {
    private val database = Firebase.database
    private val lobbyRef = database.getReference("lobbies")

    private val _lobbies = mutableStateListOf<Lobby>()
    val lobbies: List<Lobby>
        get() = _lobbies

    init {
        lobbyRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val newLobby = snapshot.getValue(Lobby::class.java)
                newLobby?.let { it: Lobby ->
                    _lobbies.add(it)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val updatedLobby = snapshot.getValue(Lobby::class.java)
                updatedLobby?.let { it: Lobby ->
                    val index = _lobbies.indexOfFirst { it.lobbyKey == updatedLobby.lobbyKey }
                    if (index != -1) {
                        _lobbies[index] = updatedLobby
                    }
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun createNewLobby(username: String) : String {
        val lobbyKey = generateRandomKey()
        val newLobby = Lobby(lobbyKey = lobbyKey, players = listOf(username))
        lobbyRef.child(lobbyKey).setValue(newLobby)
        return lobbyKey
    }

    fun joinLobby(username: String, lobbyKey: String) {

        var lobbyToUpdate = _lobbies.find { it.lobbyKey == lobbyKey }

        if (lobbyToUpdate != null) {
            val updatedPlayers = lobbyToUpdate.players.toMutableList().apply { add(username) }
            lobbyToUpdate.players = updatedPlayers
            lobbyRef.child(lobbyToUpdate.lobbyKey).setValue(lobbyToUpdate)
        }
    }

    private fun generateRandomKey() : String {
        var uniqueKey: String
        do {
            // Generate a random 6-digit string
            uniqueKey = (100000..999999).random().toString()

            for (lobby in _lobbies) {
                if (lobby.lobbyKey == uniqueKey) {
                    uniqueKey = ""
                }
            }
        } while (uniqueKey.isEmpty())
        return uniqueKey
    }
}

@Composable
fun GameRoomScreen(viewModel: GameRoomViewModel) {
    var username by remember { mutableStateOf("") }
    var lobbyKey by remember { mutableStateOf("") }

    Column {
        TextField(value = username, onValueChange = { username = it })

        TextField(value = lobbyKey, onValueChange = { lobbyKey = it })

        Button(onClick = { viewModel.createNewLobby(username) }) {
            Text(text = "CREATE")
        }
        Button(onClick = { viewModel.joinLobby(username, lobbyKey) }) {
            Text(text = "JOIN")
        }
    }
}