package com.example.absenbnn.ui.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absenbnn.domain.model.ReportRow
import com.example.absenbnn.domain.model.ReportSummary
import com.example.absenbnn.domain.usecase.GetDailyReportUseCase
import com.example.absenbnn.util.DateKeys
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ReportUiState(
    val key: String = "",
    val rows: List<ReportRow> = emptyList(),
    val summary: ReportSummary? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

class DailyReportViewModel(
    private val getDailyReportUseCase: GetDailyReportUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(ReportUiState(key = DateKeys.todayString()))
    val state: StateFlow<ReportUiState> = _state.asStateFlow()

    init {
        load(_state.value.key)
    }

    fun setDate(value: String) {
        _state.value = _state.value.copy(key = value, errorMessage = null)
    }

    fun load(date: String) {
        val trimmed = date.trim()
        _state.value = _state.value.copy(isLoading = true, errorMessage = null)
        viewModelScope.launch {
            val ok = runCatching { DateKeys.parseDate(trimmed) }.isSuccess
            if (!ok) {
                _state.value = _state.value.copy(isLoading = false, errorMessage = "Format tanggal harus YYYY-MM-DD")
                return@launch
            }
            runCatching { getDailyReportUseCase.execute(trimmed) }
                .onSuccess { (rows, summary) ->
                    _state.value = _state.value.copy(isLoading = false, rows = rows, summary = summary)
                }
                .onFailure { e ->
                    _state.value = _state.value.copy(isLoading = false, errorMessage = e.message ?: "Gagal memuat rekap")
                }
        }
    }
}

