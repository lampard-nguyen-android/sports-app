package com.sports.app.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.sports.app.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
        // TODO: Track screen destination
    }

    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the splash screen transition.
        val splashScreen = installSplashScreen()
        var shouldKeepOnScreen = true
        splashScreen.setKeepOnScreenCondition { shouldKeepOnScreen }

        lifecycleScope.launch {
            delay(1500)
            shouldKeepOnScreen = false
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.nav_host)
        navController?.setGraph(R.navigation.home_nav, null)
    }
    override fun onResume() {
        super.onResume()
        navController?.addOnDestinationChangedListener(listener)
    }

    override fun onPause() {
        super.onPause()
        navController?.removeOnDestinationChangedListener(listener)
    }
}
