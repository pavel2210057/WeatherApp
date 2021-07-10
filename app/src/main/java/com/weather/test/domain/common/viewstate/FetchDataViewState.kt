package com.weather.test.domain.common.viewstate

sealed class FetchDataViewState<T> {
    class Loading<T> : FetchDataViewState<T>()

    class Data<T>(val data: T) : FetchDataViewState<T>()

    class Failure<T>(val error: String) : FetchDataViewState<T>()

    companion object {
        fun<T> loading() = Loading<T>()
        fun<T> data(data: T) = Data(data)
        fun<T> failure(error: String) = Failure<T>(error)
    }
}

