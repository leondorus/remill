package org.leondorus.remill.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import org.leondorus.remill.RemillApplication
import org.leondorus.remill.ui.screens.drug.DrugAddViewModel
import org.leondorus.remill.ui.screens.drug.DrugEditViewModel
import org.leondorus.remill.ui.screens.drug.DrugInfoViewModel
import org.leondorus.remill.ui.screens.drugs.DrugsViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            DrugsViewModel(
                remillApplication().container.drugGetUseCase,
            )
        }
        initializer {
            DrugInfoViewModel(
                this.createSavedStateHandle(),
                remillApplication().container.drugGetUseCase
            )
        }
        initializer {
            DrugAddViewModel(
                remillApplication().container.drugEditUseCase
            )
        }
        initializer {
            DrugEditViewModel(
                this.createSavedStateHandle(),
                remillApplication().container.drugGetUseCase,
                remillApplication().container.drugEditUseCase
            )
        }
    }
}

fun CreationExtras.remillApplication(): RemillApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RemillApplication)
