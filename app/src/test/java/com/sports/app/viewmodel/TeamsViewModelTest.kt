package com.sports.app.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.sports.app.CoroutinesTestRule
import com.sports.app.MockData
import com.sports.app.data.NetworkResult
import com.sports.app.data.UiState
import com.sports.app.data.toError
import com.sports.app.main.viewmodel.TeamsViewModel
import com.sports.app.mockFlow
import com.sports.app.repository.SportsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
class TeamsViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @InjectMocks
    private lateinit var viewModel: TeamsViewModel

    private val repository: SportsRepository = mock()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `given repository return success, when VM get list teams, then VM emit ui state success`() =
        runTest {
            // Given
            val mockSuccess = NetworkResult.Success(MockData.ListTeamsMock)
            repository.stub {
                onBlocking { getAllTeams() } doReturn mockFlow { mockSuccess }
            }

            // When
            viewModel.getAllTeams()

            // Then
            viewModel.listTeamsState.test {
                val result = expectMostRecentItem()
                val actual = result?.value()
                assertThat(result is UiState.Success).isTrue()
                assertThat(actual).isEqualTo(MockData.ListTeamsMock)
            }
        }

    @Test
    fun `given repository return error, when VM get list teams, then VM emit ui state error`() =
        runTest {
            // Given
            val mockError = Throwable("Unexpected error").toError()
            val mockErrorState = NetworkResult.Error(mockError)
            repository.stub {
                onBlocking { getAllTeams() } doReturn mockFlow { mockErrorState }
            }

            // When
            viewModel.getAllTeams()

            // Then
            viewModel.listTeamsState.test {
                val result = expectMostRecentItem()
                assertThat(result is UiState.Error).isTrue()
                assertThat(result?.error()).isEqualTo(mockError)
            }
        }
}
