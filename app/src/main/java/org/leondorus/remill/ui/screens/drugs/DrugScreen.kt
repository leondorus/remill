package org.leondorus.remill.ui.screens.drugs

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import org.leondorus.remill.ui.AppViewModelProvider

@Composable
fun DrugScreen(
    modifier: Modifier = Modifier,
    viewModel: DrugViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    ),
) {
    Column(modifier = modifier) {
        Text("Android is shit")
    }
}