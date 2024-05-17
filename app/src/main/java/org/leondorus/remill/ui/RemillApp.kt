package org.leondorus.remill.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.leondorus.remill.ui.screens.drugs.DrugScreen

@Composable
fun RemillApp(modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier) {
        Column(modifier = Modifier.padding(it)) {
            DrugScreen()
        }
    }
}