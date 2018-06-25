package io.github.diov.epicearth.data.source.local

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Dio_V on 2018/6/24.
 * Copyright © 2018 diov.github.io. All rights reserved.
 */

class EarthPreviousLocalSource(private val context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(EARTH_PREVIOUS, Context.MODE_PRIVATE)
    }

    fun storePreviousImage(imageUrl: String) {
        val edit = sharedPreferences.edit()
        edit.putString(PREVIOUS_IMAGE, imageUrl)
        edit.apply()
    }

    fun loadPreviousImage(): String {
        return sharedPreferences.getString(PREVIOUS_IMAGE, "")
    }

    companion object {
        const val EARTH_PREVIOUS = "earth_setting"
        const val PREVIOUS_IMAGE = "previous_image"
    }
}
