package com.kakapo.oakane

import android.app.Application
import android.content.Context
import com.kakapo.oakane.di.initKoin
import org.koin.dsl.module

class OakaneApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(
            appModule = module {
                single<Context> {
                    this@OakaneApplication
                }
            }
        )
    }
}