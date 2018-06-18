package io.github.diov.epicearth.data.source.remote

import io.github.diov.epicearth.BuildConfig
import io.github.diov.epicearth.data.EarthData
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.withContext
import kotlinx.serialization.json.JSON
import kotlinx.serialization.list
import kotlinx.serialization.serializer
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Created by Dio_V on 2018/6/18.
 * Copyright © 2018 diov.github.io. All rights reserved.
 */

class EarthRemoteDataSource {

    companion object {
        private const val API_KEY = BuildConfig.API_KEY
        private const val API_KEY_QUERY = "?api_key=$API_KEY"
        const val DOMAIN = "https://api.nasa.gov/EPIC/api/natural$API_KEY_QUERY"
    }

    private val client = OkHttpClient()

    suspend fun fetchEarthData() = withContext(CommonPool) {
        val request = Request.Builder().url(DOMAIN).build()
        val response = client.newCall(request).execute()
        val responseBody = response.body()?.string() ?: ""
        return@withContext JSON.nonstrict.parse(EarthData::class.serializer().list, responseBody)
    }
}
