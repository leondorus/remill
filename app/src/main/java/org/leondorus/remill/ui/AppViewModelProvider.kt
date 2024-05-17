package org.leondorus.remill.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import org.leondorus.remill.RemillApplication
import org.leondorus.remill.ui.screens.drugs.DrugViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            val application = this[APPLICATION_KEY] as RemillApplication
            val drugGetUseCase = application.container.drugGetUseCase
            val drugEditUseCase = application.container.drugEditUseCase
            DrugViewModel(drugGetUseCase, drugEditUseCase)
        }
    }
}