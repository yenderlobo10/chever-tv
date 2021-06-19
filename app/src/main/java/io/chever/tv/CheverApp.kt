package io.chever.tv

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import io.chever.tv.common.extension.Constants

class CheverApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initProperties()

        setupLogger()
    }


    private fun initProperties(){

        context = this.applicationContext
    }

    private fun setupLogger() {

        Logger.addLogAdapter(
            AndroidLogAdapter(
                PrettyFormatStrategy.newBuilder()
                    .tag(Constants.loggerTag)
                    .build()
            )
        )

        Logger.i(">> CREATE APP <<")
    }


    companion object{

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
}