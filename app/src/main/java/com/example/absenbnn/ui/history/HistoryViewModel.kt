package com.example.absenbnn.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absenbnn.domain.usecase.GetHistoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HistoryUiState(
    val weeks: List<String> = emptyList(),
    val months: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

class HistoryViewModel(
    private val getHistoryUseCase: GetHistoryUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(HistoryUiState())
    val state: StateFlow<HistoryUiState> = _state.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        _state.value = _state.value.copy(isLoading = true, errorMessage = null)
        viewModelScope.launch {
            runCatching {
                val weeks = getHistoryUseCase.weeks()
                val months = getHistoryUseCase.months()
                weeks to months
            }.onSuccess { (weeks, months) ->
                _state.value = _state.value.copy(isLoading = false, weeks = weeks, months = months)
            }.onFailure { e ->
                _state.value = _state.value.copy(isLoading = false, errorMessage = e.message ?: "Gagal memuat riwayat")
            }
        }
    }
}

