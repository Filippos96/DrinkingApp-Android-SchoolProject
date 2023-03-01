package com.example.drinkingapp

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

data class Message(
    val message: String = "",
    val sentAt: Long = 0,
){
    constructor() : this("", 0) // add a no-argument constructor for Firebase
}


class ChatRoomViewModel : ViewModel() {
    private val database = Firebase.database
    private val messagesRef = database.getReference("messages")

    // MutableState holds the current list of messages
    private val _messages = mutableStateListOf<Message>()
    val messages: List<Message>
        get() = _messages

    init {
        // Listen for new messages and update the list
        messagesRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val newMessage = snapshot.getValue(Message::class.java)
                newMessage?.let { it: Message ->
                    _messages.add(it)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                // Not used in this example
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val removedMessage = snapshot.getValue(Message::class.java)
                removedMessage?.let {it: Message ->
                    _messages.remove(it)
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // Not used in this example
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Error fetching messages", error.toException())
            }
        })
    }

    // Add a new message to Firebase Realtime Database
    fun sendMessage(message: String) {
        val newMessage = Message(message, System.currentTimeMillis())
        messagesRef.push().setValue(newMessage)
    }
}

@Composable
fun ChatRoomScreen(viewModel: ChatRoomViewModel) {
    val messages = viewModel.messages

    // UI for displaying messages and sending a new message
    Column {
        // Display the list of messages
        LazyColumn {
            items(messages.size) { i ->
                MessageItem(messages[i])
            }
        }

        // Input field for sending a new message
        SendMessageInput(onSend = { text ->
            viewModel.sendMessage(text)
        })
    }
}

@Composable
fun MessageItem(message: Message) {
    // UI for displaying a message
    Text(text = message.message)
}

@Composable
fun SendMessageInput(onSend: (String) -> Unit) {
    var messageText  by remember { mutableStateOf("") }
    // UI for sending a message
    // Call the onSend callback when a message is sent

    TextField(value = messageText, onValueChange = { messageText = it })

    Button(
        onClick = { onSend(messageText) },
        shape = RoundedCornerShape(40),
        modifier = Modifier
            .size(width = 200.dp, height = 60.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFFA500), // set the background color of the button to bright orange
            contentColor = Color.White // set the text color to white
        )
    ) {
        Text(text = "Send") // set the text displayed on the button
    }
}

