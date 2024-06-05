package org.leondorus.remill

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.leondorus.remill.ui.RemillApp
import org.leondorus.remill.ui.screens.drug.DrugInfoDestination
import org.leondorus.remill.ui.theme.RemillTheme

class MainActivity : ComponentActivity(), PermissionProvider {
    private val requestPermissionLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {}
    private lateinit var activityPermissionManager: ActivityPermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activityPermissionManager =
            (application as RemillApplication).container.permissionManager as ActivityPermissionManager

        setContent {
            RemillTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    RemillApp()
                }
            }
        }

        Log.d(null, DrugInfoDestination.routeWithArgs)
    }

    override fun requestPermissions(permissions: Array<String>) {
        requestPermissionLauncher.launch(permissions)
    }

    override fun onStart() {
        super.onStart()
        activityPermissionManager.attachPermissionProvider(this)
    }

    override fun onStop() {
        super.onStop()
        activityPermissionManager.detachPermissionProvider(this)
    }
}