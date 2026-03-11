package com.example.absenbnn.ui.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absenbnn.domain.usecase.GetWeeklyReportUseCase
import com.example.absenbnn.util.DateKeys
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeeklyReportViewModel(
    private val getWeeklyReportUseCase: GetWeeklyReportUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(
        ReportUiState(
            key = DateKeys.weekKeyIso(DateKeys.parseDate(DateKeys.todayString())),
        ),
    )
    val state: StateFlow<ReportUiState> = _state.asStateFlow()

    init {
        load(_state.value.key)
    }

    fun setWeekKey(value: String) {
        _state.value = _state.value.copy(key = value, errorMessage = null)
    }

    fun load(weekKey: String) {
        val trimmed = weekKey.trim()
        _state.value = _state.value.copy(isLoading = true, errorMessage = null)
        viewModelScope.launch {
            runCatching { getWeeklyReportUseCase.execute(trimmed) }
                .onSuccess { (rows, summary) ->
                    _state.value = _state.value.copy(isLoading = false, rows = rows, summary = summary)
                }
                .onFailure { e ->
                    _state.value = _state.value.copy(isLoading = false, errorMessage = e.message ?: "Gagal memuat rekap")
                }
        }
    }
}

