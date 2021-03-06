package io.github.diov.epicearth

import io.github.diov.epicearth.data.ColorType
import io.github.diov.epicearth.data.ImageType
import io.github.diov.epicearth.helper.splitDate
import io.github.diov.epicearth.helper.urlDate
import org.junit.Test
import java.util.concurrent.ThreadLocalRandom

/**
 * Created by Dio_V on 2018/6/24.
 * Copyright © 2018 diov.github.io. All rights reserved.
 */

class FeatureTest {
    @Test
    fun enum_value_test() {
        val defaultColorType = ColorType.typeOf("")
        assert(defaultColorType == ColorType.NATURAL)

        val naturalColorType = ColorType.typeOf("natural")
        assert(naturalColorType == ColorType.NATURAL)

        val enhancedColorType = ColorType.typeOf("enhanced")
        assert(enhancedColorType == ColorType.ENHANCED)

        val defaultImageType = ImageType.typeOf("")
        assert(defaultImageType == ImageType.JPG)

        val pngImageType = ImageType.typeOf("PNG")
        assert(pngImageType == ImageType.PNG)

        val jpgImageType = ImageType.typeOf("jpg")
        assert(jpgImageType == ImageType.JPG)
    }

    @Test
    fun random_test() {
        (0..100).forEach {
            val random = ThreadLocalRandom.current().nextInt(2)
            println(random)
            assert(random < 2)
        }
    }

    @Test
    fun date_format_test() {
        val originString = "2018-06-30 13:56:00"
        val urlDate = originString.urlDate()
        val splitDate = originString.splitDate()

        assert(urlDate == "2018/06/30")
        assert(splitDate == "2018-06-30")
    }
}
