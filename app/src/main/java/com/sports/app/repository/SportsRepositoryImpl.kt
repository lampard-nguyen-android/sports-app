package com.sports.app.repository

import com.sports.app.data.NetworkResult
import com.sports.app.data.domain.AllMatches
import com.sports.app.data.domain.toDomain
import com.sports.app.service.SportsService
import com.sports.app.service.safeCallApi
import com.sports.app.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SportsRepositoryImpl @Inject constructor(
    private val service: SportsService,
    private val dispatcher: DispatcherProvider,
) : SportsRepository {

    override fun getAllTeams() = flow {
        val result = safeCallApi {
            service.getAllTeams()
        }
        when (result) {
            is NetworkResult.Success -> {
                val teams = result.value.teams?.mapNotNull { it?.toDomain() } ?: emptyList()
                emit(NetworkResult.Success(teams))
            }
            is NetworkResult.Error -> {
                emit(result)
            }
        }
    }.flowOn(dispatcher.io())

    override fun getAllMatches() = flow {
        val result = safeCallApi {
            service.getAllMatches()
        }
        when (result) {
            is NetworkResult.Success -> {
                val allMatches = result.value.matches?.toDomain() ?: AllMatches()
                emit(NetworkResult.Success(allMatches))
            }
            is NetworkResult.Error -> {
                emit(result)
            }
        }
    }.flowOn(dispatcher.io())

    override fun getTeamMatches(teamId: String) = flow {
        val result = safeCallApi {
            service.getTeamMatches(teamId)
        }
        when (result) {
            is NetworkResult.Success -> {
                val allMatches = result.value.matches?.toDomain() ?: AllMatches()
                emit(NetworkResult.Success(allMatches))
            }
            is NetworkResult.Error -> {
                emit(result)
            }
        }
    }.flowOn(dispatcher.io())
}
