package com.kakapo.oakane

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import com.kakapo.oakane.di.initKoin
import org.koin.androidx.workmanager.factory.KoinWorkerFactory
import org.koin.core.component.KoinComponent
import org.koin.core.context.GlobalContext
import org.koin.dsl.module

class OakaneApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        initKoin(
            appModule = module {
                single<Context> {
                    this@OakaneApplication
                }
                single { KoinWorkerFactory() }
            }
        )

        val koinWorkerFactory = GlobalContext.get().get<KoinWorkerFactory>()
        WorkManager.initialize(
            this,
            Configuration.Builder().setWorkerFactory(koinWorkerFactory).build()
        )
    }
}