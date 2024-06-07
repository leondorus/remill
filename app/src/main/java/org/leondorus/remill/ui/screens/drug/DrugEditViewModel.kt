package org.leondorus.remill.ui.screens.drug

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.leondorus.remill.RemillFileProvider
import org.leondorus.remill.domain.drugs.DrugEditUseCase
import org.leondorus.remill.domain.drugs.DrugGetUseCase
import org.leondorus.remill.domain.model.Drug
import org.leondorus.remill.domain.model.DrugId
import org.leondorus.remill.domain.model.NotifGroup
import org.leondorus.remill.domain.model.Schedule
import org.leondorus.remill.domain.model.UsePattern
import org.leondorus.remill.domain.notifgroups.NotifGroupEditUseCase
import org.leondorus.remill.domain.notifgroups.NotifGroupGetUseCase
import java.time.LocalDateTime

private const val TAG = "DrugEditViewModel"

class DrugEditViewModel(
    savedStateHandle: SavedStateHandle,
    drugGetUseCase: DrugGetUseCase,
    private val drugEditUseCase: DrugEditUseCase,
    notifGroupGetUseCase: NotifGroupGetUseCase,
    private val notifGroupEditUseCase: NotifGroupEditUseCase,
    private val fileProvider: RemillFileProvider,
) : ViewModel() {
    val drugId: DrugId = DrugId(checkNotNull(savedStateHandle[DrugEditDestination.itemIdArg]))
    private lateinit var notifGroup: NotifGroup

    private var isDrugOrNotifGroupNull: Boolean = false

    init {
        viewModelScope.launch {
            val drug = drugGetUseCase.getDrug(drugId).first()
            if (drug?.notifGroupId == null) {
                isDrugOrNotifGroupNull = true
                return@launch
            }

            val nullableNotifGroup = notifGroupGetUseCase.getNotifGroup(drug.notifGroupId).first()
            if (nullableNotifGroup == null) {
                isDrugOrNotifGroupNull = true
                return@launch
            }

            notifGroup = nullableNotifGroup
            val drugName = drug.name
            val notifGroupName = notifGroup.name
            val times = notifGroup.usePattern.schedule.times
            val photoUri = drug.photoPath
            _uiState.value = DrugEditUiState(
                name = drugName,
                isDialogShown = false,
                notifGroupName = notifGroupName,
                times = times,
                photoPath = photoUri,
                hasImage = photoUri != null
            )
        }
    }

    private val _uiState =
        MutableStateFlow(DrugEditUiState("", false, "", false, null, emptyList()))
    val uiState: StateFlow<DrugEditUiState>
        get() = _uiState.asStateFlow()


    suspend fun saveEditedDrug() {
        if (isDrugOrNotifGroupNull) {
            // TODO(make this known to user)
            Log.e(TAG, "Drug or notif group is null for drugId $drugId")
            return
        }
        Log.d(TAG, notifGroup.usePattern.notifTypes.toString())
        val usePattern = UsePattern(Schedule(uiState.value.times), notifGroup.usePattern.notifTypes)
        val notifGroup =
            NotifGroup(notifGroup.id, _uiState.value.notifGroupName, usePattern, emptyList())
        notifGroupEditUseCase.updateNotifGroup(notifGroup)
        val drug = Drug(
            drugId,
            _uiState.value.name,
            _uiState.value.photoPath,
            notifGroup.id
        ) // Change to not null

        drugEditUseCase.updateDrug(drug)
    }

    fun updateDrugName(newName: String) {
        _uiState.update { uiState -> uiState.copy(name = newName) }
    }

    fun updateNotifGroupName(newName: String) {
        _uiState.update { uiState -> uiState.copy(notifGroupName = newName) }
    }

    fun addNotifTime(time: LocalDateTime) {
        val newTimes = (_uiState.value.times + time).sorted()
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

    fun photoResultCallback(success: Boolean) {
        _uiState.update { uiState -> uiState.copy(hasImage = success) }
    }
    fun genNewUri(context: Context): Uri {
        val tempUri = fileProvider.getNewUri(context)
        _uiState.update { uiState -> uiState.copy(photoPath = tempUri) }
        return tempUri
    }
}

data class DrugEditUiState(
    val name: String,
    val isDialogShown: Boolean,
    val notifGroupName: String,
    val hasImage: Boolean,
    val photoPath: Uri?,
    val times: List<LocalDateTime>,
)