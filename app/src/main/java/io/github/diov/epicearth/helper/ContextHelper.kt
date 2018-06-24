package io.github.diov.epicearth.helper

import android.content.Context

/**
 * Created by Dio_V on 2018/6/24.
 * Copyright © 2018 diov.github.io. All rights reserved.
 */

fun Context.dpToPx(dp: Int): Int {
    val metrics = resources.displayMetrics
    return (dp * metrics.density).toInt()
}

fun Context.dpToPx(dp: Float): Float {
    val metrics = resources.displayMetrics
    return dp * metrics.density
}
