package com.digisafari.stopwatch.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDialogBox(openApplicationSettings: () -> Unit, onCommonClick: () -> Unit) {
    // TODO: 1. Create Alert Dialog
    AlertDialog(
        onDismissRequest = onCommonClick,
        title = { Text(text = "Open App Settings") },
        text = { Text("This action will take you to the App Settings.") },
        icon = {
               Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "Info"
            )
        },
        confirmButton = {
            TextButton(onClick = openApplicationSettings) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onCommonClick) {
                Text("Cancel")
            }
        }
    )
}


@Preview
@Composable
fun CustomDialogBoxPreview() {
    CustomDialogBox(openApplicationSettings = {}, onCommonClick = {})
}

