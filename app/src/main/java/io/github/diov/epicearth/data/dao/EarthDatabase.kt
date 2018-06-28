package io.github.diov.epicearth.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by Dio_V on 2018/6/28.
 * Copyright © 2018 diov.github.io. All rights reserved.
 */

@Database(entities = [(EarthData::class)], version = 1, exportSchema = false)
abstract class EarthDatabase : RoomDatabase() {
    abstract fun earthDataDao(): EarthDataDao

    companion object {
        private var instance: EarthDatabase? = null

        fun getInstance(context: Context): EarthDatabase {
            val instance = instance
            return if (null != instance) {
                instance
            } else {
                val database = Room.databaseBuilder(context, EarthDatabase::class.java, "earth.db").build()
                this.instance = database
                database
            }
        }
    }
}
