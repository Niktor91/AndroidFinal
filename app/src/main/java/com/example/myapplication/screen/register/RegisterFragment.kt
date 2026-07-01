package com.example.myapplication.screen.register

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentRegisterBinding
import com.example.myapplication.util.showToast

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val registerService by lazy { RegisterService() }
    private val navController by lazy { findNavController() }

    override fun init() {
        setupButtons()

    }

    private fun setupButtons() {
        binding.btnBack.setOnClickListener {
            navController.navigateUp()
        }
        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val repeatPassword = binding.etRepeatPassword.text.toString()
            if (email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
                showToast(getString(R.string.all_fields_are_required))
            } else if (password != repeatPassword) {
                showToast(getString(R.string.passwords_doesn_t_match))
            } else {
                binding.progressBar.isVisible = true
                registerService.register(
                    email = email,
                    password = password,
                    onSuccess = {
                        binding.progressBar.isVisible = false
                        navController.navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
                    },
                    onError = {
                        binding.progressBar.isVisible = false
                        showToast(it.localizedMessage ?: getString(R.string.register_error))
                    }
                )
            }
        }
    }

}