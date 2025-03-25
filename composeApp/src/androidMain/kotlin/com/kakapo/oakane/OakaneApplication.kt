package com.kakapo.oakane

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.kakapo.data.repository.base.SettingsRepository
import com.kakapo.oakane.di.initKoin
import com.kakapo.oakane.sync.worker.ReminderWorker
import org.koin.androidx.workmanager.factory.KoinWorkerFactory
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.GlobalContext
import org.koin.core.context.GlobalContext.get
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

class OakaneApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        initKoin(
            appModule = module {
                single<Context> {
                    this@OakaneApplication
                }
                single {
                    Configuration.Builder()
                        .setWorkerFactory(ReminderWorker.createFactory(get()))
                        .build()
                }
            }
        )
        val config = get<Configuration>()
        WorkManager.initialize(this, config)
        scheduleDailyReminder(this)
    }

    private fun scheduleDailyReminder(context: Context) {
        val workRequest = PeriodicWorkRequestBuilder<ReminderWorker>(1, TimeUnit.DAYS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiresBatteryNotLow(true)
                    .setRequiresDeviceIdle(false)
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "DailyReminder",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}