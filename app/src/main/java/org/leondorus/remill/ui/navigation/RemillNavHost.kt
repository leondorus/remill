package org.leondorus.remill.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.leondorus.remill.R
import org.leondorus.remill.ui.screens.CalendarScreen
import org.leondorus.remill.ui.screens.DayPlanScreen
import org.leondorus.remill.ui.screens.DrugsScreen

@Composable
fun RemillNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Drugs.route,
        modifier = modifier
    ) {
        composable(route = BottomNavItem.Plan.route) {
            DayPlanScreen()
        }
        composable(route = BottomNavItem.Calendar.route) {
            CalendarScreen()
        }
        composable(route = BottomNavItem.Drugs.route) {
            Scaffold(bottomBar = {
                BottomNavBar(
                    navController = navController
                )
            }, floatingActionButton = {
                FloatingActionButton(
                    onClick = {},
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                }
            }) { innerPadding ->
                DrugsScreen(modifier = Modifier.padding(innerPadding))
            }
        }

    }
}