package com.weather.test.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.weather.test.data.db.dao.WeatherStatisticsDao
import com.weather.test.data.db.entity.DbWeatherStatisticsEntity

@Database(entities = [DbWeatherStatisticsEntity::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getWeatherStatisticsDao(): WeatherStatisticsDao
}
