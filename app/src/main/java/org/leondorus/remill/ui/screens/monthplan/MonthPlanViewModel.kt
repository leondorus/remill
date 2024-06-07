package org.leondorus.remill.ui.screens.monthplan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.leondorus.remill.domain.sharing.FullDrugInfoUseCase
import org.leondorus.remill.domain.sharing.HolyTriple
import java.time.LocalDate

class MonthPlanViewModel(fullDrugInfoUseCase: FullDrugInfoUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(MonthPlanUiState(LocalDate.now()))
    val uiState: StateFlow<MonthPlanUiState>
        get() = _uiState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val holyTriples: StateFlow<List<HolyTriple>> = uiState.flatMapLatest { uiState ->
        val currentDate = uiState.selectedDate
        fullDrugInfoUseCase.getHolyTriplesInRange(
            currentDate.atStartOfDay(),
            currentDate.atTime(23, 59)
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = emptyList()
    )

    fun chooseCurrentDate(newDate: LocalDate) {
        _uiState.update { uiState -> uiState.copy(selectedDate = newDate) }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class MonthPlanUiState(val selectedDate: LocalDate)