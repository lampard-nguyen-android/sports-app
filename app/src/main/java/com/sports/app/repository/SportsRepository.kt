package com.sports.app.repository

import com.sports.app.data.NetworkResult
import com.sports.app.data.domain.AllMatches
import com.sports.app.data.domain.Team
import kotlinx.coroutines.flow.Flow

interface SportsRepository {

    fun getAllTeams(): Flow<NetworkResult<List<Team>>>

    fun getAllMatches(): Flow<NetworkResult<AllMatches>>

    fun getTeamMatches(teamId: String): Flow<NetworkResult<AllMatches>>
}
