package com.example.kaizenmockapp.util

import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TimeManager @Inject constructor() {

    fun formatElapsedTime(timeInMilli: Long): String {
        if (timeInMilli < 0) {
            return " --- "
        }

        val timeInSeconds = timeInMilli / 1000

        var seconds = timeInSeconds

        val hours: Long = TimeUnit.SECONDS.toHours(seconds)
        seconds -= TimeUnit.HOURS.toSeconds(hours)

        val minutes: Long = TimeUnit.SECONDS.toMinutes(seconds)
        seconds -= TimeUnit.MINUTES.toSeconds(minutes)

        return String.format(Locale.getDefault(),"%02d:%02d:%02d", hours, minutes, seconds)
    }
}