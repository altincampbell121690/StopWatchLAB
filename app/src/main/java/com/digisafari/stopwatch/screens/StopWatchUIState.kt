package com.digisafari.stopwatch.screens

data class StopWatchUIState(
    val time: String = "00:00:00",
    val bTimerStarted: Boolean = false,
    val bPaused: Boolean = false
)
