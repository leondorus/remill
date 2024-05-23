package org.leondorus.remill.ui.screens.drugs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import org.leondorus.remill.R
import org.leondorus.remill.ui.navigation.NavigationDestination

object DrugsDestination: NavigationDestination {
    override val route: String = "/drugs"
    override val titleRes: Int = R.string.drugs_titile
    override val icon: ImageVector = Icons.AutoMirrored.Default.List
}