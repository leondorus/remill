package org.leondorus.remill.ui.screens.sharing

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.leondorus.remill.bluetooth.AndroidBluetoothWrapper
import org.leondorus.remill.domain.model.DrugId
import org.leondorus.remill.domain.sharing.FullDrugInfoUseCase

class SharingSendViewModel(
    savedStateHandle: SavedStateHandle,
    private val androidBluetoothWrapper: AndroidBluetoothWrapper?,
    private val fullDrugInfoUseCase: FullDrugInfoUseCase,
) : ViewModel() {
    private val lock = Any()
    val drugId: DrugId = DrugId(checkNotNull(savedStateHandle[SharingSendDestination.itemIdArg]))

    private val _uiState = MutableStateFlow<SharingSendUiState>(SharingSendUiState.Initial)
    val uiState: StateFlow<SharingSendUiState>
        get() = _uiState.asStateFlow()

    val devices: StateFlow<List<BluetoothDevice>> = androidBluetoothWrapper?.getAvailableFriends() ?: MutableStateFlow(
        emptyList()
    )

    fun startSending(bluetoothDevice: BluetoothDevice) {
        viewModelScope.launch {
            if (androidBluetoothWrapper == null)
                return@launch
            syncUpdateStateFlow(SharingSendUiState.Sending)
            val fullDrug = fullDrugInfoUseCase.getFullDrugInfo(drugId).first()
            if (fullDrug == null) {
                syncUpdateStateFlow(SharingSendUiState.Done)
                return@launch
            }
            androidBluetoothWrapper.sendToBluetoothDevice(bluetoothDevice, fullDrug) {
                syncUpdateStateFlow(SharingSendUiState.Done)
            }
        }
    }

    private fun syncUpdateStateFlow(newState: SharingSendUiState) {
        synchronized(lock) {
            _uiState.value = newState
        }
    }

}

sealed interface SharingSendUiState {
    object Initial: SharingSendUiState
    object Sending: SharingSendUiState
    object Done: SharingSendUiState
}