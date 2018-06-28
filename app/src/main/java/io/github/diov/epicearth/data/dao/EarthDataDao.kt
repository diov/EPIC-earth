package io.github.diov.epicearth.data.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by Dio_V on 2018/6/28.
 * Copyright © 2018 diov.github.io. All rights reserved.
 */

@Dao
interface EarthDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun storeEarthData(vararg earthData: EarthData): LongArray

    @Query("SELECT * FROM earth_data WHERE (SELECT max(date) FROM earth_data)")
    fun loadLatestEarth(): List<EarthData>

    @Query("SELECT * FROM earth_data WHERE (SELECT max(date) FROM earth_data)")
    fun loadLatestEarthCursor(): Cursor
}
