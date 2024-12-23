package com.example.kaizenmockapp.util.serializationmanager

import com.example.kaizenmockapp.util.SerializationManager
import org.junit.Assert
import org.junit.Test

class StringToSportTest {

    private val manager = SerializationManager()

    @Test
    fun `test string to storage`() {

        val jsonString = """
            {
                "id": "FOOT",
                "name": "SOCCER"
            }
        """.trimIndent()

        val sport = manager.stringToSport(jsonString)

        Assert.assertEquals("FOOT", sport.id)
        Assert.assertEquals("SOCCER", sport.name)
    }
}