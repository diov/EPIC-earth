package io.github.diov.epicearth.helper

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

fun String.splitDate(): String {
    val date = parseDate(this)
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return format.format(date)
}

fun String.urlDate(): String {
    val date = parseDate(this)
    val format = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
    return format.format(date)
}

private fun parseDate(dateString: String): Date {
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return format.parse(dateString)
}
