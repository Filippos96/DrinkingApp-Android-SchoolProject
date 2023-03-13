package com.example.drinkingapp

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

data class Lobby(
    var players: List<String> = emptyList(),
    val lobbyKey: String = "",
    var gameStarted: Boolean = false,
    var prompts: List<String> = emptyList()
)

data class Player(
    val name: String = ""
)

data class User(
    val name: String = "",
    val email: String = ""
)

data class Post(
    val userId: String = "",
    val username: String = "",
    val title: String = "",
    val body: String = ""
)

class GameRoomViewModel() : ViewModel() {
    private val database = Firebase.database.reference
    private val lobbiesRef = database.child("lobbies")

    private val postReference = database.child("posts")

    private val _lobbies = listOf<Lobby>()

    private val _lobby = mutableStateOf(Lobby())
    val lobby: MutableState<Lobby>
        get() = _lobby

    private val _prompts = mutableStateListOf<String>()
    val prompts: List<String>
        get() = _prompts

    private val _currentAnswers = mutableStateListOf<String>()
    val currentAnswers: List<String>
        get() = _currentAnswers

    private val _allAnswers = mutableStateListOf<String>()
    val allAnswers: List<String>
        get() = _allAnswers


    private val _posts = mutableStateListOf<Post>()
    val posts: List<Post>
        get() = _posts

    private val _users = mutableStateListOf<User>()
    val users: List<User>
        get() = _users

    init {
        addPostEventListener(postReference)
    }

    private fun addPostEventListener(postReference: DatabaseReference) {
        // [START post_value_event_listener]
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
              //  val post = dataSnapshot.getValue<Post>()
                // ...
                val updatedPosts = mutableListOf<Post>()
                for (postSnapshot in dataSnapshot.children) {
                    val post = postSnapshot.getValue(Post::class.java)
                    post?.let {
                        updatedPosts.add(it)
                    }
                }
                _posts.clear()
                _posts.addAll(updatedPosts)

                Log.d("Hej", "ANANANAN")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        postReference.addValueEventListener(postListener)
        // [END post_value_event_listener]
    }

    fun writeNewPosts(userId: String, username: String, title: String, body: String) {

        val post = Post(userId, username, title, body)
        val postId = postReference.push().key
        postId?.let {
            database.child("posts").child(it).setValue(post)
        }
    }

    fun writeNewPost(userId: String, username: String, title: String, body: String) {

        val post = Post(userId, username, title, body)

        database.child("users").child(userId).child("posts").setValue(post)
    }

    fun writeNewUser(userId: String, name: String, email: String) {
        val user = User(name, email)

      //  database.child("users").child(userId).child("username").setValue(name)
        database.child("users").child(userId).setValue(user)
        val userReference = database.child("users").child(userId)
        addUserEventListener(userReference)
    }

    private fun addUserEventListener(userReference: DatabaseReference) {
        // [START post_value_event_listener]
        val userListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get User object and use the values to update the UI
                //  val user = dataSnapshot.getValue<Post>()
                // ...
                val user = dataSnapshot.getValue<User>()
                /*
                user.add(user)
                for (userSnapshot in dataSnapshot.children) {
                    val user = userSnapshot.getValue(User::class.java)
                    user?.let {
                        updatedUsers.add(it)
                    }
                }
                 */
                user?.let {
                    _users.add(user)
                }
                Log.d("Hej", "I LIKE THIS")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        userReference.addValueEventListener(userListener)
        // [END post_value_event_listener]
    }

    fun createNewLobby(username: String, navController: NavController) : String {
        val lobbyKey = generateRandomKey()
        val newLobby = Lobby(lobbyKey = lobbyKey, players = listOf(username), prompts = listOf(""))
        lobbiesRef.child(lobbyKey).setValue(newLobby)
        val lobbyReference = database.child("lobbies").child(lobbyKey)
        addLobbyEventListener(navController, lobbyReference, lobbyKey)
        return lobbyKey
    }

