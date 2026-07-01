package com.example.myapplication.screen.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel(private val loginService: LoginService = LoginService()) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState = _loginState.asStateFlow()

    val isAuthorized = loginService.isAuthorized

    fun login(email: String, password: String) {
        _loginState.value = LoginState.Loading
        loginService.login(
            email = email,
            password = password,
            onSuccess = {
                _loginState.value = LoginState.Success
            },
            onError = {
                _loginState.value = LoginState.Error(it.localizedMessage ?: "Login failed")
            }
        )
    }

    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        object Success : LoginState()
        data class Error(val message: String) : LoginState()
    }
}
