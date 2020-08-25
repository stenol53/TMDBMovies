package com.voak.android.tmdbmovies.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.voak.android.tmdbmovies.repository.LoginRepository
import javax.inject.Inject

class LoginFragmentViewModel @Inject constructor(private val loginRepository: LoginRepository): ViewModel() {

    fun auth(username: String, password: String) {
        loginRepository.makeAuth(username, password)
    }

    fun getLoadingLiveData(): LiveData<Boolean> {
        return loginRepository.loading
    }

    fun getLoginErrorLiveData(): LiveData<String?> {
        return loginRepository.loginError
    }

    fun getSessionIdLiveData(): LiveData<String?> {
        return loginRepository.sessionId
    }
}