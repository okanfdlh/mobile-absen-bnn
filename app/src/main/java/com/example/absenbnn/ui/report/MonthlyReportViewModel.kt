package com.example.absenbnn.ui.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absenbnn.domain.usecase.GetMonthlyReportUseCase
import com.example.absenbnn.util.DateKeys
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MonthlyReportViewModel(
    private val getMonthlyReportUseCase: GetMonthlyReportUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(
        ReportUiState(
            key = DateKeys.monthKeyFromDateString(DateKeys.todayString()),
        ),
    )
    val state: StateFlow<ReportUiState> = _state.asStateFlow()

    init {
        load(_state.value.key)
    }

    fun setMonthKey(value: String) {
        _state.value = _state.value.copy(key = value, errorMessage = null)
    }

    fun load(monthKey: String) {
        val trimmed = monthKey.trim()
        _state.value = _state.value.copy(isLoading = true, errorMessage = null)
        viewModelScope.launch {
            runCatching { getMonthlyReportUseCase.execute(trimmed) }
                .onSuccess { (rows, summary) ->
                    _state.value = _state.value.copy(isLoading = false, rows = rows, summary = summary)
                }
                .onFailure { e ->
                    _state.value = _state.value.copy(isLoading = false, errorMessage = e.message ?: "Gagal memuat rekap")
                }
        }
    }
}

