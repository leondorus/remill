package org.leondorus.remill.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import org.leondorus.remill.R

enum class BottomNavItem(val route: String, val icon: ImageVector, val labelRes: Int) {
    Plan("plan", Icons.Default.Check, R.string.plan),
    Calendar("calendar", Icons.Default.DateRange, R.string.calendar),
    Drugs("drugs", Icons.Default.Menu, R.string.drugs)
}