    private fun addLobbyEventListener(navController: NavController, lobbyReference: DatabaseReference, lobbyKey: String) {
        // [START post_value_event_listener]
        val lobbyListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val lobby = dataSnapshot.getValue<Lobby>()
                lobby?.let {
                    _lobby.value = lobby
                    if ( it.gameStarted ) {
                        Log.d("Hej", "Fortfarande connectad")
                        lobbyReference.removeEventListener(this)
                        navController.navigate(Screen.CreatePrompt.withArgs(lobbyKey))
                    }
                }
                Log.d("Hej", "I LIKE THIS")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        Log.d("Hej", "Lets see how often it triggers")
        lobbyReference.addValueEventListener(lobbyListener)
        // [END post_value_event_listener]
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

    fun joinLobby(username: String, lobbyKey: String) {

        val lobbyToJoinRef = lobbiesRef.child(lobbyKey)

        lobbyToJoinRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val lobbyToJoin = dataSnapshot.getValue(Lobby::class.java)
                if (lobbyToJoin != null) {
                    val updatedPlayers = lobbyToJoin.players.toMutableList().apply { add(username) }
                    lobbyToJoin.players = updatedPlayers
                    lobbiesRef.child(lobbyToJoin.lobbyKey).setValue(lobbyToJoin)
                } else {
                    Log.e(TAG, "Lobby not found: $lobbyKey")
                }
                Log.d("Hej", "QWEERRIIO")
            }


            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(TAG, "Error joining lobby: $lobbyKey", databaseError.toException())
            }
        })
    }

    fun startGame(lobbyKey: String){
        val lobbyRef = database.child("lobbies").child(lobbyKey)
        lobbyRef.child("gameStarted").setValue(true)
    }
    fun startGames(lobbyKey: String){
        val lobbyRef = database.child("lobbies").child(lobbyKey)
        lobbyRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lobby = snapshot.getValue(Lobby::class.java)
                if (lobby != null) {
                    lobby.gameStarted = true
                    lobbyRef.setValue(lobby)
                }
                Log.d("Hej", "JAJAJAJAJJJAA")
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error as necessary
            }
        })
    }

    fun createNewPrompt(prompt: String, lobbyKey: String, navController: NavController) {
        val promptsRef = database.child("lobbies").child(lobbyKey).child("prompts")

     //   promptsRef.setValue(listOf(prompt))

        promptsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get the current list of prompts
                val prompts = dataSnapshot.getValue<List<String>>()
                if(prompts == null) {
                    Log.d("Hej", "DEN FANNS INTE")
                }
                if (prompts != null) {
                    Log.d("Hej", "DEN FANNS")
                    // Add the new prompt to the list
                    val newPrompts = prompts.toMutableList()
                    newPrompts.add(prompt)
                    Log.d("Hej", "There is: " + _prompts.size.toString() + " prompts")
                    // Update the list of prompts in the database
                    promptsRef.setValue(newPrompts)
                    addPromptsEventListener(navController, promptsRef, lobbyKey)
                    navController.navigate(Screen.Waiting.withArgs(lobbyKey))
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun addPromptsEventListener(navController: NavController, promptsReference: DatabaseReference, lobbyKey: String) {
        val promptsListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val prompts = dataSnapshot.getValue<List<String>>()

                prompts?.let {
                    Log.d("Hej", prompts.size.toString())
                    Log.d("Hej", _lobby.value.players.size.toString())
                    if ( prompts.size == _lobby.value.players.size ) {
                        Log.d("Hej", "It does go in here")
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(3000)
                            _prompts.addAll(prompts)
                            Log.d("Hej", "There is: " + _prompts.size.toString() + " prompts! 2nd!!")
                            navController.navigate(Screen.Questions.withArgs(lobbyKey))
                        }
                    }

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        promptsReference.addValueEventListener(promptsListener)
    }

    fun submitAnswer(player: String, lobbyKey: String, navController: NavController) {
        val addAnswersRef = database.child("lobbies").child(lobbyKey).child("currentAnswers")

        addAnswersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get the current list of prompts
                val answers = dataSnapshot.getValue<List<String>>()
                if(answers == null) {
                    Log.d("Hej", "It didnt exist")
                    Log.d("Hej", "Creating new list of answers")
                    // Create a new list of answers if the current list is null
                    val newAnswers = mutableListOf(player)

                    // Update the list of prompts in the database
                    addAnswersRef.setValue(newAnswers)
                } else {
                    Log.d("Hej", "DEN FANNS")
                    // Add the new prompt to the list
                    val newAnswers = answers.toMutableList()
                    newAnswers.add(player)

                    // Update the list of prompts in the database
                    addAnswersRef.setValue(newAnswers)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
        addCurrentAnswersEventListener(navController, addAnswersRef, lobbyKey)
    }

    private fun addCurrentAnswersEventListener(navController: NavController, currentAnswersRef: DatabaseReference, lobbyKey: String) {
        val currentAnswersListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val currentAnswers = dataSnapshot.getValue<List<String>>()

                currentAnswers?.let {
                    if ( currentAnswers.size == _lobby.value.players.size ) {
                        Log.d("Hej", "It does go in here")
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(3000)
                            _currentAnswers.clear()
                            _currentAnswers.addAll(currentAnswers)
                            Log.d("Hej", "There is: " + _currentAnswers.size.toString() + " current Answers!")
                            navController.navigate(Screen.Result.withArgs(lobbyKey))
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        currentAnswersRef.addValueEventListener(currentAnswersListener)
    }

    fun addToTotal(player: String, lobbyKey: String) {
        val allAnswers = database.child("lobbies").child(lobbyKey).child("allAnswers")

        allAnswers.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get the current list of prompts
                val answers = dataSnapshot.getValue<List<String>>()
                if (answers == null) {
                    Log.d("Hej", "It didnt exist")
                    Log.d("Hej", "Creating new list of answers")
                    // Create a new list of answers if the current list is null
                    val newAnswers = mutableListOf(player)
                    // Update the list of prompts in the database
                    allAnswers.setValue(newAnswers)
                } else {
                    Log.d("Hej", "DEN FANNS")
                    // Add the new prompt to the list
                    val newAnswers = answers.toMutableList()
                    newAnswers.add(player)
                    // Update the list of prompts in the database
                    allAnswers.setValue(newAnswers)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

}