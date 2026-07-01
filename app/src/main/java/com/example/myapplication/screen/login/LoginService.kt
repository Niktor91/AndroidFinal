package com.example.myapplication.screen.login

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginService {
    private val firebaseAuth: FirebaseAuth = Firebase.auth
    val isAuthorized= firebaseAuth.currentUser!=null

    fun login(
        email: String,
        password: String,
        onSuccess: (AuthResult) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener(onSuccess)
            .addOnFailureListener(onError)
    }
}