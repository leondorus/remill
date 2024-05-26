package org.leondorus.remill.ui.screens.drug

import org.leondorus.remill.R
import org.leondorus.remill.ui.navigation.ItemNavigationDestination

object DrugEditDestination: ItemNavigationDestination {
    override val route: String = "drug_edit"
    override val itemIdArg: String = "drug_id"
    override val titleRes: Int = R.string.edit_drug
}