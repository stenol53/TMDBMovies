package com.voak.android.tmdbmovies.ui.login

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.voak.android.tmdbmovies.R
import com.voak.android.tmdbmovies.model.Result
import com.voak.android.tmdbmovies.repository.LoginRepository
import javax.inject.Inject

class LoginFragmentViewModel @Inject constructor(private val loginRepository: LoginRepository): ViewModel() {

    private val _userName = MutableLiveData<String>()
    val userName = _userName
    private val _password = MutableLiveData<String>()
    val password = _password
    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading: LiveData<Boolean> = _loading
    private val _loginError = MutableLiveData<String?>().apply { value = null }
    val loginError = _loginError
    private val _sessionId = MutableLiveData<String?>().apply { value = null }
    val sessionId = _sessionId

    /**
     *  lambda that uses resId to get string by getString(resId: Int): String function.
     *  Need to set in LoginFragment.
     */
    lateinit var getString : (Int) -> String

    init {
        Log.i("LoginFragmentViewModel", this.toString())
    }

    /**
     *  Bind onClick method of login_button.
     */
    fun onLoginBtnClicked(@Suppress("UNUSED_PARAMETER") view: View) {
        if (!_userName.value.isNullOrEmpty() && !_password.value.isNullOrEmpty()) {
            _loginError.value = null
            _loading.value = true

            loginRepository.makeAuth(
                _userName.value.toString(),
                _password.value.toString(),
                callback = {
                    if (it is Result.Success) {
                        Log.i("LoginFragmentViewModel", "SessionId: ${it.data}")
                        _sessionId.value = it.data.toString()
                    } else {
                        Log.i("LoginFragmentViewModel", "error: ${(it as Result.Error).errorMessage}")
                        Log.i("LoginFragmentViewModel", "error: ${it.errorMessage?.length}")
                        when ((it as Result.Error).errorMessage) {
                            "timeout" -> _loginError.value = getString(R.string.timeout_error)
                            "HTTP 401 " -> _loginError.value = getString(R.string.wrong_login_or_password_error)
                            "Unable to resolve host \"api.themoviedb.org\": No address associated with hostname" ->
                                _loginError.value = getString(R.string.connection_error)
                        }
                    }
                    _loading.value = false
                })
        } else {
            _loginError.value = getString(R.string.need_login_and_password_error)
        }
    }
}