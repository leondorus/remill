package org.leondorus.remill.ui.screens.monthplan

import org.leondorus.remill.R
import org.leondorus.remill.ui.navigation.IconNavigationDestination

object MonthPlanDestination: IconNavigationDestination {
    override val route: String = "/month_plan"
    override val titleRes: Int = R.string.month_plan_title
    override val icon: Int = R.drawable.month_plan
}