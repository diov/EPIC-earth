package io.github.diov.epicearth

import io.github.diov.epicearth.data.ColorType.ENHANCED
import io.github.diov.epicearth.data.EarthOption
import io.github.diov.epicearth.data.ImageType.PNG
import io.github.diov.epicearth.data.source.remote.EarthDataRemoteSource
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
            println("Thread==>" + Thread.currentThread().name)
            try {
                val option = EarthOption(ENHANCED, PNG)
                val fetchEarthData = EarthDataRemoteSource().fetchEarthData(option)
                fetchEarthData.forEach {
                    print(it.getPreviewImageUrl(option))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
