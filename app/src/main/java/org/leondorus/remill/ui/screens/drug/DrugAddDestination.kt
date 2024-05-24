package org.leondorus.remill.ui.screens.drug

import org.leondorus.remill.R
import org.leondorus.remill.ui.navigation.NavigationDestination

object DrugAddDestination: NavigationDestination {
    override val route: String = "drug_add"
    override val titleRes: Int = R.string.add_drug_title
}