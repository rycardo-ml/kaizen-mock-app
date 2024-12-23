package com.example.kaizenmockapp.util.serializationmanager

import com.example.kaizenmockapp.data.SportStorage
import com.example.kaizenmockapp.util.SerializationManager
import org.junit.Assert
import org.junit.Test

class SportsToStringTest {

    private val manager = SerializationManager()

    @Test
    fun `test list storage to list string`() {
        val sports = listOf(
            SportStorage(
                id = "FOOT",
                name = "SOCCER",
            ),
            SportStorage(
                id = "TT",
                name = "TABLE TENNIS",
            )
        )

        val jsonStrings = manager.sportsToString(sports)
        Assert.assertEquals(sports.size, jsonStrings.size)
    }
}