package org.leondorus.remill.ui.screens.drug

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.leondorus.remill.domain.drugs.DrugEditUseCase

class DrugAddViewModel(private val drugEditUseCase: DrugEditUseCase): ViewModel() {
    private val _uiState = MutableStateFlow(DrugEditUiState(""))
    val uiState: StateFlow<DrugEditUiState>
        get() = _uiState.asStateFlow()
    suspend fun saveCurDrug() {
        drugEditUseCase.addDrug(_uiState.value.name)
    }

    fun updateDrugName(newName: String) {
        _uiState.update { uiState -> uiState.copy(name = newName) }
    }
}

data class DrugUiState(val newDrugName: String)