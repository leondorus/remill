package org.leondorus.remill.ui.screens.drugs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.leondorus.remill.domain.drugs.DrugGetUseCase
import org.leondorus.remill.domain.model.Drug

class DrugsViewModel(
    drugGetUseCase: DrugGetUseCase,
) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val drugsUiState: StateFlow<DrugsUiState> =
        drugGetUseCase.getAllDrugs().map { DrugsUiState(it) }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = DrugsUiState()
            )
}

data class DrugsUiState(val drugs: List<Drug> = listOf())