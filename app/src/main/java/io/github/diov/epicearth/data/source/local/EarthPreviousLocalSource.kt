package io.github.diov.epicearth.data.source.local

import android.content.Context
import io.github.diov.epicearth.data.dao.EarthData
import io.github.diov.epicearth.data.dao.EarthDataDao
import io.github.diov.epicearth.data.dao.EarthDatabase
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.withContext

/**
 * Created by Dio_V on 2018/6/24.
 * Copyright © 2018 diov.github.io. All rights reserved.
 */

class EarthPreviousLocalSource(context: Context) {

    private val earthDataDao: EarthDataDao

    init {
        val earthDatabase = EarthDatabase.getInstance(context)
        earthDataDao = earthDatabase.earthDataDao()
    }

    suspend fun storeEarthData(vararg earthData: EarthData) = withContext(CommonPool) {
        earthDataDao.storeEarthData(*earthData)
    }

    suspend fun loadLatestEarth(): List<EarthData> = async {
        return@async earthDataDao.loadLatestEarth()
    }.await()
}
