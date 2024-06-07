package org.leondorus.remill.ui.screens.drug

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.leondorus.remill.domain.drugs.DrugGetUseCase
import org.leondorus.remill.domain.model.DrugId
import org.leondorus.remill.domain.notifgroups.NotifGroupGetUseCase
import java.time.LocalDateTime

class DrugInfoViewModel(
    savedStateHandle: SavedStateHandle,
    drugGetUseCase: DrugGetUseCase,
    notifGroupGetUseCase: NotifGroupGetUseCase,
) : ViewModel() {
    val drugId: DrugId = DrugId(checkNotNull(savedStateHandle[DrugInfoDestination.itemIdArg]))

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<DrugInfoUiState> =
        drugGetUseCase.getDrug(drugId).filterNotNull().flatMapLatest { drug ->
                val notifGroupId = drug.notifGroupId
                if (notifGroupId != null) {
                    notifGroupGetUseCase.getNotifGroup(notifGroupId).map { notifGroup ->
                        val notifGroupName = notifGroup?.name ?: ""
                        val times = notifGroup?.usePattern?.schedule?.times ?: emptyList()
                        val soundUri = notifGroup?.usePattern?.notifTypes?.audio?.audioUri
                        var proposedSound: ProposedSound = ProposedSound.None
                        for (cur in proposedSounds) {
                            if (cur.uri == soundUri) {
                                proposedSound = cur
                                break
                            }
                        }
                        DrugInfoUiState(drug.name, notifGroupName, drug.photoPath.toString(), times, proposedSound)
                    }
                } else {
                    flow { DrugInfoUiState(drug.name, "", null, emptyList(), ProposedSound.None) }
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = DrugInfoUiState("", "", null, listOf(), ProposedSound.None)
            )


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class DrugInfoUiState(
    val name: String,
    val notifGroupName: String,
    val photoPath: String?,
    val times: List<LocalDateTime>,
    val proposedSound: ProposedSound
)
