package com.example.kaizenmockapp.util

import com.example.kaizenmockapp.data.SportDto
import com.example.kaizenmockapp.data.SportEventStorage
import com.example.kaizenmockapp.data.SportStorage
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject

class SerializationManager {

    private val json = Json { ignoreUnknownKeys = true }

    fun deserializeSports(jsonString: String): List<SportDto> {
        val mapOfSports = mutableMapOf<String, SportDto>()

        val rootElement = json.parseToJsonElement(jsonString)
        convertElement(
            rootElement,
            mapOfSports
        )

        return mapOfSports.values.toList()
    }

    fun stringToSport(jsonString: String) = json.decodeFromString<SportStorage>(jsonString)

    fun sportsToString(sports: List<SportStorage>) = sports.map { sport ->
        json.encodeToString(sport)
    }

    fun sportEventsToString(sportEvents: List<SportEventStorage>) = json.encodeToString(sportEvents)

    fun stringToSportEvents(jsonString: String) = json.decodeFromString<List<SportEventStorage>>(jsonString)

    private fun convertElement(
        element: JsonElement,
        mapOfSports: MutableMap<String, SportDto>
    ) {
        if (element !is JsonArray) return


        element.jsonArray.forEach { sportElement ->
            val sportNameElement = sportElement.jsonObject["d"]

            if (sportNameElement !is JsonArray) {
                addSportDtoToMap(sportElement.toString(), mapOfSports)


                return@forEach
            }

            sportNameElement.jsonArray.forEach { nestedSportElement ->
                addSportDtoToMap(nestedSportElement.toString(), mapOfSports)
                //convertElement(nestedSportElement, mapOfSports)
            }
        }
    }

    private fun addSportDtoToMap(jsonString: String, mapOfSports: MutableMap<String, SportDto>) {
        val sport = json.decodeFromString<SportDto>(jsonString)
        if (!mapOfSports.containsKey(sport.id)) {
            mapOfSports[sport.id] = sport
        }
    }
}