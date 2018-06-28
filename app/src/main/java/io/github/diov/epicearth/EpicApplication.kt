package io.github.diov.epicearth

import android.app.Application
import com.facebook.stetho.Stetho

/**
 * Created by Dio_V on 2018/6/26.
 * Copyright © 2018 diov.github.io. All rights reserved.
 */

class EpicApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }
}
