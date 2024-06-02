package org.leondorus.remill.ui.screens.drug

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import org.leondorus.remill.ui.AppViewModelProvider

@Composable
fun DrugEditScreen(
    goBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DrugEditViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

//    DrugAddBody(
//        onSaveButtonClick = {
//            coroutineScope.launch {
//                viewModel.saveEditedDrug()
//                goBack()
//            }
//        },
//        onCancelButtonClick = goBack,
//        drugName = uiState.name,
//        onDrugNameUpdate = { viewModel.updateDrugName(it) },
//        modifier = modifier
//    )
}