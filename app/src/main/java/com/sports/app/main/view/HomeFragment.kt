package com.sports.app.main.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.sports.app.R
import com.sports.app.databinding.FragmentHomeBinding
import com.sports.app.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val teamFragment: Fragment by lazy {
        TeamsFragment()
    }
    private val allMatchesFragment: Fragment by lazy {
        AllMatchesFragment()
    }

    lateinit var activeFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        childFragmentManager.beginTransaction()
            .add(R.id.frame_container, allMatchesFragment, AllMatchesFragment::class.java.name)
            .hide(allMatchesFragment).commit()
        childFragmentManager.beginTransaction()
            .add(R.id.frame_container, teamFragment, TeamsFragment::class.java.name)
            .commit()
        activeFragment = teamFragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpEvent()
    }

    private fun setUpEvent() {
        binding.navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_team -> {
                    childFragmentManager.beginTransaction().hide(activeFragment).show(teamFragment).commit()
                    activeFragment = teamFragment
                    true
                }
                R.id.item_all_matches -> {
                    childFragmentManager.beginTransaction().hide(activeFragment).show(allMatchesFragment).commit()
                    activeFragment = allMatchesFragment
                    true
                }
                else -> false
            }
        }
    }
}
