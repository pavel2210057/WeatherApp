package com.weather.test.data.restapi.call

import com.weather.test.data.restapi.entity.ApiWeatherStatisticsEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherStatisticsCall {
    @GET("data/2.5//weather")
    fun getWeatherStatisticsByZipCode(@Query("zip") zip: String): Call<ApiWeatherStatisticsEntity>
}
