package com.sports.app.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sports.app.data.NetworkResult
import com.sports.app.data.UiState
import com.sports.app.data.domain.Team
import com.sports.app.data.toError
import com.sports.app.repository.SportsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamsViewModel @Inject constructor(
    private val repository: SportsRepository,
) : ViewModel() {
    private val _listTeamsState = MutableStateFlow<UiState<List<Team>>?>(null)
    val listTeamsState = _listTeamsState.asStateFlow()

    fun getAllTeams() {
        viewModelScope.launch {
            repository.getAllTeams()
                .onStart { _listTeamsState.emit(UiState.Loading()) }
                .catch { _listTeamsState.emit(UiState.Error(it.toError())) }
                .collect { result ->
                    when (result) {
                        is NetworkResult.Success -> {
                            _listTeamsState.emit(UiState.Success(result.value))
                        }
                        is NetworkResult.Error -> {
                            _listTeamsState.emit(UiState.Error(result.errorType))
                        }
                    }
                }
        }
    }
}
