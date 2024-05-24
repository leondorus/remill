package org.leondorus.remill.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.leondorus.remill.ui.screens.drug.DrugAddDestination
import org.leondorus.remill.ui.screens.drug.DrugAddScreen
import org.leondorus.remill.ui.screens.drug.DrugInfoDestination
import org.leondorus.remill.ui.screens.drugs.DrugsDestination
import org.leondorus.remill.ui.screens.drugs.DrugsScreen

@Composable
fun RemillNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = DrugsDestination.route, //TODO(change this to dayplan)
        modifier = modifier,
    ) {
        composable(DrugsDestination.route) {
            DrugsScreen(navigateToItemInfo = { id -> navController.navigate("${DrugInfoDestination.route}/$id") },
                navigateToAddNewDrug = { navController.navigate(DrugAddDestination.route) })
        }
        composable(DrugAddDestination.route) {
            DrugAddScreen(goBack = { navController.popBackStack() })
        }
    }
}
