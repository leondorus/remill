package org.leondorus.remill.ui.screens.drugs

import org.leondorus.remill.R
import org.leondorus.remill.ui.navigation.IconNavigationDestination

object DrugsDestination : IconNavigationDestination {
    override val route: String = "/drugs"
    override val titleRes: Int = R.string.drugs_title
    override val icon: Int = R.drawable.list_icon
}