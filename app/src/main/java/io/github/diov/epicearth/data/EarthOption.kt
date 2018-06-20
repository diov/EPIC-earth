package io.github.diov.epicearth.data

/**
 * Created by Dio_V on 2018/6/21.
 * Copyright © 2018 diov.github.io. All rights reserved.
 */

data class EarthOption(
    var colorType: ColorType,
    var imageType: ImageType
)

enum class ColorType {
    NATURAL, ENHANCED;

    override fun toString(): String {
        return super.toString().toLowerCase()
    }
}

enum class ImageType(val suffix: String) {
    PNG(".png"),
    JPG(".jpg"),
    THUMBS(".jpg");

    override fun toString(): String {
        return super.toString().toLowerCase()
    }
}
