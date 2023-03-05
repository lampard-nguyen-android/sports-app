package com.sports.app.data.domain

import com.sports.app.data.dto.PreviousMatchDto
import com.sports.app.data.dto.UpcomingMatchDto

data class Match(
    val away: String = "",
    val date: String = "",
    val description: String = "",
    val highlights: String = "",
    val home: String = "",
    val winner: String = "",
    val type: MatchType = MatchType.UPCOMING,
)

enum class MatchType {
    PREVIOUS, UPCOMING
}

fun PreviousMatchDto.toDomain() = Match(
    away = this.away.orEmpty(),
    date = this.date.orEmpty(),
    description = this.description.orEmpty(),
    highlights = this.highlights.orEmpty(),
    home = this.home.orEmpty(),
    winner = this.winner.orEmpty(),
    type = MatchType.PREVIOUS,
)

fun UpcomingMatchDto.toDomain() = Match(
    away = this.away.orEmpty(),
    date = this.date.orEmpty(),
    description = this.description.orEmpty(),
    home = this.home.orEmpty(),
    type = MatchType.UPCOMING,
)
