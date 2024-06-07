package org.leondorus.remill.ui.screens.sharing

import org.leondorus.remill.R
import org.leondorus.remill.ui.navigation.ItemNavigationDestination

object SharingSendDestination: ItemNavigationDestination {
    override val route: String = "share_send_drug"
    override val itemIdArg: String = "drug_id"
    override val titleRes: Int = R.string.share_send_drug
}
