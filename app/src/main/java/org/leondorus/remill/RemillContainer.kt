package org.leondorus.remill

import android.content.Context
import org.leondorus.remill.domain.drugs.DrugEditUseCase
import org.leondorus.remill.domain.drugs.DrugGetUseCase

interface RemillContainer {
    val drugGetUseCase: DrugGetUseCase
    val drugEditUseCase: DrugEditUseCase
}

class AndroidRemillContainer(context: Context): RemillContainer {
    override val drugGetUseCase: DrugGetUseCase
    override val drugEditUseCase: DrugEditUseCase
    init {
        drugGetUseCase = DrugGetUseCase()
        drugEditUseCase = DrugEditUseCase()
    }
}