package com.example.myapplication.screen.register

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegisterViewModel(private val registerService: RegisterService = RegisterService()) : ViewModel() {

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState = _registerState.asStateFlow()

    fun register(email: String, password: String) {
        _registerState.value = RegisterState.Loading
        registerService.register(
            email = email,
            password = password,
            onSuccess = {
                _registerState.value = RegisterState.Success
            },
            onError = {
                _registerState.value = RegisterState.Error(it.localizedMessage ?: "Registration failed")
            }
        )
    }

    sealed class RegisterState {
        object Idle : RegisterState()
        object Loading : RegisterState()
        object Success : RegisterState()
        data class Error(val message: String) : RegisterState()
    }
}
