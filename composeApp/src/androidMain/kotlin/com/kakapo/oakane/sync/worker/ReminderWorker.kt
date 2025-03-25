package com.kakapo.oakane.sync.worker

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.kakapo.data.repository.base.SettingsRepository
import com.kakapo.model.reminder.Reminder
import com.kakapo.oakane.sync.receiver.ReminderReceiver
import java.time.DayOfWeek
import java.util.Calendar

class ReminderWorker(
    context: Context,
    params: WorkerParameters,
    private val settingsRepository: SettingsRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val reminder = settingsRepository.loadReminder().getOrNull()
        reminder?.let { alarmReminder ->
            if (alarmReminder.isReminderEnabled) {
                schedule(reminder)
            }
        }
        return Result.success()
    }

    private fun schedule(reminder: Reminder) {
        val calendar = Calendar.getInstance()
        val currentDay = DayOfWeek.of(calendar.get(Calendar.DAY_OF_WEEK))

        if (reminder.reminders.any{ it.dayOfWeek == currentDay}) {
            setAlarm(reminder.selectedHour, reminder.selectedMinute)
        }
    }

    private fun setAlarm(hour: Int, minute: Int) {
        val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(applicationContext, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    companion object {
        fun createFactory(settingsRepository: SettingsRepository) = object : WorkerFactory() {
            override fun createWorker(
                appContext: Context,
                workerClassName: String,
                workerParameters: WorkerParameters
            ): ListenableWorker? {
                return if (workerClassName == ReminderWorker::class.java.name) {
                    ReminderWorker(appContext, workerParameters, settingsRepository)
                } else {
                    null
                }
            }

        }
    }
}