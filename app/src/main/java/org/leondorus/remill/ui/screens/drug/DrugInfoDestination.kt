package org.leondorus.remill.ui.screens.drug

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector
import org.leondorus.remill.R
import org.leondorus.remill.ui.navigation.NavigationDestination

object DrugInfoDestination: NavigationDestination {
    override val route: String = "drug_info"
    override val titleRes: Int = R.string.drug_information
    override val icon: ImageVector = Icons.Default.Info
    val drugIdArg = "drugId"
    val routeWithArgs = "$route/$drugIdArg"
}