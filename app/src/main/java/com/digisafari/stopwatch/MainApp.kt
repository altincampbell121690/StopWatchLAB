package com.digisafari.stopwatch

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.content.Context
import android.os.Build

// TODO: 8. Create MainApp extending Application class. Create 2 notifications channels
class MainApp() : Application() {
   val channel1: NotificationChannel =
        NotificationChannel("A", "Stop Watch Status", IMPORTANCE_HIGH)
    val channel2: NotificationChannel =
        NotificationChannel("B", "Background Status", IMPORTANCE_DEFAULT)
}