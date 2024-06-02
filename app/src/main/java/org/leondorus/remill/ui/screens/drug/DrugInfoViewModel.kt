package org.leondorus.remill.ui.screens.drug

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.leondorus.remill.domain.drugs.DrugGetRepo
import org.leondorus.remill.domain.drugs.DrugGetUseCase
import org.leondorus.remill.domain.model.DrugId
import java.time.LocalDateTime

class DrugInfoViewModel(
    savedStateHandle: SavedStateHandle,
    drugGetUseCase: DrugGetUseCase
): ViewModel() {
    val drugId: DrugId = DrugId(checkNotNull(savedStateHandle[DrugInfoDestination.itemIdArg]))

    val uiState: StateFlow<DrugInfoUiState> = drugGetUseCase.getDrug(drugId)
        .filterNotNull()
        .map {
            DrugInfoUiState(name = it.name, "", listOf())
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = DrugInfoUiState("", "", listOf())
        )


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class DrugInfoUiState(val name: String, val notifGroupName: String, val times: List<LocalDateTime>)
