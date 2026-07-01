package com.example.myapplication.screen.login

import androidx.core.text.buildSpannedString
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.util.showToast

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val loginService by lazy { LoginService() }
    private val navController by lazy { findNavController() }

    override fun init() {
        checkIfAuthorized()
        setupButtons()

    }

    private fun checkIfAuthorized() {
        if (loginService.isAuthorized){
            navController.navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
        }
        

    }

    private fun setupButtons() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isEmpty() && password.isEmpty()){
                showToast(getString(R.string.fields_must_not_be_empty))
            }else if (email.isEmpty()){
                showToast(getString(R.string.email_must_not_be_empty))
            }else if (password.isEmpty()){
                showToast(getString(R.string.password_must_not_be_empty))
            }else{
                binding.progressBar.isVisible = true
                binding.btnLogin.isEnabled = false
                loginService.login(
                    email = email,
                    password = password,
                    onSuccess = {
                        binding.progressBar.isVisible = false
                        binding.btnLogin.isEnabled = true
                        navController.navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                    },
                    onError = {
                        binding.progressBar.isVisible = false
                        binding.btnLogin.isEnabled = true
                        showToast(it.localizedMessage?: getString(R.string.login_error))
                    },
                )
            }
        }

        val registerString  = buildSpannedString {
            append(getString(R.string.sign_up))
            setSpan(
                android.text.style.UnderlineSpan(),
                0,
                length,
                android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        binding.btnRegister.text = registerString

        binding.btnRegister.setOnClickListener {
            navController.navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }



    }


}