package org.leondorus.remill.ui.screens.drug

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.leondorus.remill.domain.drugs.DrugEditUseCase

class DrugEditViewModel(drugEditUseCase: DrugEditUseCase): ViewModel() {
    private val _uiState = MutableStateFlow<DrugEditUiState>(DrugEditUiState(""))
    val uiState: StateFlow<DrugEditUiState>
        get() = _uiState.asStateFlow()

    fun updateDrugName(newName: String) {
        _uiState.update { uiState -> uiState.copy(name = newName) }
    }

    fun addNewDrug() {

    }

    fun saveEditedDrug() {

    }
}

data class DrugEditUiState(
    val name: String
)