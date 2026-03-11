package com.example.absenbnn.ui.today

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absenbnn.domain.model.AttendanceBreakdown
import com.example.absenbnn.domain.model.AttendanceDaily
import com.example.absenbnn.domain.model.AttendanceDraft
import com.example.absenbnn.domain.model.Division
import com.example.absenbnn.domain.model.Session
import com.example.absenbnn.domain.usecase.GetAttendanceDailyUseCase
import com.example.absenbnn.domain.usecase.ObserveDivisionsUseCase
import com.example.absenbnn.domain.usecase.ObserveSessionUseCase
import com.example.absenbnn.domain.usecase.UpsertAttendanceDailyUseCase
import com.example.absenbnn.util.DateKeys
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class AttendanceTodayUiState(
    val session: Session? = null,
    val dateString: String = DateKeys.todayString(),
    val divisions: List<Division> = emptyList(),
    val selectedDivisionId: Long? = null,
    val jumlahText: String = "",
    val hadirText: String = "",
    val dinasText: String = "0",
    val terlambatText: String = "0",
    val sakitText: String = "0",
    val cutiText: String = "0",
    val izinText: String = "0",
    val offDinasText: String = "0",
    val lainLainText: String = "0",
    val lainLainNote: String = "",
    val loadedExisting: AttendanceDaily? = null,
    val isSaving: Boolean = false,
    val message: String? = null,
) {
    fun jumlahInt(): Int = jumlahText.toIntOrNull() ?: 0
    fun hadirInt(): Int = hadirText.toIntOrNull() ?: 0
    fun kurangInt(): Int = (jumlahInt() - hadirInt()).coerceAtLeast(0)

    fun breakdown(): AttendanceBreakdown {
        return AttendanceBreakdown(
            dinas = dinasText.toIntOrNull() ?: 0,
            terlambat = terlambatText.toIntOrNull() ?: 0,
            sakit = sakitText.toIntOrNull() ?: 0,
            cuti = cutiText.toIntOrNull() ?: 0,
            izin = izinText.toIntOrNull() ?: 0,
            offDinas = offDinasText.toIntOrNull() ?: 0,
            lainLain = lainLainText.toIntOrNull() ?: 0,
            lainLainNote = lainLainNote,
        )
    }
}

class AttendanceTodayViewModel(
    observeSession: ObserveSessionUseCase,
    observeDivisions: ObserveDivisionsUseCase,
    private val getAttendanceDailyUseCase: GetAttendanceDailyUseCase,
    private val upsertAttendanceDailyUseCase: UpsertAttendanceDailyUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(AttendanceTodayUiState())
    val state: StateFlow<AttendanceTodayUiState> = _state.asStateFlow()

    private val sessionFlow = observeSession.execute().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null,
    )

    init {
        viewModelScope.launch {
            sessionFlow.collect { session ->
                _state.value = _state.value.copy(session = session)
            }
        }
        viewModelScope.launch {
            observeDivisions.active().collect { divisions ->
                val selected = _state.value.selectedDivisionId
                val nextSelected = selected ?: divisions.firstOrNull()?.id
                _state.value = _state.value.copy(divisions = divisions, selectedDivisionId = nextSelected)
                reloadExisting()
            }
        }
    }

    fun setDate(value: String) {
        _state.value = _state.value.copy(dateString = value, message = null)
        reloadExisting()
    }

    fun selectDivision(divisionId: Long) {
        _state.value = _state.value.copy(selectedDivisionId = divisionId, message = null)
        reloadExisting()
    }

    fun setJumlah(value: String) {
        _state.value = _state.value.copy(jumlahText = value, message = null)
    }

    fun setHadir(value: String) {
        _state.value = _state.value.copy(hadirText = value, message = null)
    }

    fun setDinas(value: String) {
        _state.value = _state.value.copy(dinasText = value, message = null)
    }

    fun setTerlambat(value: String) {
        _state.value = _state.value.copy(terlambatText = value, message = null)
    }

    fun setSakit(value: String) {
        _state.value = _state.value.copy(sakitText = value, message = null)
    }

    fun setCuti(value: String) {
        _state.value = _state.value.copy(cutiText = value, message = null)
    }

    fun setIzin(value: String) {
        _state.value = _state.value.copy(izinText = value, message = null)
    }

    fun setOffDinas(value: String) {
        _state.value = _state.value.copy(offDinasText = value, message = null)
    }

    fun setLainLain(value: String) {
        _state.value = _state.value.copy(lainLainText = value, message = null)
    }

    fun setLainLainNote(value: String) {
        _state.value = _state.value.copy(lainLainNote = value, message = null)
    }

    fun clearMessage() {
        _state.value = _state.value.copy(message = null)
    }

    fun save() {
        val current = _state.value
        val divisionId = current.selectedDivisionId ?: return
        val userId = current.session?.userId ?: return
        if (current.isSaving) return

        _state.value = current.copy(isSaving = true, message = null)
        viewModelScope.launch {
            val draft = AttendanceDraft(
                divisionId = divisionId,
                attendanceDate = current.dateString.trim(),
                jumlah = current.jumlahText.toIntOrNull() ?: 0,
                hadir = current.hadirText.toIntOrNull() ?: 0,
                breakdown = current.breakdown(),
            )

            val normalizedResult = runCatching { DateKeys.parseDate(draft.attendanceDate) }.fold(
                onSuccess = { Result.success(Unit) },
                onFailure = { Result.failure(IllegalArgumentException("Format tanggal harus YYYY-MM-DD")) },
            )

            normalizedResult.getOrElse { e ->
                _state.value = _state.value.copy(isSaving = false, message = e.message)
                return@launch
            }

            upsertAttendanceDailyUseCase.execute(draft, userId)
                .onSuccess {
                    _state.value = _state.value.copy(isSaving = false, message = "Tersimpan")
                    reloadExisting()
                }
                .onFailure { e ->
                    _state.value = _state.value.copy(isSaving = false, message = e.message ?: "Gagal menyimpan")
                }
        }
    }

    private fun reloadExisting() {
        val current = _state.value
        val divisionId = current.selectedDivisionId ?: return
        val date = current.dateString.trim()
        viewModelScope.launch {
            val existing = runCatching { getAttendanceDailyUseCase.execute(divisionId, date) }.getOrNull()
            if (existing == null) {
                _state.value = _state.value.copy(
                    loadedExisting = null,
                    jumlahText = "",
                    hadirText = "",
                    dinasText = "0",
                    terlambatText = "0",
                    sakitText = "0",
                    cutiText = "0",
                    izinText = "0",
                    offDinasText = "0",
                    lainLainText = "0",
                    lainLainNote = "",
                )
            } else {
                _state.value = _state.value.copy(
                    loadedExisting = existing,
                    jumlahText = existing.jumlah.toString(),
                    hadirText = existing.hadir.toString(),
                    dinasText = existing.breakdown.dinas.toString(),
                    terlambatText = existing.breakdown.terlambat.toString(),
                    sakitText = existing.breakdown.sakit.toString(),
                    cutiText = existing.breakdown.cuti.toString(),
                    izinText = existing.breakdown.izin.toString(),
                    offDinasText = existing.breakdown.offDinas.toString(),
                    lainLainText = existing.breakdown.lainLain.toString(),
                    lainLainNote = existing.breakdown.lainLainNote.orEmpty(),
                )
            }
        }
    }
}

