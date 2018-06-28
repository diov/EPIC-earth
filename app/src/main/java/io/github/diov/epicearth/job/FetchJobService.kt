package io.github.diov.epicearth.job

import android.app.job.JobParameters
import android.app.job.JobService
import io.github.diov.epicearth.Constant
import io.github.diov.epicearth.data.dao.EarthData
import io.github.diov.epicearth.data.source.local.EarthDataLocalSource
import io.github.diov.epicearth.data.source.local.EarthSettingLocalSource
import io.github.diov.epicearth.data.source.remote.EarthDataRemoteSource
import kotlinx.coroutines.experimental.launch

/**
 * Created by Dio_V on 2018/6/29.
 * Copyright © 2018 diov.github.io. All rights reserved.
 */

class FetchJobService : JobService() {

    private val settingSource: EarthSettingLocalSource = EarthSettingLocalSource(application)

    override fun onStopJob(params: JobParameters?): Boolean {
        try {
            launch {
                val setting = settingSource.loadEarthSetting()
                val option = setting.generateOption()
                val originData = EarthDataRemoteSource().fetchEarthData(option)
                val data = originData.map {
                    EarthData.fromOriginData(it, option)
                }
                EarthDataLocalSource(this@FetchJobService).storeEarthData(data)
                contentResolver.notifyChange(Constant.LATEST_UPDATE_URL, null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            jobFinished(params, true)
        }

        return true
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        return true
    }
}
