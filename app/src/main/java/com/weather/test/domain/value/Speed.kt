package com.weather.test.domain.value

sealed class Speed(val value: Float)

class MilesPerHour(value: Float) : Speed(value) {
    fun asKilometersPerHour(): KilometersPerHour = KilometersPerHour(value * 1.609f)
}

class KilometersPerHour(value: Float) : Speed(value) {
    fun asMilesPerHour(): MilesPerHour = MilesPerHour(value / 1.609f)
}
