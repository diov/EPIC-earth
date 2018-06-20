package io.github.diov.epicearth

import okhttp3.OkHttpClient

/**
 * Created by Dio_V on 2018/6/21.
 * Copyright © 2018 diov.github.io. All rights reserved.
 */
object ApiService {
    val client = OkHttpClient()

    private const val MAIN_DOMAIN = "https://api.nasa.gov/EPIC"
    const val API_KEY = "?api_key=${BuildConfig.API_KEY}"
    const val API_DOMAIN = "$MAIN_DOMAIN/api"
    const val ARCHIVE_DOMAIN = "$MAIN_DOMAIN/archive"
}
