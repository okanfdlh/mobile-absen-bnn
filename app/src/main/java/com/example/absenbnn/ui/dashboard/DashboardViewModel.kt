package com.example.absenbnn.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absenbnn.domain.model.Session
import com.example.absenbnn.domain.usecase.LogoutUseCase
import com.example.absenbnn.domain.usecase.ObserveSessionUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DashboardViewModel(
    observeSessionUseCase: ObserveSessionUseCase,
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {
    val session: StateFlow<Session?> = observeSessionUseCase.execute().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null,
    )

    fun logout() {
        viewModelScope.launch {
            logoutUseCase.execute()
        }
    }
}

