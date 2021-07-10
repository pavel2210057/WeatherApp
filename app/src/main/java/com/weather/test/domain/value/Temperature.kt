package com.weather.test.domain.value

sealed class Temperature(val value: Float)

class Fahrenheit(value: Float) : Temperature(value) {
    fun asCelsius(): Celsius = Celsius((value - 32) * 5 / 9)
}

class Celsius(value: Float) : Temperature(value) {
    fun asFahrenheit(): Fahrenheit = Fahrenheit(value * 9 / 5 + 32)
}
