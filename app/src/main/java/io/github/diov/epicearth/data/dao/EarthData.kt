package io.github.diov.epicearth.data.dao

import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.diov.epicearth.data.Attitude
import io.github.diov.epicearth.data.Coordinate
import io.github.diov.epicearth.data.EarthOption
import io.github.diov.epicearth.data.EarthOriginData
import io.github.diov.epicearth.data.Position

/**
 * Created by Dio_V on 2018/6/28.
 * Copyright © 2018 diov.github.io. All rights reserved.
 */

@Entity(tableName = TABLE_NAME)
data class EarthData(
    @PrimaryKey
    @ColumnInfo(name = BaseColumns._ID)
    val id: String,
    val previewUrl: String,
    val imageUrl: String,
    val version: String,
    val date: String,
    @Embedded(prefix = "centroid")
    val centroidCoordinates: Coordinate,
    @Embedded(prefix = "satellite")
    val satellitePosition: Position? = null,
    @Embedded(prefix = "lunar")
    val lunarPosition: Position? = null,
    @Embedded(prefix = "sun")
    val sunPosition: Position? = null,
    @Embedded
    val satelliteAttitude: Attitude? = null
) {
    companion object {
        fun fromOriginData(originData: EarthOriginData, option: EarthOption): EarthData {
            val id = originData.id
            val previewUrl = originData.getPreviewImageUrl(option)
            val imageUrl = originData.getRealImageUrl(option)
            val version = originData.version
            val date = originData.date
            val centroidCoordinates = originData.centroidCoordinates
            val satellitePosition = originData.satellitePosition
            val lunarPosition = originData.lunarPosition
            val sunPosition = originData.sunPosition
            val satelliteAttitude = originData.satelliteAttitude
            return EarthData(
                id,
                previewUrl,
                imageUrl,
                version,
                date,
                centroidCoordinates,
                satellitePosition,
                lunarPosition,
                sunPosition,
                satelliteAttitude
            )
        }
    }
}

const val TABLE_NAME = "earth_data"
