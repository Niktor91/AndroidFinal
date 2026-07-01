package com.example.myapplication.screen.register

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterService {
    private val firebaseAuth:FirebaseAuth = Firebase.auth

    fun register(
        email: String,
        password: String,
        onSuccess: (AuthResult) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener(onSuccess)
            .addOnFailureListener(onError)
    }
}