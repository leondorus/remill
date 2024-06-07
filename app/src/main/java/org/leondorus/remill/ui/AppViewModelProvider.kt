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
import org.leondorus.remill.ui.screens.settings.SettingsViewModel
import org.leondorus.remill.ui.screens.sharing.SharingReceiveViewModel
import org.leondorus.remill.ui.screens.sharing.SharingSendViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            DrugsViewModel(
                remillApplication().container.drugGetUseCase,
                remillApplication().container.drugEditUseCase,
                remillApplication().container.notifGroupEditUseCase
            )
        }
        initializer {
            DrugInfoViewModel(
                this.createSavedStateHandle(),
                remillApplication().container.drugGetUseCase,
                remillApplication().container.notifGroupGetUseCase
            )
        }
        initializer {
            DrugAddViewModel(
                remillApplication().container.drugEditUseCase,
                remillApplication().container.notifGroupEditUseCase
            )
        }
        initializer {
            DrugEditViewModel(
                this.createSavedStateHandle(),
                remillApplication().container.drugGetUseCase,
                remillApplication().container.drugEditUseCase,
                remillApplication().container.notifGroupGetUseCase,
                remillApplication().container.notifGroupEditUseCase,
            )
        }

        initializer {
            SettingsViewModel(remillApplication().container.permissionManager)
        }

        initializer {
            SharingSendViewModel(this.createSavedStateHandle(),
                remillApplication().container.androidBluetoothWrapper,
                remillApplication().container.fullDrugIntoUseCase
            )
        }
        initializer {
            SharingReceiveViewModel(
                remillApplication().container.androidBluetoothWrapper,
                remillApplication().container.fullDrugIntoUseCase
            )
        }
    }
}

fun CreationExtras.remillApplication(): RemillApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RemillApplication)
