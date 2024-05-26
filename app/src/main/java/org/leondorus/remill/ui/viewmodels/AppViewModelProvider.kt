package org.leondorus.remill.ui.viewmodels

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import org.leondorus.remill.RemillApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            DrugViewModel(
                remillApplication().container.drugGetUseCase,
                remillApplication().container.drugEditUseCase
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.remillApplication(): RemillApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RemillApplication)
