package io.github.diov.epicearth.data.source.local

import android.content.Context
import android.content.SharedPreferences
import io.github.diov.epicearth.data.ColorType
import io.github.diov.epicearth.data.EarthOption
import io.github.diov.epicearth.data.ImageType

/**
 * Created by Dio_V on 2018/6/24.
 * Copyright © 2018 diov.github.io. All rights reserved.
 */

class EarthSettingLocalSource(private val context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(EARTH_SETTING, Context.MODE_PRIVATE)
    }

    fun loadEarthOption(): EarthOption {
        val color = sharedPreferences.getString(OPTION_COLOR_TYPE, "")
        val image = sharedPreferences.getString(OPTION_IMAGE_TYPE, "")
        return EarthOption(ColorType.typeOf(color), ImageType.typeOf(image))
    }

    companion object {
        const val EARTH_SETTING = "earth_setting"
        const val OPTION_COLOR_TYPE = "option_color"
        const val OPTION_IMAGE_TYPE = "option_image"
    }
}
