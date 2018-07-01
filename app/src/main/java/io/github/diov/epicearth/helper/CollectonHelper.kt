package io.github.diov.epicearth.helper

import java.util.concurrent.ThreadLocalRandom

/**
 * Created by Dio_V on 2018/7/1.
 * Copyright © 2018 diov.github.io. All rights reserved.
 */

fun <T> List<T>.random(): T? {
    return if (size <= 0) {
        null
    } else {
        val randomIndex = ThreadLocalRandom.current().nextInt(size)
        get(randomIndex)
    }
}
