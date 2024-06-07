package org.leondorus.remill.ui.screens.drugs

import androidx.compose.runtime.isTraceInProgress
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.leondorus.remill.domain.drugs.DrugEditUseCase
import org.leondorus.remill.domain.drugs.DrugGetUseCase
import org.leondorus.remill.domain.model.Drug
import org.leondorus.remill.domain.notifgroups.NotifGroupEditUseCase

class DrugsViewModel(
    drugGetUseCase: DrugGetUseCase,
    private val drugEditUseCase: DrugEditUseCase,
    private val notifGroupEditUseCase: NotifGroupEditUseCase
) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val drugs: StateFlow<List<Drug>> =
        drugGetUseCase.getAllDrugs().stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = emptyList()
            )

    private val _uiState = MutableStateFlow<DrugsUiState>(DrugsUiState(false))
    val uiState = _uiState.asStateFlow()

    suspend fun deleteDrug(drug: Drug) {
        if (drug.notifGroupId != null) {
            val freeDrug = drug.copy(notifGroupId = null)
            drugEditUseCase.updateDrug(freeDrug)
            notifGroupEditUseCase.deleteNotifGroup(drug.notifGroupId)
        }
        drugEditUseCase.deleteDrug(drug.id)
    }

    fun showAddDialog() {
        _uiState.update { it.copy(isChoosingAddMethod = true) }
    }

    fun dismissAddDialog() {
        _uiState.update { it.copy(isChoosingAddMethod = false) }
    }
}

data class DrugsUiState(val isChoosingAddMethod: Boolean)