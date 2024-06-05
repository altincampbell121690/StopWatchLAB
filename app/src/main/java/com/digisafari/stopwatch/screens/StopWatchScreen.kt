package com.digisafari.stopwatch.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.digisafari.stopwatch.services.CustomNotificationService

@Composable
fun StopWatch(
    notificationService: CustomNotificationService,
    modifier: Modifier = Modifier,
    viewModel: StopWatchViewModel = viewModel()
) {

    val stopWatchUIState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stopWatchUIState.time, fontSize = 45.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            if (! stopWatchUIState.bTimerStarted) {
                Button(onClick = {
                    viewModel.startTimer()
                    notificationService.stopWatchStatus(
                        "has " + when (stopWatchUIState.bPaused) {
                            true -> "Resumed"
                            false -> "Started"
                        }
                    )
                }) {
                    Text(
                        when (stopWatchUIState.bPaused) {
                            true -> "Resume"
                            false -> "Start"
                        }
                    )
                }
                if (stopWatchUIState.bPaused) {
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = {
                        viewModel.stopTimer()
                        notificationService.stopWatchStatus("has been stopped")
                    }) {
                        Text("Stop")
                    }
                }
            } else {
                Button(onClick = {
                    viewModel.pauseTimer()
                    notificationService.stopWatchStatus("has been paused")
                }) {
                    Text("Pause")
                }
            }
        }
    }
}