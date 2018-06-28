package io.github.diov.epicearth.data.source.local

import android.content.Context
import io.github.diov.epicearth.data.dao.EarthData
import io.github.diov.epicearth.data.dao.EarthDataDao
import io.github.diov.epicearth.data.dao.EarthDatabase
import kotlinx.coroutines.experimental.async

/**
 * Created by Dio_V on 2018/6/24.
 * Copyright © 2018 diov.github.io. All rights reserved.
 */

class EarthDataLocalSource(context: Context) {

    private val earthDataDao: EarthDataDao

    init {
        val earthDatabase = EarthDatabase.getInstance(context)
        earthDataDao = earthDatabase.earthDataDao()
    }

    suspend fun storeEarthData(dataList: List<EarthData>) = async {
        return@async earthDataDao.storeEarthData(*dataList.toTypedArray())
    }.await()

    suspend fun loadLatestEarth(): List<EarthData> = async {
        return@async earthDataDao.loadLatestEarth()
    }.await()
}
