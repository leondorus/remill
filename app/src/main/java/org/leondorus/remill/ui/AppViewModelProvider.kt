package org.leondorus.remill.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import org.leondorus.remill.RemillApplication
import org.leondorus.remill.ui.screens.drugs.DrugViewModel

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

fun CreationExtras.remillApplication(): RemillApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RemillApplication)
