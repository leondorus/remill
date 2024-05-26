package org.leondorus.remill.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.leondorus.remill.domain.DrugEditUseCase
import org.leondorus.remill.domain.DrugGetUseCase
import org.leondorus.remill.domain.model.Drug

class DrugViewModel(
    drugGetUseCase: DrugGetUseCase,
    drugEditUseCase: DrugEditUseCase,
) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val drugUiState: StateFlow<DrugUiState> = drugGetUseCase
        .getAllDrugs()
        .map{DrugUiState(it) }
        .stateIn(scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = DrugUiState()
        )
}

data class DrugUiState(val drugs: List<Drug> = listOf())