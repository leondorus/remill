package org.leondorus.remill.ui.screens.dayplan

import android.content.res.Resources
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import org.leondorus.remill.R
import org.leondorus.remill.ui.navigation.IconNavigationDestination

object DayPlanDestination: IconNavigationDestination {
    override val route: String = "/day_plan"
    override val titleRes: Int = R.string.day_plan_title
    override val icon: Int = R.drawable.view_day
}