package com.sports.app.main.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sports.app.R
import com.sports.app.data.UiState
import com.sports.app.data.domain.AllMatches
import com.sports.app.data.domain.Match
import com.sports.app.data.domain.MatchType
import com.sports.app.data.message
import com.sports.app.databinding.FragmentMatchBinding
import com.sports.app.main.adapter.MatchAdapter
import com.sports.app.main.viewmodel.MatchesViewModel
import com.sports.app.reminder.ReminderManager
import com.sports.app.utils.DateUtils.toDate
import com.sports.app.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MatchFragment : Fragment(R.layout.fragment_match) {
    private val binding by viewBinding(FragmentMatchBinding::bind)
    private val viewModel: MatchesViewModel by viewModels(ownerProducer = { requireParentFragment() })
    private var adapter = MatchAdapter(::onItemHighlightClicked, ::onItemReminderClicked)

    @Inject
    lateinit var reminderManager: ReminderManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
    }

    private fun initView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun observeData() {
        viewModel.allMatchesState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                handleAllMatchesState(state)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleAllMatchesState(state: UiState<AllMatches>?) {
        val type = arguments?.get(ARG_MATCH_TYPE) as MatchType
        when (state) {
            is UiState.Loading -> {
                binding.progressBar.isVisible = true
            }
            is UiState.Success -> {
                binding.progressBar.isVisible = false
                val listMatch =
                    if (type == MatchType.PREVIOUS) state.data.previous else state.data.upcoming
                adapter.submitList(listMatch)
            }
            is UiState.Error -> {
                Toast.makeText(
                    requireContext(),
                    state.error.message(),
                    Toast.LENGTH_SHORT,
                ).show()
                binding.progressBar.isVisible = false
            }
        }
    }

    private fun onItemHighlightClicked(item: Match) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.highlights))
            intent.setDataAndType(Uri.parse(item.highlights), "video/mp4")
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                e.message ?: getString(R.string.error_cannot_play_video),
                Toast.LENGTH_LONG,
            ).show()
        }
    }

    private fun onItemReminderClicked(item: Match) {
        /**
         * Example: val testDate = 2023-03-05T18:57:00.000Z
         */
        item.date.toDate()?.let { date ->
            reminderManager.setReminder(date, item)
        }
    }

    companion object {
        const val ARG_MATCH_TYPE = "ARG_MATCH_TYPE"

        fun newInstance(type: MatchType) = MatchFragment().apply {
            arguments = bundleOf(
                ARG_MATCH_TYPE to type,
            )
        }
    }
}
