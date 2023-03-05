package com.sports.app.service

import com.sports.app.data.dto.MatchesResponseDto
import com.sports.app.data.dto.TeamListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SportsService {

    @GET("/teams")
    suspend fun getAllTeams(): Response<TeamListDto>

    @GET("/teams/matches")
    suspend fun getAllMatches(): Response<MatchesResponseDto>

    @GET("/teams/{id}/matches")
    suspend fun getTeamMatches(
        @Path("id") teamId: String,
    ): Response<MatchesResponseDto>
}
