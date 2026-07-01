package com.example.myapplication.screen.settings

import androidx.navigation.fragment.findNavController
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentSettingsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {
    private lateinit var firebaseAuth: FirebaseAuth
    private val navController by lazy { findNavController() }
    override fun init() {
        setupScreen()

    }

    private fun setupScreen() {
        firebaseAuth = Firebase.auth
        val email = firebaseAuth.currentUser?.email ?: return navigateToLogin()
        binding.tvSettingEmail.text = email

        binding.btnLogOut.setOnClickListener {
            firebaseAuth.signOut()
            navigateToLogin()
        }

    }

    private fun navigateToLogin() {
        navController.navigate(SettingsFragmentDirections.actionSettingsFragmentToLoginFragment())

    }


}