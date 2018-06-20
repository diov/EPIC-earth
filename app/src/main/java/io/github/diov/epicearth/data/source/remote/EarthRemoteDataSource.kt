package io.github.diov.epicearth.data.source.remote

import io.github.diov.epicearth.ApiService
import io.github.diov.epicearth.data.EarthData
import io.github.diov.epicearth.data.EarthOption
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.serialization.json.JSON
import kotlinx.serialization.list
import kotlinx.serialization.serializer
import okhttp3.Request

/**
 * Created by Dio_V on 2018/6/18.
 * Copyright © 2018 diov.github.io. All rights reserved.
 */

class EarthRemoteDataSource {

    fun fetchEarthData(option: EarthOption) = async(CommonPool) {
        val url = "${ApiService.API_DOMAIN}/${option.colorType}${ApiService.API_KEY}"
        val request = Request.Builder().url(url).build()
        val response = ApiService.client.newCall(request).execute()
        val responseBody = response.body()?.string() ?: ""
        return@async JSON.nonstrict.parse(EarthData::class.serializer().list, responseBody)
    }
}
