package com.sports.app.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sports.app.data.NetworkResult
import com.sports.app.data.UiState
import com.sports.app.data.domain.AllMatches
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
class MatchesViewModel @Inject constructor(
    private val repository: SportsRepository,
) : ViewModel() {

    private val _allMatchesState = MutableStateFlow<UiState<AllMatches>?>(null)
    val allMatchesState = _allMatchesState.asStateFlow()

    fun getAllMatches(teamId: String? = null) {
        viewModelScope.launch {
            val allMatchFlows =
                if (teamId != null) repository.getTeamMatches(teamId) else repository.getAllMatches()

            allMatchFlows
                .onStart { _allMatchesState.emit(UiState.Loading()) }
                .catch { _allMatchesState.emit(UiState.Error(it.toError())) }
                .collect { result ->
                    when (result) {
                        is NetworkResult.Success -> {
                            _allMatchesState.emit(UiState.Success(result.value))
                        }
                        is NetworkResult.Error -> {
                            _allMatchesState.emit(UiState.Error(result.errorType))
                        }
                    }
                }
        }
    }
}
