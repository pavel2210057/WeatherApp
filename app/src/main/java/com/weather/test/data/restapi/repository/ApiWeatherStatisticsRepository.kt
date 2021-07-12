package com.weather.test.data.restapi.repository

import com.weather.test.data.restapi.call.WeatherStatisticsCall
import com.weather.test.data.restapi.entity.ApiWeatherStatisticsEntity
import com.weather.test.presentation.common.FetchDataViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ApiWeatherStatisticsRepository(
    private val weatherStatisticsCall: WeatherStatisticsCall
) {
    suspend fun getWeatherStatisticsByZipCode(zipCode: Int): FetchDataViewState<ApiWeatherStatisticsEntity> =
        withContext(Dispatchers.IO) {
            val response = weatherStatisticsCall.getWeatherStatisticsByZipCode("$zipCode,us").execute()

            if (response.isSuccessful && response.code() == 200)
                FetchDataViewState.data(response.body()!!)
            else
                FetchDataViewState.failure(response.message())
        }
}
