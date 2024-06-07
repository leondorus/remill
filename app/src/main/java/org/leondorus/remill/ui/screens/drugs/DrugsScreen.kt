package org.leondorus.remill.ui.screens.drugs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import org.leondorus.remill.R
import org.leondorus.remill.domain.model.Drug
import org.leondorus.remill.domain.model.DrugId
import org.leondorus.remill.ui.AppViewModelProvider
import org.leondorus.remill.ui.screens.drug.DrugAddDestination

@Composable
fun DrugsScreen(
    navigateToItemInfo: (DrugId) -> Unit,
    navigateToShare: (DrugId) -> Unit,
    navigateToAddingDrugManually: () -> Unit,
    navigateToAddDrugViaBluetooth: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DrugsViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val drugs by viewModel.drugs.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    if (uiState.isChoosingAddMethod) {
        ChooseAddMethodDialog(onManualAdd = { navigateToAddingDrugManually() },
            onBluetoothAdd = { navigateToAddDrugViaBluetooth() },
            onDismiss = { viewModel.dismissAddDialog() })
    }

    Scaffold(modifier = modifier, floatingActionButton = {
        FloatingActionButton(
            onClick = {
                viewModel.showAddDialog()
            }, shape = MaterialTheme.shapes.medium, modifier = Modifier
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
            items(items = drugs, key = { it.id }) { drug ->
                DrugCard(
                    drug = drug,
                    navigateToItemInfo = navigateToItemInfo,
                    deleteDrug = {
                        coroutineScope.launch {
                            viewModel.deleteDrug(it)
                        }
                    },
                    navigateToShare = navigateToShare,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )
            }
        }
    }
}

@Composable
fun DrugCard(
    drug: Drug,
    navigateToItemInfo: (DrugId) -> Unit,
    navigateToShare: (DrugId) -> Unit,
    deleteDrug: (Drug) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Drug name: %s".format(drug.name),
                style = MaterialTheme.typography.headlineMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DrugCardButton(
                    icon = Icons.Default.Share,
                    text = stringResource(R.string.share_this_drug),
                    onClick = { navigateToShare(drug.id) },
                    modifier = Modifier.weight(1f)
                )
                DrugCardButton(
                    icon = Icons.Default.Info,
                    text = stringResource(R.string.drug_info),
                    onClick = { navigateToItemInfo(drug.id) },
                    modifier = Modifier.weight(1f)
                )
                DrugCardButton(
                    icon = Icons.Default.Delete,
                    text = stringResource(id = R.string.delete_this_drug),
                    onClick = { deleteDrug(drug) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun DrugCardButton(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(imageVector = icon, contentDescription = text)
        Text(text = text)
    }
}

@Composable
fun ChooseAddMethodDialog(
    onManualAdd: () -> Unit,
    onBluetoothAdd: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(
        onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            AddMethodOption(
                icon = Icons.Default.Add,
                text = stringResource(R.string.add_manually),
                onClick = onManualAdd
            )
            AddMethodOption(
                icon = ImageVector.vectorResource(R.drawable.bluetooth_icon), text = stringResource(
                    R.string.download_from_another_device
                ), onClick = onBluetoothAdd
            )
        }
    }
}

@Composable
fun AddMethodOption(
    onClick: () -> Unit,
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier.clickable { onClick() }.padding(8.dp)) {
        Row {
            Icon(icon, contentDescription = text)
            Spacer(modifier = Modifier.padding(12.dp))
            Text(text = text)
        }
    }
}