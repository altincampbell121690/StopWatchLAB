package com.digisafari.stopwatch.services

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.digisafari.stopwatch.R

class CustomNotificationService(
    private val context: Context
) {
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun stopWatchStatus(status: String) {

        // TODO: 11. Create a notification for the stopwatch status
    }

    fun showBackgroundNotification() {

        // TODO: 12. Create a notification for the background status, make it permanent

    }

    fun cancelBackgroundNotification(){
        // TODO: 13. cancel the permanent notification once the app is back in the foreground
    }
}