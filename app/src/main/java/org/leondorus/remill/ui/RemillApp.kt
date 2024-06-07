package org.leondorus.remill.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.leondorus.remill.ui.navigation.RemillNavBar
import org.leondorus.remill.ui.navigation.RemillNavHost

@Composable
fun RemillApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            RemillNavBar(navController = navController)
        }
    ) {
        RemillNavHost(navController = navController, modifier = Modifier.padding(it))
    }
}