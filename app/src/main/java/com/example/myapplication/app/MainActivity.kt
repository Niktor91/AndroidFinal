package com.example.myapplication.app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())

            v.setPadding(systemBars.left, systemBars.top, systemBars.right, maxOf(systemBars.bottom,imeInsets.bottom))
            insets
        }

        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        navController.graph = navController.navInflater.inflate(R.navigation.nav_graph)

        binding.bottomMenu.setupWithNavController(navController)
        binding.bottomMenu.setOnApplyWindowInsetsListener(null)

        binding.bottomMenu.setOnItemReselectedListener { }

        binding.bottomMenu.setOnItemSelectedListener { item ->
            navController.navigate(
                item.itemId,
                null,
                NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .setPopUpTo(
                        R.id.homeFragment,
                        inclusive = false,
                        saveState = true
                    )
                    .setRestoreState(true)
                    .build()
            )
            true
        }

        appBarConfiguration = AppBarConfiguration(
            getVisibleNavFragmentIds().toSet()
        )

        setBottomNavBarVisibility()
    }

    private fun getVisibleNavFragmentIds(): List<Int> {
        return listOf(
            R.id.homeFragment,
            R.id.settingsFragment
        )
    }
    private fun setBottomNavBarVisibility() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomMenu.isVisible = destination.id in getVisibleNavFragmentIds()

        }
    }

}