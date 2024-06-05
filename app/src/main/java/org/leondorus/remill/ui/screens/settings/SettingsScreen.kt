package org.leondorus.remill.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.leondorus.remill.ui.AppViewModelProvider

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    drugsViewModel: SettingsViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    Column(modifier = modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {

    }
}