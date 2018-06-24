package io.github.diov.epicearth.data.source.local

import android.content.Context
import android.content.SharedPreferences
import io.github.diov.epicearth.data.ColorType
import io.github.diov.epicearth.data.EarthOption
import io.github.diov.epicearth.data.EarthSetting
import io.github.diov.epicearth.data.ImageType

/**
 * Created by Dio_V on 2018/6/24.
 * Copyright © 2018 diov.github.io. All rights reserved.
 */

class EarthSettingLocalSource(private val context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(EARTH_SETTING, Context.MODE_PRIVATE)
    }

    fun storeEarthSetting(setting: EarthSetting) {
        val edit = sharedPreferences.edit()
        edit.putBoolean(ENHANCED_MODE, setting.enhancedMode)
        edit.putBoolean(HIGH_QUALITY, setting.highQuality)
        edit.putBoolean(WIFI_TRIGGER, setting.wifiTrigger)
        edit.apply()
    }

    fun loadEarthSetting(): EarthSetting {
        val enhancedMode = sharedPreferences.getBoolean(ENHANCED_MODE, false)
        val highQuality = sharedPreferences.getBoolean(HIGH_QUALITY, false)
        val wifiTrigger = sharedPreferences.getBoolean(WIFI_TRIGGER, false)
        return EarthSetting(enhancedMode, highQuality, wifiTrigger)
    }

    companion object {
        const val EARTH_SETTING = "earth_setting"
        const val ENHANCED_MODE = "enhanced_mode"
        const val HIGH_QUALITY = "high_quality"
        const val WIFI_TRIGGER = "wifi_trigger"
    }
}
