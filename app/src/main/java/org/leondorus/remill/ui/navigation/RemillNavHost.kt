package org.leondorus.remill.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.leondorus.remill.ui.screens.dayplan.DayPlanDestination
import org.leondorus.remill.ui.screens.dayplan.DayPlanScreen
import org.leondorus.remill.ui.screens.drug.DrugAddDestination
import org.leondorus.remill.ui.screens.drug.DrugAddScreen
import org.leondorus.remill.ui.screens.drug.DrugEditDestination
import org.leondorus.remill.ui.screens.drug.DrugEditScreen
import org.leondorus.remill.ui.screens.drug.DrugInfoDestination
import org.leondorus.remill.ui.screens.drug.DrugInfoScreen
import org.leondorus.remill.ui.screens.drugs.DrugsDestination
import org.leondorus.remill.ui.screens.drugs.DrugsScreen
import org.leondorus.remill.ui.screens.monthplan.MonthPlanDestination
import org.leondorus.remill.ui.screens.monthplan.MonthPlanScreen
import org.leondorus.remill.ui.screens.settings.SettingsDestination
import org.leondorus.remill.ui.screens.settings.SettingsScreen
import org.leondorus.remill.ui.screens.sharing.SharingReceiveDestination
import org.leondorus.remill.ui.screens.sharing.SharingReceiveScreen
import org.leondorus.remill.ui.screens.sharing.SharingSendDestination
import org.leondorus.remill.ui.screens.sharing.SharingSendScreen

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
            DrugsScreen(
                navigateToItemInfo = { id ->
                    navController.navigate("${DrugInfoDestination.route}/${id.id}")
                },
                navigateToAddingDrugManually = { navController.navigate(DrugAddDestination.route) },
                navigateToAddDrugViaBluetooth = { navController.navigate(SharingReceiveDestination.route) },
                navigateToShare = { id ->
                    navController.navigate("${SharingSendDestination.route}/${id.id}")
                },
            )
        }
        composable(DrugAddDestination.route) {
            DrugAddScreen(goBack = { navController.popBackStack() })
        }
        composable(
            route = DrugInfoDestination.routeWithArgs,
            arguments = listOf(navArgument(DrugInfoDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            DrugInfoScreen({ id ->
                navController.navigate("${DrugEditDestination.route}/${id.id}")
            })
        }
        composable(
            route = DrugEditDestination.routeWithArgs,
            arguments = listOf(navArgument(DrugEditDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            DrugEditScreen({ navController.popBackStack() })
        }

        composable(
            route = SettingsDestination.route
        ) {
            SettingsScreen()
        }

        composable(
            route = SharingSendDestination.routeWithArgs,
            arguments = listOf(navArgument(SharingSendDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            SharingSendScreen({ navController.popBackStack() })
        }
        composable(
            route = SharingReceiveDestination.route
        ) {
            SharingReceiveScreen()
        }

        composable(
            route = DayPlanDestination.route
        ) {
            DayPlanScreen()
        }
        composable(
            route = MonthPlanDestination.route
        ) {
            MonthPlanScreen()
        }
    }
}
