package com.example.drinkingapp

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

data class MessageObject(
    val text: String
)

class PromptViewModel {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val messagesRef: DatabaseReference = database.reference.child("messages")

    fun sendMessage(message: String) {
        val key = messagesRef.push().key ?: return
        val newMessage = MessageObject(message)
        messagesRef.child(key).setValue(newMessage, object : DatabaseReference.CompletionListener {
            override fun onComplete(error: DatabaseError?, ref: DatabaseReference) {
                if (error != null) {
                    Log.e(TAG, "Error sending message", error.toException())
                }
            }
        })
    }
}