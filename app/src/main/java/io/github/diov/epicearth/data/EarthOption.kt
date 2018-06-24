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

    companion object {
        fun typeOf(type: String): ColorType {
            return when (type.toLowerCase()) {
                "natural" -> NATURAL
                "enhanced" -> ENHANCED
                else -> NATURAL
            }
        }
    }
}

enum class ImageType(val extension: String) {
    PNG(".png"),
    JPG(".jpg");

    override fun toString(): String {
        return super.toString().toLowerCase()
    }

    companion object {
        fun typeOf(type: String): ImageType {
            return when (type.toLowerCase()) {
                "png" -> PNG
                "jpg" -> JPG
                else -> PNG
            }
        }
    }
}
