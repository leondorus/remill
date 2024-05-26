package org.leondorus.remill.ui.screens.drugs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.leondorus.remill.domain.model.Drug
import org.leondorus.remill.domain.model.DrugId
import org.leondorus.remill.ui.AppViewModelProvider
import org.leondorus.remill.ui.screens.drug.DrugAddDestination

@Composable
fun DrugsScreen(
    navigateToItemInfo: (DrugId) -> Unit,
    navigateToAddNewDrug: () -> Unit,
    modifier: Modifier = Modifier,
    drugsViewModel: DrugsViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val drugsUiState: DrugsUiState by drugsViewModel.drugsUiState.collectAsState()

    Scaffold(modifier = modifier, floatingActionButton = {
        FloatingActionButton(
            onClick = navigateToAddNewDrug,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(DrugAddDestination.titleRes)
            )
        }
    }) { innerPaddings ->
        LazyColumn(
            modifier = modifier
                .padding(innerPaddings)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(items = drugsUiState.drugs, key = { it.id }) { drug ->
                DrugCard(
                    drug = drug,
                    navigateToItemInfo = navigateToItemInfo,
                    modifier = Modifier.fillMaxWidth().padding(4.dp)
                )
            }
        }
    }
}

@Composable
fun DrugCard(drug: Drug, navigateToItemInfo: (DrugId) -> Unit, modifier: Modifier = Modifier) {
    Card(modifier = modifier.clickable { navigateToItemInfo(drug.id) }) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = drug.name, style = MaterialTheme.typography.bodyMedium)
        }
    }
}