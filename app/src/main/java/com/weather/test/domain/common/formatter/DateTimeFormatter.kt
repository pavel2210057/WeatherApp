package com.weather.test.domain.common.formatter

import java.text.SimpleDateFormat
import java.util.*

class DateTimeFormatter {
    fun format(date: Date): String = SimpleDateFormat("d/MM/yyyy h:mm:ss a 'UTC'").format(date)
}
