package com.kakapo.oakane

import android.app.Application
import com.kakapo.oakane.di.initKoin

class OakaneApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}