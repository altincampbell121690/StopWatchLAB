package com.digisafari.stopwatch.services

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.digisafari.stopwatch.R

class CustomNotificationService(
    private val context: Context
) {
    //Note: We need a notification manager to send notifications, in order to do that we need a context
    // and from the context we can retrieve the notification manager by calling getSystemService
    // NOTIFICATION MANAGER is a reference to the system service responsible for managing notifications.
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun stopWatchStatus(status: String) {

        // TODO: 11. Create a notification for the stopwatch status
        val notification = NotificationCompat.Builder(context, "A")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Stopwatch Status")
            .setContentText(status)
            .build()

        notificationManager.notify(1, notification)
    }

    fun showBackgroundNotification() {

        // TODO: 12. Create a notification for the background status, make it permanent
        val notification = NotificationCompat.Builder(context, "B")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Background Status")
            .setContentText("App is in background")
            .setOngoing(true)
            .setSilent(true)
            .build()

        notificationManager.notify(2, notification)
    }

    fun cancelBackgroundNotification() {
        // TODO: 13. cancel the permanent notification once the app is back in the foreground
        notificationManager.cancel(2)
    }
}
