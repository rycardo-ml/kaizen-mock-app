package com.example.kaizenmockapp.data

import com.example.kaizenmockapp.util.AsyncParam
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SportDto(
    @SerialName("i") val id: String,
    @SerialName("d") val name: String,
    @SerialName("e") val events: List<SportEventDto>,
)

@Serializable
data class SportStorage(
    val id: String,
    val name: String,
)

data class Sport(
    val id: String,
    val name: String,
)


fun SportDto.toStorage() = SportStorage(
    id = id,
    name = name,
)

fun SportStorage.toDomain() = Sport(
    id = id,
    name = name,
)