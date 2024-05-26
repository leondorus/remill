package org.leondorus.remill.ui

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import org.leondorus.remill.ui.navigation.BottomNavBar
import org.leondorus.remill.ui.navigation.RemillNavHost
import org.leondorus.remill.ui.theme.RemillTheme

@Composable
fun RemillApp() {
    RemillTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(), color = colorScheme.background
        ) {
            val navController = rememberNavController()
            RemillNavHost(navController = navController)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RemillAppPreview() {
    RemillApp()
}
