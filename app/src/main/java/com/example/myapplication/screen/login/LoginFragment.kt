package com.example.myapplication.screen.login

import androidx.core.text.buildSpannedString
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.retrofit.RetrofitProvider
import com.example.myapplication.util.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val loginService by lazy { LoginService() }
    private val navController by lazy { findNavController() }
    private val posterAdapter by lazy { MoviePosterAdapter() }

    override fun init() {
        setupButtons()
        setupMovieTape()
    }

    private fun setupMovieTape() {
        binding.rvMovieTape.apply {
            adapter = posterAdapter
            // Start from a middle position to allow scrolling in both directions if needed, 
            // though we mostly care about right-to-left.
            scrollToPosition(Int.MAX_VALUE / 2)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitProvider.movieService.getMovies()
                }
                if (response.isSuccessful) {
                    response.body()?.results?.let { movies ->
                        posterAdapter.submitList(movies)
                        startAutoScroll()
                    }
                }
            } catch (e: Exception) {
                // Ignore background errors
            }
        }
    }

    private fun startAutoScroll() {
        viewLifecycleOwner.lifecycleScope.launch {
            while (true) {
                delay(20)
                binding.rvMovieTape.scrollBy(2, 0)
            }
        }
    }

    private fun checkIfAuthorized() {
        if (loginService.isAuthorized) {
            navController.navigate(
                LoginFragmentDirections.actionLoginFragmentToHomeFragment()
            )
        }
    }

    private fun setupButtons() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            when {
                email.isEmpty() && password.isEmpty() -> {
                    showToast(getString(R.string.fields_must_not_be_empty))
                }

                email.isEmpty() -> {
                    showToast(getString(R.string.email_must_not_be_empty))
                }

                password.isEmpty() -> {
                    showToast(getString(R.string.password_must_not_be_empty))
                }

                else -> {
                    binding.progressBar.isVisible = true
                    binding.btnLogin.isEnabled = false

                    loginService.login(
                        email = email,
                        password = password,
                        onSuccess = {
                            binding.progressBar.isVisible = false
                            binding.btnLogin.isEnabled = true
                            navController.navigate(
                                LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                            )
                        },
                        onError = {
                            binding.progressBar.isVisible = false
                            binding.btnLogin.isEnabled = true
                            showToast(
                                it.localizedMessage ?: getString(R.string.login_error)
                            )
                        }
                    )
                }
            }
        }

        val registerString = buildSpannedString {
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
            navController.navigate(
                LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            )
        }
    }
}