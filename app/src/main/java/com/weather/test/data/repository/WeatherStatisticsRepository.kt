package com.weather.test.data.repository

import com.weather.test.data.db.entity.mapper.asDbWeatherStatisticsEntity
import com.weather.test.data.db.entity.mapper.asWeatherStatisticsEntity
import com.weather.test.data.db.repository.DbWeatherStatisticsRepository
import com.weather.test.data.restapi.entity.mapper.asWeatherStatisticsEntity
import com.weather.test.data.restapi.repository.ApiWeatherStatisticsRepository
import com.weather.test.domain.common.viewstate.FetchDataViewState
import com.weather.test.domain.entity.WeatherStatisticsEntity
import com.weather.test.service.NetworkService

class WeatherStatisticsRepository(
    private val networkService: NetworkService,
    private val apiWeatherStatisticsRepository: ApiWeatherStatisticsRepository,
    private val dbWeatherStatisticsRepository: DbWeatherStatisticsRepository
) {
    suspend fun getWeatherStatistics(zipCode: Int): FetchDataViewState<WeatherStatisticsEntity> =
        if (networkService.isNetworkEnable) {
            when (val fetchedData = apiWeatherStatisticsRepository.getWeatherStatisticsByZipCode(zipCode)) {
                is FetchDataViewState.Data -> {
                    val weatherStatistics = fetchedData.data.asWeatherStatisticsEntity()

                    dbWeatherStatisticsRepository.insertWeatherStatistics(
                        weatherStatistics.asDbWeatherStatisticsEntity(zipCode))

                    FetchDataViewState.data(weatherStatistics)
                }
                is FetchDataViewState.Failure -> FetchDataViewState.failure(fetchedData.error)
                else -> error("Unexpected state")
            }
        } else {
            when (val fetchedData = dbWeatherStatisticsRepository.getWeatherStatisticsByZipCode(zipCode)) {
                is FetchDataViewState.Data -> FetchDataViewState.data(fetchedData.data.asWeatherStatisticsEntity())
                is FetchDataViewState.Failure -> FetchDataViewState.failure(fetchedData.error)
                else -> error("Unexpected state")
            }
        }
}
