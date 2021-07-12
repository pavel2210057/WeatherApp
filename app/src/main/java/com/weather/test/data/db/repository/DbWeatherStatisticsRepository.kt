package com.weather.test.data.db.repository

import com.weather.test.data.db.WeatherDatabase
import com.weather.test.data.db.entity.DbWeatherStatisticsEntity
import com.weather.test.presentation.common.FetchDataViewState
import com.weather.test.domain.entity.SpeedMeasure
import com.weather.test.domain.entity.TemperatureMeasure

class DbWeatherStatisticsRepository(
    private val database: WeatherDatabase
) {
    suspend fun insertWeatherStatistics(dbWeatherStatisticsEntity: DbWeatherStatisticsEntity) {
        database.getWeatherStatisticsDao().insertWeatherStatistics(dbWeatherStatisticsEntity)
    }

    suspend fun getWeatherStatisticsByZipCode(zipCode: Int): FetchDataViewState<DbWeatherStatisticsEntity> =
        database.getWeatherStatisticsDao().getWeatherStatisticsByZipCode(zipCode)?.let {
            FetchDataViewState.data(it)
        } ?: FetchDataViewState.failure("Data wasn't found")


    suspend fun updateTemperatureMeasure(
        zipCode: Int,
        temperatureMeasure: TemperatureMeasure,
        temperature: Float
    ) {
        database.getWeatherStatisticsDao().updateTemperatureMeasure(zipCode, temperatureMeasure,
            temperature)
    }

    suspend fun updateWindSpeedMeasure(
        zipCode: Int,
        windSpeedMeasure: SpeedMeasure,
        windSpeed: Float
    ) {
        database.getWeatherStatisticsDao().updateWindSpeedMeasure(zipCode, windSpeedMeasure,
            windSpeed)
    }
}
