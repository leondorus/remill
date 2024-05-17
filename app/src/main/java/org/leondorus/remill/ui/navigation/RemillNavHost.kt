package org.leondorus.remill.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.leondorus.remill.ui.screens.drugs.DrugDestination
import org.leondorus.remill.ui.screens.drugs.DrugScreen

@Composable
fun RemillNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = DrugDestination.route, //TODO(change this to dayplan)
        modifier = modifier,
    ) {
       composable(DrugDestination.route) {
           DrugScreen()
       }
    }
}
