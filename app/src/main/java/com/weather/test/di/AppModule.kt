package com.weather.test.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.weather.test.BuildConfig
import com.weather.test.data.db.WeatherDatabase
import com.weather.test.data.db.repository.DbWeatherStatisticsRepository
import com.weather.test.data.repository.WeatherStatisticsRepository
import com.weather.test.data.restapi.call.WeatherStatisticsCall
import com.weather.test.data.restapi.interceptor.ApiInterceptor
import com.weather.test.data.restapi.repository.ApiWeatherStatisticsRepository
import com.weather.test.domain.common.formatter.*
import com.weather.test.presentation.ui.weatherstat.WeatherStatisticsViewModel
import com.weather.test.service.NetworkService
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val appModule = module {
    single {
        val connectivityManager = (androidContext().getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager)
        NetworkService(connectivityManager)
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(ApiInterceptor())
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single { (get() as Retrofit).create<WeatherStatisticsCall>() }
    single { ApiWeatherStatisticsRepository(get()) }

    single {
        Room.databaseBuilder(androidContext(), WeatherDatabase::class.java, "weather-db")
            .build()
    }
    single { DbWeatherStatisticsRepository(get()) }

    single { WeatherStatisticsRepository(get(), get(), get()) }

    single { DateTimeFormatter() }

    viewModel { WeatherStatisticsViewModel(get(), get(), get()) }
}
