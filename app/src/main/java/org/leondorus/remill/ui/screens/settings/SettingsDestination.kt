package org.leondorus.remill.ui.screens.settings

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import org.leondorus.remill.R
import org.leondorus.remill.ui.navigation.IconNavigationDestination

object SettingsDestination: IconNavigationDestination {
    override val route: String = "/settings"
    override val titleRes: Int = R.string.settings_title
    override val icon: Int = R.drawable.settings_icon
}
