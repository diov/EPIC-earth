package io.github.diov.epicearth.data

import io.github.diov.epicearth.ApiService
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * Created by Dio_V on 2018/6/18.
 * Copyright © 2018 diov.github.io. All rights reserved.
 */

@Serializable
data class EarthData(
    @SerialName("identifier")
    val id: String,
    @SerialName("caption")
    val caption: String,
    @SerialName("image")
    private val imagePath: String,
    @SerialName("version")
    val version: String,
    @SerialName("date")
    val date: String,
    @SerialName("centroid_coordinates")
    val centroidCoordinates: Coordinate,
    @Transient
    @SerialName("dscovr_j2000_position")
    val satellitePosition: Position? = null,
    @Transient
    @SerialName("lunar_j2000_position")
    val lunarPosition: Position? = null,
    @Transient
    @SerialName("sun_j2000_position")
    val sunPosition: Position? = null,
    @Transient
    @SerialName("attitude_quaternions")
    val satelliteAttitude: Attitude? = null,
    @Transient
    @SerialName("coords")
    val coordinates: EarthCoordinates? = null
) {
    fun getPreviewImageUrl(option: EarthOption): String {
        val datePath = date.split(" ").first().replace("-", "/")
        val path = "/${option.colorType}/$datePath/png/$imagePath.png"
        return "${ApiService.ARCHIVE_DOMAIN}$path${ApiService.API_KEY}"
    }

    fun getRealImageUrl(option: EarthOption): String {
        val datePath = date.split(" ").first().replace("-", "/")
        val path = "/${option.colorType}/$datePath/${option.imageType}/$imagePath${option.imageType.extension}"
        return "${ApiService.ARCHIVE_DOMAIN}$path${ApiService.API_KEY}"
    }
}

@Serializable
data class Coordinate(
    @SerialName("lat")
    val latitude: Float,
    @SerialName("lon")
    val longitude: Float
)

@Serializable
data class Position(
    val x: Float,
    val y: Float,
    val z: Float
)

@Serializable
data class Attitude(
    val q0: Float,
    val q1: Float,
    val q2: Float,
    val q3: Float
)

@Serializable
data class EarthCoordinates(
    @SerialName("centroid_coordinates")
    val centroidCoordinates: Coordinate,
    @SerialName("dscovr_j2000_position")
    val satellitePosition: Position,
    @SerialName("lunar_j2000_position")
    val lunarPosition: Position,
    @SerialName("sun_j2000_position")
    val sunPosition: Position,
    @SerialName("attitude_quaternions")
    val satelliteAttitude: Attitude
)
