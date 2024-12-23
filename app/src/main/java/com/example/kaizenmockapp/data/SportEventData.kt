package com.example.kaizenmockapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//FIXME try to force time to int when inner events from API
@Serializable
data class SportEventDto(
    @SerialName("i") val id: String,
    @SerialName("si") val sportId: String,
    @SerialName("sh") val name: String,
    @SerialName("tt") val time: Float,
)

@Serializable
data class SportEventStorage(
    val id: String,
    val sportId: String,
    val competitors: List<String>,
    val timeInMillis: Long
)

data class SportEvent(
    val id: String,
    val sportId: String,
    val competitors: List<String>,
    val timeInMillis: Long,
    val favorite: Boolean,
)

fun SportEventDto.toStorage() = SportEventStorage(
    id = id,
    sportId = sportId,
    competitors = name.split("-"),
    timeInMillis = time.toLong() * 1000
)

fun SportEventStorage.toDomain() = SportEvent(
    id = id,
    sportId = sportId,
    competitors = competitors,
    timeInMillis = timeInMillis,
    favorite = false,
)