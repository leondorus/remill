package org.leondorus.remill.ui.screens.drug

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.leondorus.remill.domain.drugs.DrugEditUseCase
import org.leondorus.remill.domain.drugs.DrugGetUseCase
import org.leondorus.remill.domain.model.Drug
import org.leondorus.remill.domain.model.DrugId

class DrugEditViewModel(savedStateHandle: SavedStateHandle, drugGetUseCase: DrugGetUseCase, private val drugEditUseCase: DrugEditUseCase): ViewModel() {
    val drugId: DrugId = DrugId(checkNotNull(savedStateHandle[DrugEditDestination.itemIdArg]))

    private val _uiState = MutableStateFlow(DrugEditUiState(""))
    val uiState: StateFlow<DrugEditUiState>
        get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val drug = drugGetUseCase.getDrug(drugId).first()
            val name = drug?.name ?: ""
            _uiState.value = DrugEditUiState(name)
        }
    }

    fun updateDrugName(newName: String) {
        _uiState.update { uiState -> uiState.copy(name = newName) }
    }

    suspend fun saveEditedDrug() {
        val drug = Drug(drugId, _uiState.value.name, null) // Change to not null
        drugEditUseCase.updateDrug(drug)
    }
}

data class DrugEditUiState(
    val name: String
)