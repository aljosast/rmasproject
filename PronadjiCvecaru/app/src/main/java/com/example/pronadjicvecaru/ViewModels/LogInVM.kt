package com.example.pronadjicvecaru.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pronadjicvecaru.repository.Authentication

class LogInVM(private val repository: Authentication, val go : () -> Unit): ViewModel() {

    var email = mutableStateOf("")
    var pass = mutableStateOf("")
    var passwordVissible = mutableStateOf(false)

    fun Signin(){
        repository.SignIn(email.value, pass.value, go)
    }

    init {
        repository.Init()
    }
}

class LogInFactory(val go : () -> Unit) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LogInVM::class.java)) {
            return LogInVM(Authentication(), go) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}