package com.digisafari.stopwatch.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StopWatchViewModel : ViewModel() {
    private var secondsElapsed = 0
    private var timerJob: Job? = null

    private val _uiState = MutableStateFlow(StopWatchUIState())
    val uiState: StateFlow<StopWatchUIState> = _uiState.asStateFlow()

    fun startTimer() {
        if (!_uiState.value.bTimerStarted) {
            _uiState.update { currentState ->
                currentState.copy(
                    bTimerStarted = true, bPaused = false
                )
            }

            timerJob = viewModelScope.launch {
                while (_uiState.value.bTimerStarted) {
                    delay( 1000)
                    secondsElapsed++
                    _uiState.update { currentState ->
                        currentState.copy(
                            time = formatTime(secondsElapsed)
                        )
                    }
                }
            }
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
        timerJob = null

        _uiState.update { currentState ->
            currentState.copy(
                bTimerStarted = false, bPaused = true, time = formatTime(secondsElapsed)
            )
        }
    }

    fun stopTimer() {
        secondsElapsed = 0

        _uiState.update { currentState ->
            currentState.copy(
                bTimerStarted = false, bPaused = false, time = formatTime(secondsElapsed)
            )
        }
    }

    private fun formatTime(totalSeconds: Int): String {
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

}