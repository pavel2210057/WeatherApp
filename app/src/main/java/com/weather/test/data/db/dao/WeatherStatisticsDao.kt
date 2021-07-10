package com.weather.test.data.db.dao

import androidx.room.*
import com.weather.test.data.db.entity.DbWeatherStatisticsEntity
import com.weather.test.domain.entity.SpeedMeasure
import com.weather.test.domain.entity.TemperatureMeasure

@Dao
interface WeatherStatisticsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherStatistics(dbWeatherStatisticsEntity: DbWeatherStatisticsEntity)

    @Query("SELECT * FROM DbWeatherStatisticsEntity WHERE zipCode=:zipCode")
    suspend fun getWeatherStatisticsByZipCode(zipCode: Int): DbWeatherStatisticsEntity?

    @Query("""UPDATE DbWeatherStatisticsEntity SET temperatureMeasure=:temperatureMeasure, 
        temperature=:temperature WHERE zipCode=:zipCode""")
    suspend fun updateTemperatureMeasure(
        zipCode: Int,
        temperatureMeasure: TemperatureMeasure,
        temperature: Float
    )

    @Query("""UPDATE DbWeatherStatisticsEntity SET windSpeedMeasure=:windSpeedMeasure, 
        windSpeed=:windSpeed WHERE zipCode=:zipCode""")
    suspend fun updateWindSpeedMeasure(
        zipCode: Int,
        windSpeedMeasure: SpeedMeasure,
        windSpeed: Float
    )
}
