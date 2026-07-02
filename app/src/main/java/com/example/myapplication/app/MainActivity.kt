package com.example.myapplication.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSystemBars()
        setupNavigation()
        setupBottomMenu()
    }

    private fun setupSystemBars() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.md_theme_background)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.md_theme_background)

        val controller = WindowCompat.getInsetsController(window, window.decorView)
        controller.isAppearanceLightStatusBars = false
        controller.isAppearanceLightNavigationBars = false
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val showBottomMenu = destination.id == R.id.homeFragment || destination.id == R.id.settingsFragment
            binding.bottomMenu.isVisible = showBottomMenu
            updateBottomMenuColors(destination.id)
        }
    }

    private fun setupBottomMenu() {
        binding.navHome.setOnClickListener {
            if (navController.currentDestination?.id != R.id.homeFragment) {
                navController.navigate(R.id.homeFragment)
            }
        }

        binding.navSettings.setOnClickListener {
            if (navController.currentDestination?.id != R.id.settingsFragment) {
                navController.navigate(R.id.settingsFragment)
            }
        }
    }

    private fun updateBottomMenuColors(destinationId: Int) {
        val active = ContextCompat.getColor(this, R.color.movie_accent)
        val inactive = ContextCompat.getColor(this, R.color.icon_inactive)

        binding.navHome.setTextColor(if (destinationId == R.id.homeFragment) active else inactive)
        binding.navSettings.setTextColor(if (destinationId == R.id.settingsFragment) active else inactive)
    }
}
