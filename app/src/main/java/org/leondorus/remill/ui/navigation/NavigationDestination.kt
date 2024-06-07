package org.leondorus.remill.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

interface NavigationDestination {
    val route: String
    val titleRes: Int
}

interface IconNavigationDestination: NavigationDestination {
    val icon: Int
}

interface ItemNavigationDestination: NavigationDestination {
    val itemIdArg: String
    val routeWithArgs
        get() = "$route/{$itemIdArg}"
}


