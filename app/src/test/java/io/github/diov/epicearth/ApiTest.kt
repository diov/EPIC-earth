package io.github.diov.epicearth

import io.github.diov.epicearth.data.source.remote.EarthRemoteDataSource
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test

/**
 * Created by Dio_V on 2018/6/18.
 * Copyright © 2018 diov.github.io. All rights reserved.
 */

class ApiTest {
    @Test
    fun fetch_earth_data() {
        runBlocking {
            try {
                val fetchEarthData = EarthRemoteDataSource().fetchEarthData().await()
                println(Thread.currentThread().name)
                println(fetchEarthData)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
