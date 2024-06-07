package org.leondorus.remill.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import org.leondorus.remill.ui.screens.dayplan.DayPlanDestination
import org.leondorus.remill.ui.screens.drugs.DrugsDestination
import org.leondorus.remill.ui.screens.monthplan.MonthPlanDestination
import org.leondorus.remill.ui.screens.settings.SettingsDestination

val DESTINATIONS: List<IconNavigationDestination> = listOf(
    DayPlanDestination, MonthPlanDestination, DrugsDestination, SettingsDestination
)

@Composable
fun RemillNavBar(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        DESTINATIONS.forEach { destination ->
            NavigationBarItem(
                icon = {
                    Icon(
                        ImageVector.vectorResource(destination.icon),
                        contentDescription = stringResource(destination.titleRes)
                    )
                },
                label = { Text(stringResource(destination.titleRes)) },
                selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true,
                onClick = {
                    navController.navigate(destination.route) {
                        // Code from documentation: https://developer.android.com/develop/ui/compose/navigation
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}