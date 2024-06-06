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

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel1: NotificationChannel =
                NotificationChannel("A", "Stop Watch Status", IMPORTANCE_HIGH)
            channel1.description = "Notification for Status of the Stop Watch(Running, Paused, Stopped)"
            val channel2: NotificationChannel =
                NotificationChannel("B", "Background Status", IMPORTANCE_DEFAULT)

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel1)
            manager.createNotificationChannel(channel2)
        }


    }
}