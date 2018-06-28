package io.github.diov.epicearth.helper

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import io.github.diov.epicearth.Constant
import io.github.diov.epicearth.data.EarthSetting
import io.github.diov.epicearth.job.FetchJobService

/**
 * Created by Dio_V on 2018/6/29.
 * Copyright © 2018 diov.github.io. All rights reserved.
 */

class JobSchedulerHelper(context: Context) {

    private val applicationContext = context.applicationContext
    private val scheduler = applicationContext.getSystemService(Context.JOB_SCHEDULER_SERVICE) as? JobScheduler
    private val serviceComponent = ComponentName(applicationContext, FetchJobService::class.java)

    fun startFetchJob(setting: EarthSetting) {
        val jobScheduler = scheduler ?: return
        val jobInfo = JobInfo.Builder(Constant.FETCH_JOB_ID, serviceComponent)
            .setOverrideDeadline(24 * 60 * 60 * 1000)
            .setRequiresDeviceIdle(true)
            .setRequiredNetworkType(if (setting.wifiTrigger) JobInfo.NETWORK_TYPE_UNMETERED else JobInfo.NETWORK_TYPE_ANY)
            .build()
        jobScheduler.schedule(jobInfo)
    }

    fun stopFetchJob() {
        val jobScheduler = scheduler ?: return
        jobScheduler.cancel(Constant.FETCH_JOB_ID)
    }
}
