package org.leondorus.remill.ui.screens.drug

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.leondorus.remill.R
import org.leondorus.remill.ui.AppViewModelProvider

@Composable
fun DrugAddScreen(
    goBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DrugAddViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = AppViewModelProvider.Factory
    ),
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    DrugAddBody(
        onSaveButtonClick = {
            coroutineScope.launch {
                viewModel.saveCurDrug()
                goBack()
            }
        },
        drugName = uiState.name,
        onDrugNameUpdate = { viewModel.updateDrugName(it) },
        modifier = modifier
    )
}

@Composable
fun DrugAddBody(
    onSaveButtonClick: () -> Unit,
    drugName: String,
    onDrugNameUpdate: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(value = drugName,
            onValueChange = onDrugNameUpdate,
            label = { Text(stringResource(R.string.name)) })
        Button(onSaveButtonClick) {
            Text(text = stringResource(R.string.save))
        }
    }
}