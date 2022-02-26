package com.tandem.tandemtest

import android.app.Application
import android.content.Context
import com.tandem.tandemtest.core.di.koinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        // start koin
        startKon(this)
    }

    /**
     * start koin modules
     */
    private fun startKon(context: Context) {
        startKoin {
            androidContext(context)
            modules(koinModules)
        }
    }
}