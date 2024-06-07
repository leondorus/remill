package org.leondorus.remill.ui.screens.drugs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.leondorus.remill.domain.drugs.DrugEditUseCase
import org.leondorus.remill.domain.drugs.DrugGetUseCase
import org.leondorus.remill.domain.model.Drug
import org.leondorus.remill.domain.notifgroups.NotifGroupEditUseCase

class DrugsViewModel(
    drugGetUseCase: DrugGetUseCase,
    private val drugEditUseCase: DrugEditUseCase,
    private val notifGroupEditUseCase: NotifGroupEditUseCase,
) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    private val _uiState = MutableStateFlow(
        DrugsUiState(
            isChoosingAddMethod = false,
            isShowingFilter = false,
            searchQuery = "",
            sortType = DrugsSortType.NoSort
        )
    )
    val uiState = _uiState.asStateFlow()

    val drugs: StateFlow<List<Drug>> =
        drugGetUseCase.getAllDrugs().combine(_uiState) { drugs, uiState ->
            val searchQuery = uiState.searchQuery
            val filteredDrugs = drugs.filter { drug ->
                var result = true
                if (_uiState.value.searchQuery != "") {
                    result = drug.name.lowercase().contains(searchQuery.lowercase())
                }
                result
            }
            val sortedDrugs: List<Drug> = when (uiState.sortType) {
                DrugsSortType.NameAsc -> filteredDrugs.sortedBy { it.name }
                DrugsSortType.NameDesc -> filteredDrugs.sortedByDescending { it.name }
                DrugsSortType.NoSort -> filteredDrugs
            }
            sortedDrugs
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = emptyList()
        )

    suspend fun deleteDrug(drug: Drug) {
        if (drug.notifGroupId != null) {
            val freeDrug = drug.copy(notifGroupId = null)
            drugEditUseCase.updateDrug(freeDrug)
            notifGroupEditUseCase.deleteNotifGroup(drug.notifGroupId)
        }
        drugEditUseCase.deleteDrug(drug.id)
    }

    fun updateSearchQuery(newQuery: String) {
        _uiState.update { it.copy(searchQuery = newQuery) }
    }

    fun showAddDialog() {
        _uiState.update { it.copy(isChoosingAddMethod = true) }
    }

    fun dismissAddDialog() {
        _uiState.update { it.copy(isChoosingAddMethod = false) }
    }

    fun toggleFilter() {
        _uiState.update { it.copy(isShowingFilter = !it.isShowingFilter) }
    }

    fun updateSort(newSortType: DrugsSortType) {
        _uiState.update { it.copy(sortType = newSortType) }
    }
}

data class DrugsUiState(
    val isChoosingAddMethod: Boolean,
    val isShowingFilter: Boolean,
    val searchQuery: String,
    val sortType: DrugsSortType,
)

sealed interface DrugsSortType {
    val title: String

    data object NoSort : DrugsSortType {
        override val title: String = "No sort"
    }

    data object NameAsc : DrugsSortType {
        override val title: String = "Name ASC"
    }

    data object NameDesc : DrugsSortType {
        override val title: String = "Name DESC"
    }

    companion object {
        val DrugsSortTypes =
            listOf(NoSort, NameAsc, NameDesc)
    }
}

