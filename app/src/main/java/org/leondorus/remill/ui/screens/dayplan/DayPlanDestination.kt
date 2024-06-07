package org.leondorus.remill.ui.screens.dayplan

import org.leondorus.remill.R
import org.leondorus.remill.ui.navigation.IconNavigationDestination

object DayPlanDestination : IconNavigationDestination {
    override val route: String = "/day_plan"
    override val titleRes: Int = R.string.day_plan_title
    override val icon: Int = R.drawable.view_day
}