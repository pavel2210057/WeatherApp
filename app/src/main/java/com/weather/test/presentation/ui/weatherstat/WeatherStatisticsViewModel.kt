package com.weather.test.presentation.ui.weatherstat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.test.data.db.repository.DbWeatherStatisticsRepository
import com.weather.test.data.repository.WeatherStatisticsRepository
import com.weather.test.domain.common.formatter.*
import com.weather.test.presentation.common.FetchDataViewState
import com.weather.test.domain.entity.SpeedMeasure
import com.weather.test.domain.entity.TemperatureMeasure
import com.weather.test.domain.entity.WeatherStatisticsEntity
import com.weather.test.domain.value.*
import kotlinx.coroutines.launch

class WeatherStatisticsViewModel(
    private val weatherStatisticsRepository: WeatherStatisticsRepository,
    private val dbWeatherStatisticsRepository: DbWeatherStatisticsRepository,
    private val dateTimeFormatter: DateTimeFormatter
) : ViewModel() {
    private val _weatherStatistics: MutableLiveData<FetchDataViewState<UiWeatherStatisticsEntity>> = MutableLiveData()
    val weatherStatistics: LiveData<FetchDataViewState<UiWeatherStatisticsEntity>> = _weatherStatistics

    private val _temperature: MutableLiveData<Temperature> = MutableLiveData()
    val temperature: LiveData<Temperature> = _temperature

    private val _windSpeed: MutableLiveData<Speed> = MutableLiveData()
    val windSpeed: LiveData<Speed> = _windSpeed

    private var lastZipCode: Int = 0

    fun loadWeatherStatistics(zipCode: Int) {
        _weatherStatistics.value = FetchDataViewState.loading()

        viewModelScope.launch {
            val weatherStatistics = weatherStatisticsRepository.getWeatherStatistics(zipCode)

            _weatherStatistics.value = when (weatherStatistics) {
                is FetchDataViewState.Data -> {
                    lastZipCode = zipCode
                    FetchDataViewState.data(weatherStatistics.data.asUiWeatherStatisticsEntity())
                }
                is FetchDataViewState.Failure ->
                    FetchDataViewState.failure(weatherStatistics.error)
                else -> error("Unexpected state")
            }
        }
    }

    fun changeTemperatureUnits() {
        viewModelScope.launch {
            val currentStatistics = weatherStatistics.value
            if (currentStatistics is FetchDataViewState.Data) {
                val actualTemperature = when(val it = currentStatistics.data.temperature) {
                    is Fahrenheit -> TemperatureMeasure.CELSIUS to it.asCelsius()
                    is Celsius -> TemperatureMeasure.FAHRENHEIT to it.asFahrenheit()
                }

                dbWeatherStatisticsRepository.updateTemperatureMeasure(
                    lastZipCode,
                    actualTemperature.first,
                    actualTemperature.second.value
                )

                currentStatistics.data.temperature = actualTemperature.second
                _temperature.value = actualTemperature.second
            }
        }
    }

    fun changeWindSpeedUnits() {
        viewModelScope.launch {
            val currentStatistics = weatherStatistics.value
            if (currentStatistics is FetchDataViewState.Data) {
                val actualWindSpeed = when(val it = currentStatistics.data.windSpeed) {
                    is MilesPerHour -> SpeedMeasure.KILOMETERS_PER_HOUR to it.asKilometersPerHour()
                    is KilometersPerHour -> SpeedMeasure.MILES_PER_HOUR to it.asMilesPerHour()
                }

                dbWeatherStatisticsRepository.updateWindSpeedMeasure(
                    lastZipCode,
                    actualWindSpeed.first,
                    actualWindSpeed.second.value
                )

                currentStatistics.data.windSpeed = actualWindSpeed.second
                _windSpeed.value = actualWindSpeed.second
            }
        }
    }

    private fun WeatherStatisticsEntity.asUiWeatherStatisticsEntity() = UiWeatherStatisticsEntity(
        location,
        temperature,
        windSpeed,
        humidity,
        visibility,
        dateTimeFormatter.format(sunriseTime),
        dateTimeFormatter.format(sunsetTime)
    )

    data class UiWeatherStatisticsEntity(
        val location: String,
        var temperature: Temperature,
        var windSpeed: Speed,
        val humidity: Int,
        val visibility: Float,
        val formattedSunriseTime: String,
        val formattedSunsetTime: String
    )
}
