package com.kakapo.oakane.sync.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.kakapo.oakane.R

class ReminderReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        showNotification(context)
    }

    private fun showNotification(context: Context) {
        val notificationManager = ContextCompat.getSystemService(
            context, NotificationManager::class.java
        ) as NotificationManager

        val builder = NotificationCompat.Builder(context, "reminder_channel")
            .setSmallIcon(R.drawable.oakane_icon)
            .setContentTitle("Reminder Alert")
            .setContentText("You have a reminder set for today.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        notificationManager.notify(1, builder.build())
    }
}