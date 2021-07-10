package com.weather.test.domain.entity

import com.weather.test.domain.value.Speed
import com.weather.test.domain.value.Temperature
import java.util.*

data class WeatherStatisticsEntity(
    val location: String,
    val temperature: Temperature,
    val windSpeed: Speed,
    val humidity: Int,
    val visibility: Float,
    val sunriseTime: Date,
    val sunsetTime: Date
)
