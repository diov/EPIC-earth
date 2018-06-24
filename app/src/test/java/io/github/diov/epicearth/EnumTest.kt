package io.github.diov.epicearth

import io.github.diov.epicearth.data.ColorType
import io.github.diov.epicearth.data.ImageType
import org.junit.Test

/**
 * Created by Dio_V on 2018/6/24.
 * Copyright © 2018 diov.github.io. All rights reserved.
 */

class EnumTest {
    @Test
    fun enum_value_test() {
        val defaultColorType = ColorType.typeOf("")
        assert(defaultColorType == ColorType.NATURAL)

        val naturalColorType = ColorType.typeOf("natural")
        assert(naturalColorType == ColorType.NATURAL)

        val enhancedColorType = ColorType.typeOf("enhanced")
        assert(enhancedColorType == ColorType.ENHANCED)

        val defaultImageType = ImageType.typeOf("")
        assert(defaultImageType == ImageType.PNG)

        val pngImageType = ImageType.typeOf("PNG")
        assert(pngImageType == ImageType.PNG)

        val jpgImageType = ImageType.typeOf("jpg")
        assert(jpgImageType == ImageType.JPG)
    }
}
