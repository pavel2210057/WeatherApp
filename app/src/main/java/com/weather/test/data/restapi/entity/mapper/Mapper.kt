package com.weather.test.data.restapi.entity.mapper

import com.weather.test.data.restapi.entity.ApiWeatherStatisticsEntity
import com.weather.test.domain.entity.WeatherStatisticsEntity
import com.weather.test.domain.value.Fahrenheit
import com.weather.test.domain.value.MilesPerHour
import java.util.*

fun ApiWeatherStatisticsEntity.asWeatherStatisticsEntity() = WeatherStatisticsEntity(
    location,
    Fahrenheit(mainInfo.temperature),
    MilesPerHour(windInfo.speed),
    mainInfo.humidity,
    visibility,
    Date(systemInfo.sunriseTime * 1000),
    Date(systemInfo.sunsetTime * 1000)
)
