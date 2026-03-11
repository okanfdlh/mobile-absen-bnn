package com.example.absenbnn.ui.division

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absenbnn.domain.model.Division
import com.example.absenbnn.domain.usecase.ObserveDivisionsUseCase
import com.example.absenbnn.domain.usecase.UpsertDivisionUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DivisionViewModel(
    observeDivisionsUseCase: ObserveDivisionsUseCase,
    private val upsertDivisionUseCase: UpsertDivisionUseCase,
) : ViewModel() {
    val divisions: StateFlow<List<Division>> = observeDivisionsUseCase.all().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList(),
    )

    fun upsert(id: Long?, code: String, name: String, isActive: Boolean, onResult: (Result<Unit>) -> Unit) {
        viewModelScope.launch {
            val result = upsertDivisionUseCase.execute(id, code, name, isActive)
            onResult(result)
        }
    }
}

