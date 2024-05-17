package org.leondorus.remill.ui.screens.drugs

import androidx.lifecycle.ViewModel
import org.leondorus.remill.domain.drugs.DrugEditUseCase
import org.leondorus.remill.domain.drugs.DrugGetUseCase

class DrugViewModel(
    private val drugGetUseCase: DrugGetUseCase,
    private val drugEditUseCase: DrugEditUseCase
): ViewModel() {
}