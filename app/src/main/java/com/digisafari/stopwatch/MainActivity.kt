package com.digisafari.stopwatch

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.digisafari.stopwatch.components.CustomDialogBox
import com.digisafari.stopwatch.screens.StopWatch
import com.digisafari.stopwatch.services.CustomNotificationService
import com.digisafari.stopwatch.ui.theme.StopWatchTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private fun isNotificationPermissionAlreadyGranted(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        }
        return true
    }

    private fun openApplicationSettings() {
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        ).also(::startActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val notificationService = CustomNotificationService(applicationContext)

        setContent {

            // --- Notification Permission Code Starts ---


            var bNotificationPermissionGranted by remember {
                mutableStateOf(
                    isNotificationPermissionAlreadyGranted()
                )
            }

            var bShowPermissionRationale by remember {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    mutableStateOf(
                        shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
                    )
                } else {
                    mutableStateOf(false)
                }
            }

            var bShowApplicationSettings by remember {
                mutableStateOf(false)
            }

            val notificationPermissionLauncher =
                rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->

                    bNotificationPermissionGranted = isGranted

                    if (!bNotificationPermissionGranted) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            bShowPermissionRationale =
                                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    }


                }

            bShowApplicationSettings = !bShowPermissionRationale && !bNotificationPermissionGranted

            // --- Notification Permission Code Ends ---


            // TODO: 14. Use DisposableEffect and create a LifeCycleEventObserver. For the different lifecycle state, either launch permissions, or notifications or cancel the notifications.

            // TODO: 5. Create the scope and snackBarHostState for the Snackbar
            val scope = rememberCoroutineScope()
            val snackBarHostState = remember { SnackbarHostState() }

            // TODO: 2. Alert Dialog visibility variable
            val showDialog = remember { mutableStateOf(false) }

            StopWatchTheme {
                Scaffold(
                    snackbarHost = { SnackbarHost(hostState = snackBarHostState) }// TODO: 6. Assign the SnackbarHost in the scaffold.
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(modifier = Modifier.weight(1F)) {
                            StopWatch(notificationService)
                        }
                        if (bShowApplicationSettings) {
                            Button(
                                onClick = {
                                    showDialog.value = true
                                }, // TODO: 3. Alert Dialog variable change
                                modifier = Modifier.padding(vertical = 16.dp)
                            ) {

                                // TODO: 4. Alert Dialog working
                                if (showDialog.value) {
                                    CustomDialogBox(
                                        openApplicationSettings = { openApplicationSettings() },
                                        onCommonClick = { showDialog.value = false },
                                    )
                                    Text("Allow Notifications")
                                }
                            }
                        }
                        if (bShowPermissionRationale) {
                            LaunchedEffect(Unit) {
                                // TODO: 7. Launch the Snackbar and perform actions based on: a) Approve button b) Dismiss button
                                scope.launch {
                                    val result = snackBarHostState.showSnackbar(
                                        message = "Please Allow Notifications",
                                        actionLabel = "Approve",
                                        duration = SnackbarDuration.Indefinite
                                    )
                                    when (result) {
                                        SnackbarResult.Dismissed -> {
                                            bShowPermissionRationale = false
                                        }

                                        SnackbarResult.ActionPerformed -> {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
