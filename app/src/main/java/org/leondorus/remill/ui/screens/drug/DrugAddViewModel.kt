package org.leondorus.remill.ui.screens.drug

import android.app.Application
import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.leondorus.remill.R
import org.leondorus.remill.RemillApplication
import org.leondorus.remill.domain.drugs.DrugEditUseCase
import org.leondorus.remill.domain.model.NotifType
import org.leondorus.remill.domain.model.NotifTypes
import org.leondorus.remill.domain.model.Schedule
import org.leondorus.remill.domain.model.UsePattern
import org.leondorus.remill.domain.notifgroups.NotifGroupEditUseCase
import java.io.File
import java.time.LocalDateTime
import java.util.UUID

class DrugAddViewModel(
    private val drugEditUseCase: DrugEditUseCase,
    private val notifGroupEditUseCase: NotifGroupEditUseCase,
    application: Application,
) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(DrugAddUiState("", false, "", emptyList()))
    val uiState: StateFlow<DrugAddUiState>
        get() = _uiState.asStateFlow()

    private var photoPath: Uri? = null

    suspend fun saveCurDrug() {
        val drug = drugEditUseCase.addDrug(_uiState.value.drugName)

        val notifTypes = NotifTypes(
            push = NotifType.Push(
                true,
                notificationTitle = "Take ${drug.name}!",
                notificationText = "It's time to take your drug: ${drug.name}",
                notificationIcon = R.drawable.notification_icon
            ),
            audio = NotifType.Audio(false),
            flashlight = NotifType.Flashlight(false),
            blinkingScreen = NotifType.BlinkingScreen(false)
        )
        val usePattern = UsePattern(Schedule(_uiState.value.times), notifTypes)
        val notifGroup =
            notifGroupEditUseCase.addNotifGroup(_uiState.value.notifGroupName, usePattern)

        val newDrug = drug.copy(notifGroupId = notifGroup.id)
        drugEditUseCase.updateDrug(newDrug)
    }

    fun updateDrugName(newName: String) {
        _uiState.update { uiState -> uiState.copy(drugName = newName) }
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

    fun takePhoto() {
    }
}

data class DrugAddUiState(
    val drugName: String,
    val isDialogShown: Boolean,
    val notifGroupName: String,
    val times: List<LocalDateTime>,
)