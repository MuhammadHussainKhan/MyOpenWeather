package com.example.myopenweather.domain.utils

import android.os.Build
import androidx.annotation.RequiresApi
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale

object DateUtil {
    fun String.toFormattedDate(): String {
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("MMM d yyyy", Locale.getDefault())

        try {
            val date = inputDateFormat.parse(this)
            if (date != null) {
                return outputDateFormat.format(date)
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
        return this
    }

    fun String.toFormattedDay(): String? {
        val dateComponents = this.split("-")
        return if (dateComponents.size == 3) {
            val year = dateComponents[0].toInt()
            val month = dateComponents[1].toInt() - 1
            val day = dateComponents[2].toInt()

            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            val outputDateFormat = SimpleDateFormat("EE", Locale.getDefault())
            return outputDateFormat.format(calendar.time)
        } else null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun String.toFormattedFullSpellDay(): String? {
        return try {
            // Define the date formatter for the input string
            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

            // Parse the input string to LocalDate
            val date = LocalDate.parse(this, inputFormatter)

            // Get the day of the week and return its full name in the default locale
            date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
        } catch (e: DateTimeParseException) {
            // Return null if the date format is incorrect
            null
        }
    }
}
