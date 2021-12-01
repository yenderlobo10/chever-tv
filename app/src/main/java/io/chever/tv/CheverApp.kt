package io.chever.tv

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import io.chever.tv.common.extension.TimberReleaseTree
import timber.log.Timber

class CheverApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initProperties()

        setupTimber()

        Timber.i(">> CREATE APP <<")
    }


    private fun initProperties() {

        context = this.applicationContext
    }

    private fun setupTimber() {

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
        else
            Timber.plant(TimberReleaseTree())
    }


    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
}