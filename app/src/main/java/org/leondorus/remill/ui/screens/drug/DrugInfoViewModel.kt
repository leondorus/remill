package org.leondorus.remill.ui.screens.drug

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class DrugInfoViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {

}

data class DrugInfoUiState(val name: String)