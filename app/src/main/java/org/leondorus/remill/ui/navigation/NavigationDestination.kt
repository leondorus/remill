package org.leondorus.remill.ui.navigation

interface NavigationDestination {
    val route: String
    val titleRes: Int
}

interface IconNavigationDestination : NavigationDestination {
    val icon: Int
}

interface ItemNavigationDestination : NavigationDestination {
    val itemIdArg: String
    val routeWithArgs
        get() = "$route/{$itemIdArg}"
}


