package com.weather.test.data.restapi.entity

import com.google.gson.annotations.SerializedName

data class ApiWeatherStatisticsEntity(
    @SerializedName("name")
    val location: String,
    @SerializedName("main")
    val mainInfo: MainInfo,
    @SerializedName("wind")
    val windInfo: WindInfo,
    @SerializedName("sys")
    val systemInfo: SystemInfo,
    val visibility: Float
) {
    data class MainInfo(
        @SerializedName("temp")
        val temperature: Float,
        val humidity: Int
    )

    data class WindInfo(
        val speed: Float
    )

    data class SystemInfo(
        @SerializedName("sunrise")
        val sunriseTime: Long,
        @SerializedName("sunset")
        val sunsetTime: Long
    )
}
