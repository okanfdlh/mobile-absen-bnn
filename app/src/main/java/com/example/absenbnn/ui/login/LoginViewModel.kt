package com.example.absenbnn.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absenbnn.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state.asStateFlow()

    private val _loggedIn = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val loggedIn = _loggedIn.asSharedFlow()

    fun setUsername(value: String) {
        _state.value = _state.value.copy(username = value, errorMessage = null)
    }

    fun setPassword(value: String) {
        _state.value = _state.value.copy(password = value, errorMessage = null)
    }

    fun login() {
        val current = _state.value
        if (current.isLoading) return
        _state.value = current.copy(isLoading = true, errorMessage = null)
        viewModelScope.launch {
            val result = loginUseCase.execute(current.username, current.password)
            result
                .onSuccess {
                    _state.value = _state.value.copy(isLoading = false)
                    _loggedIn.tryEmit(Unit)
                }
                .onFailure { e ->
                    _state.value = _state.value.copy(isLoading = false, errorMessage = e.message ?: "Gagal login")
                }
        }
    }
}
