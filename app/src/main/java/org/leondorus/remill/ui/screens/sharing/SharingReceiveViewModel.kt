package org.leondorus.remill.ui.screens.sharing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.leondorus.remill.bluetooth.AndroidBluetoothWrapper
import org.leondorus.remill.domain.sharing.FullDrugInfo
import org.leondorus.remill.domain.sharing.FullDrugInfoUseCase

class SharingReceiveViewModel(
    private val bluetoothWrapper: AndroidBluetoothWrapper?,
    private val fullDrugInfoUseCase: FullDrugInfoUseCase,
) : ViewModel() {
    private val lock = Any()
    private val _uiState = MutableStateFlow<SharingReceiveUiState>(SharingReceiveUiState.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        if (bluetoothWrapper == null) {
            syncUpdateStateFlow(SharingReceiveUiState.NoBluetoothAvailable)
        } else {
            viewModelScope.launch {
                _uiState.collect { uiState ->
                    if (uiState is SharingReceiveUiState.UpdatingLocalDatabase) {
                        val fullDrugInfo = uiState.fullDrugInfo
                        if (fullDrugInfo != null) fullDrugInfoUseCase.addFullDrug(fullDrugInfo)
                        syncUpdateStateFlow(SharingReceiveUiState.Done)
                    }
                }
            }
        }
    }

    fun startReceiving() {
        if (bluetoothWrapper == null)
            return
        if (!bluetoothWrapper.bluetoothEnabled) {
            bluetoothWrapper.tryEnablingBluetooth()
            return
        }
        syncUpdateStateFlow(SharingReceiveUiState.Connecting)
        bluetoothWrapper.startReceiving { fullDrugInfo ->
            syncUpdateStateFlow(SharingReceiveUiState.UpdatingLocalDatabase(fullDrugInfo))
        }
    }

    private fun syncUpdateStateFlow(newState: SharingReceiveUiState) {
        synchronized(lock) {
            _uiState.value = newState
        }
    }

    fun makeDiscoverable() {
        bluetoothWrapper?.makeDeviceDiscoverable()
    }

}

sealed interface SharingReceiveUiState {
    data object Initial : SharingReceiveUiState
    data object NoBluetoothAvailable : SharingReceiveUiState
    data object Connecting : SharingReceiveUiState
    data object Receiving : SharingReceiveUiState
    data class UpdatingLocalDatabase(val fullDrugInfo: FullDrugInfo?) : SharingReceiveUiState
    data object Done : SharingReceiveUiState
}
