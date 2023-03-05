package com.sports.app.main.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sports.app.R
import com.sports.app.data.UiState
import com.sports.app.data.domain.Team
import com.sports.app.data.message
import com.sports.app.databinding.FragmentTeamsBinding
import com.sports.app.main.adapter.TeamsAdapter
import com.sports.app.main.viewmodel.TeamsViewModel
import com.sports.app.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class TeamsFragment : Fragment(R.layout.fragment_teams) {

    private val binding by viewBinding(FragmentTeamsBinding::bind)
    private val viewModel by viewModels<TeamsViewModel>()
    private var adapter = TeamsAdapter(::onItemClicked)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAllTeams()
    }

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
        viewModel.listTeamsState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                when (state) {
                    is UiState.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is UiState.Success -> {
                        binding.progressBar.isVisible = false
                        adapter.submitList(state.data)
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
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun onItemClicked(item: Team) {
        val bundle = bundleOf(
            AllMatchesFragment.ARG_TEAM_ID to item,
        )
        findNavController(requireParentFragment()).navigate(R.id.AllMatchesFragment, bundle)
    }
}
