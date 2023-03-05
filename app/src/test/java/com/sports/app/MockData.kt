package com.sports.app

import com.sports.app.data.domain.AllMatches
import com.sports.app.data.domain.Match
import com.sports.app.data.domain.MatchType
import com.sports.app.data.domain.Team

object MockData {
    val ListTeamsMock = Array(10) {
        Team()
    }.toList()

    val AllMatchesMock = AllMatches(
        previous = Array(10) {
            Match(
                type = MatchType.PREVIOUS,
            )
        }.toList(),
        upcoming = Array(10) {
            Match(
                type = MatchType.UPCOMING,
            )
        }.toList(),

    )
}
