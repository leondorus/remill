package org.leondorus.remill.ui.screens.drug

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.leondorus.remill.domain.drugs.DrugEditUseCase
import org.leondorus.remill.domain.model.NotifType
import org.leondorus.remill.domain.model.NotifTypes
import org.leondorus.remill.domain.model.Schedule
import org.leondorus.remill.domain.model.UsePattern
import org.leondorus.remill.domain.notifgroups.NotifGroupEditRepo
import org.leondorus.remill.domain.notifgroups.NotifGroupEditUseCase
import java.time.LocalDateTime

class DrugAddViewModel(private val drugEditUseCase: DrugEditUseCase, private val notifGroupEditUseCase: NotifGroupEditUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(DrugAddUiState("", false, "", emptyList()))
    val uiState: StateFlow<DrugAddUiState>
        get() = _uiState.asStateFlow()

    suspend fun saveCurDrug() {
        val drug = drugEditUseCase.addDrug(_uiState.value.drugName)

        val notifTypes = NotifTypes(push = NotifType.Push(true))
        val usePattern = UsePattern(Schedule(_uiState.value.times), notifTypes)
        val notifGroup = notifGroupEditUseCase.addNotifGroup(_uiState.value.notifGroupName, usePattern)

        val newDrug = drug.copy(notifGroup = notifGroup.id)
        drugEditUseCase.updateDrug(newDrug)
    }

    fun updateDrugName(newName: String) {
        _uiState.update { uiState -> uiState.copy(drugName = newName) }
    }

    fun updateNotifGroupName(newName: String) {
        _uiState.update { uiState -> uiState.copy(notifGroupName = newName) }
    }

    fun addNotifTime(time: LocalDateTime) {
        val newTimes = _uiState.value.times + time
        _uiState.update { uiState -> uiState.copy(times = newTimes) }
    }
    fun deleteNotifTime(time: LocalDateTime) {
        val newTimes = _uiState.value.times.filter { it != time }
        _uiState.update { uiState -> uiState.copy(times = newTimes) }
    }

    fun showDialog() {
        _uiState.update { uiState -> uiState.copy(isDialogShown = true) }
    }

    fun dismissDialog() {
        _uiState.update { uiState -> uiState.copy(isDialogShown = false) }
    }
}

data class DrugAddUiState(
    val drugName: String,
    val isDialogShown: Boolean,
    val notifGroupName: String,
    val times: List<LocalDateTime>
)