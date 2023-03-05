package com.sports.app.main.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.sports.app.R
import com.sports.app.data.domain.MatchType
import com.sports.app.data.domain.Team
import com.sports.app.databinding.FragmentAllMatchesBinding
import com.sports.app.main.viewmodel.MatchesViewModel
import com.sports.app.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllMatchesFragment : Fragment(R.layout.fragment_all_matches) {

    private val binding by viewBinding(FragmentAllMatchesBinding::bind)
    private val viewModel by viewModels<MatchesViewModel>()
    private val team by lazy {
        arguments?.get(ARG_TEAM_ID) as? Team
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (team != null) {
            binding.toolbar.isVisible = true
            binding.toolbar.title = team?.name
            binding.toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        binding.pager.adapter = MatchesPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            when (position) {
                0 -> tab.setText(R.string.text_previous_matches)
                1 -> tab.setText(R.string.text_upcoming_matches)
            }
        }.attach()
        viewModel.getAllMatches(team?.id)
    }

    inner class MatchesPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount() = 2

        override fun createFragment(position: Int): Fragment {
            return MatchFragment.newInstance(MatchType.values()[position])
        }
    }

    companion object {
        const val ARG_TEAM_ID = "ARG_TEAM_ID"
    }
}
