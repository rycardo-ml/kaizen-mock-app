package com.example.kaizenmockapp.util

import org.junit.Assert
import org.junit.Test
import java.util.Calendar

class TimeManagerTest {

    private val manager = TimeManager()

    @Test
    fun formatElapsedTime() {
        val currentTime = Calendar.getInstance()
        val testTime = Calendar.getInstance()

        testTime.timeInMillis = currentTime.timeInMillis
        Assert.assertEquals(manager.formatElapsedTime(testTime.timeInMillis - currentTime.timeInMillis), "00:00:00")

        testTime.add(Calendar.SECOND, 30)
        Assert.assertEquals(manager.formatElapsedTime(testTime.timeInMillis - currentTime.timeInMillis), "00:00:30")

        testTime.add(Calendar.MINUTE, 15)
        Assert.assertEquals(manager.formatElapsedTime(testTime.timeInMillis - currentTime.timeInMillis), "00:15:30")

        testTime.add(Calendar.HOUR, 3)
        Assert.assertEquals(manager.formatElapsedTime(testTime.timeInMillis - currentTime.timeInMillis), "03:15:30")

        testTime.add(Calendar.DATE, 1)
        Assert.assertEquals(manager.formatElapsedTime(testTime.timeInMillis - currentTime.timeInMillis), "27:15:30")

        testTime.add(Calendar.DATE, 10)
        Assert.assertEquals(manager.formatElapsedTime(testTime.timeInMillis - currentTime.timeInMillis), "267:15:30")
    }
}