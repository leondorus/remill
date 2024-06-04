package org.leondorus.remill.ui.screens.drug

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import org.leondorus.remill.domain.model.DrugId
import org.leondorus.remill.ui.AppViewModelProvider
import java.time.LocalDateTime

@Composable
fun DrugInfoScreen(
    navigateToEditDrug: (DrugId) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DrugInfoViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val drugInfoUiState by viewModel.uiState.collectAsState()

    Scaffold(modifier = modifier, floatingActionButton = {
        FloatingActionButton(
            onClick = { navigateToEditDrug(viewModel.drugId) },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = stringResource(DrugEditDestination.titleRes),
            )
        }
    }) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = stringResource(R.string.name_is_druginfo, drugInfoUiState.name))

            NotifGroupViewWidget(notifGroupName = drugInfoUiState.notifGroupName, times = drugInfoUiState.times)
        }
    }
}

@Composable
fun NotifGroupViewWidget(notifGroupName: String, times: List<LocalDateTime>, modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(8.dp)) {
        Column {
            Text("Notif group name: %s".format(notifGroupName))
            Spacer(modifier = Modifier.height(12.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                items(times) {
                    Text(it.toString())
                }
            }
        }
    }
}
