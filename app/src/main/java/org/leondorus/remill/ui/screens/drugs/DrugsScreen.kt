package org.leondorus.remill.ui.screens.drugs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.leondorus.remill.R
import org.leondorus.remill.domain.model.Drug
import org.leondorus.remill.ui.AppViewModelProvider

@Composable
fun DrugsScreen(
    navigateToItemInfo: (Int) -> Unit,
    navigateToAddNewDrug: () -> Unit,
    modifier: Modifier = Modifier,
    drugsViewModel: DrugsViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val drugsUiState: DrugsUiState by drugsViewModel.drugsUiState.collectAsState()

    Scaffold(modifier = modifier,
        floatingActionButton = {
            FloatingActionButton (
                onClick = navigateToAddNewDrug,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_new_drug)
                )
            }
        }
        ) { innerPaddings ->
        LazyColumn(modifier = modifier.padding(innerPaddings)) {
            items(items = drugsUiState.drugs, key = { it.id }) { drug ->
                DrugCard(drug = drug, navigateToItemInfo = navigateToItemInfo)
            }
        }
    }
}

@Composable
fun DrugCard(drug: Drug, navigateToItemInfo: (Int) -> Unit, modifier: Modifier = Modifier) {
    Card(modifier = modifier.clickable { navigateToItemInfo(drug.id.id) }) {
        Column {
            Text(text = drug.name, style = MaterialTheme.typography.bodyMedium)
        }
    }
}