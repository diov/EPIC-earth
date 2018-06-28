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
    @Embedded(prefix = "centroid_")
    val centroidCoordinates: Coordinate,
    @Embedded(prefix = "satellite_")
    val satellitePosition: Position,
    @Embedded(prefix = "lunar_")
    val lunarPosition: Position,
    @Embedded(prefix = "sun_")
    val sunPosition: Position,
    @Embedded
    val satelliteAttitude: Attitude
) {
    companion object {
        fun fromOriginData(originData: EarthOriginData, option: EarthOption): EarthData {
            return EarthData(
                originData.id,
                originData.getPreviewImageUrl(option),
                originData.getRealImageUrl(option),
                originData.version,
                originData.date,
                originData.centroidCoordinates,
                originData.satellitePosition,
                originData.lunarPosition,
                originData.sunPosition,
                originData.satelliteAttitude
            )
        }
    }
}

const val TABLE_NAME = "earth_data"
