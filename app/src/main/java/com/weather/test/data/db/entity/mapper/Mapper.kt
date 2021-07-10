package com.weather.test.data.db.entity.mapper

import com.weather.test.data.db.entity.DbWeatherStatisticsEntity
import com.weather.test.domain.entity.SpeedMeasure
import com.weather.test.domain.entity.TemperatureMeasure
import com.weather.test.domain.entity.WeatherStatisticsEntity
import com.weather.test.domain.value.Celsius
import com.weather.test.domain.value.Fahrenheit
import com.weather.test.domain.value.KilometersPerHour
import com.weather.test.domain.value.MilesPerHour

fun WeatherStatisticsEntity.asDbWeatherStatisticsEntity(zipCode: Int) = DbWeatherStatisticsEntity(
    zipCode,
    location,
    when(temperature) {
        is Fahrenheit -> TemperatureMeasure.FAHRENHEIT
        is Celsius -> TemperatureMeasure.CELSIUS
    },
    temperature.value,
    when(windSpeed) {
        is MilesPerHour -> SpeedMeasure.MILES_PER_HOUR
        is KilometersPerHour -> SpeedMeasure.KILOMETERS_PER_HOUR
    },
    windSpeed.value,
    humidity,
    visibility,
    sunriseTime,
    sunsetTime
)

fun DbWeatherStatisticsEntity.asWeatherStatisticsEntity() = WeatherStatisticsEntity(
    location,
    when(temperatureMeasure) {
        TemperatureMeasure.FAHRENHEIT -> Fahrenheit(temperature)
        TemperatureMeasure.CELSIUS -> Celsius(temperature)
    },
    when(windSpeedMeasure) {
        SpeedMeasure.MILES_PER_HOUR -> MilesPerHour(windSpeed)
        SpeedMeasure.KILOMETERS_PER_HOUR -> KilometersPerHour(windSpeed)
    },
    humidity,
    visibility,
    sunriseTime,
    sunsetTime
)
