package io.github.diov.epicearth

import android.content.ContentResolver
import android.net.Uri

/**
 * Created by Dio_V on 2018/6/28.
 * Copyright © 2018 diov.github.io. All rights reserved.
 */

object Constant {

    const val FETCH_JOB_ID = 10086

    private const val AUTHORITY = "${BuildConfig.APPLICATION_ID}.epic"

    private val CONTENT_URI = Uri.Builder()
        .scheme(ContentResolver.SCHEME_CONTENT)
        .authority(AUTHORITY)
        .path("earth")
        .build()

    val LATEST_UPDATE_URL: Uri = Uri.withAppendedPath(CONTENT_URI, "update")
}
