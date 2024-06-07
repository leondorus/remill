package org.leondorus.remill.ui.screens.drug

import org.leondorus.remill.R
import org.leondorus.remill.ui.navigation.ItemNavigationDestination

object DrugInfoDestination : ItemNavigationDestination {
    override val route: String = "drug_info"
    override val titleRes: Int = R.string.drug_information
    override val itemIdArg = "drug_id"
}