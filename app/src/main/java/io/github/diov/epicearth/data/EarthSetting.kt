package io.github.diov.epicearth.data

/**
 * Created by Dio_V on 2018/6/24.
 * Copyright © 2018 diov.github.io. All rights reserved.
 */

data class EarthSetting(
    var enhancedMode: Boolean,
    var highQuality: Boolean,
    var wifiTrigger: Boolean
) {
    fun generateOption(): EarthOption {
        val colorType = if (enhancedMode) ColorType.ENHANCED else ColorType.NATURAL
        val imageType = if (highQuality) ImageType.PNG else ImageType.JPG
        return EarthOption(colorType, imageType)
    }
}
