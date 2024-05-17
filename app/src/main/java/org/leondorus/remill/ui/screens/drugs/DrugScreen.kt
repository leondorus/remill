package org.leondorus.remill.ui.screens.drugs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import org.leondorus.remill.domain.model.Drug
import org.leondorus.remill.ui.AppViewModelProvider

@Composable
fun DrugScreen(modifier: Modifier = Modifier, drugViewModel: DrugViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val drugUiState: DrugUiState by drugViewModel.drugUiState.collectAsState()

    LazyColumn(modifier = modifier) {
        items(items = drugUiState.drugs, key = { it.id }) {drug ->
            DrugCard(drug = drug)
        }
    }
}

@Composable
fun DrugCard(drug: Drug) {
    Card {
        Column {
            Text(text = drug.name, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
