package com.sports.app.data.domain

import com.sports.app.data.dto.AllMatchesDto

data class AllMatches(
    val previous: List<Match> = emptyList(),
    val upcoming: List<Match> = emptyList(),
)

fun AllMatchesDto.toDomain() = AllMatches(
    previous = this.previous?.mapNotNull { it?.toDomain() } ?: emptyList(),
    upcoming = this.upcoming?.mapNotNull { it?.toDomain() } ?: emptyList(),
)
