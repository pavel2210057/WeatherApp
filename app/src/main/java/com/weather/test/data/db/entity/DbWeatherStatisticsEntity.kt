package com.weather.test.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.weather.test.data.db.converter.DateConverter
import com.weather.test.domain.entity.SpeedMeasure
import com.weather.test.domain.entity.TemperatureMeasure
import java.util.*

@Entity
@TypeConverters(DateConverter::class)
data class DbWeatherStatisticsEntity(
    @PrimaryKey
    val zipCode: Int,
    val location: String,
    val temperatureMeasure: TemperatureMeasure,
    val temperature: Float,
    val windSpeedMeasure: SpeedMeasure,
    val windSpeed: Float,
    val humidity: Int,
    val visibility: Float,
    val sunriseTime: Date,
    val sunsetTime: Date
)
