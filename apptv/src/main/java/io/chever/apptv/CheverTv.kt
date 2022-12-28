package io.chever.apptv

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.chever.shared.observability.AppLogger

@HiltAndroidApp
class CheverTv : Application() {

    override fun onCreate() {
        super.onCreate()

        AppLogger.setup()
    }
}