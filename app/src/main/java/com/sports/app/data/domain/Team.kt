package com.sports.app.data.domain

import com.sports.app.data.dto.TeamDto
import java.io.Serializable

data class Team(
    val id: String = "",
    val logo: String = "",
    val name: String = "",
) : Serializable

fun TeamDto.toDomain() = Team(
    id = id.orEmpty(),
    logo = logo.orEmpty(),
    name = name.orEmpty(),
)
