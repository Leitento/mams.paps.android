package com.gomaping.auth.ui

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gomaping.NavGraphAuthDirections
import com.gomaping.R
import com.gomaping.auth.validator.PasswordValidator
import com.gomaping.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel: LoginViewModel by viewModels { LoginViewModel.Factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setWindowInsetsListeners()

        with(binding) {
            buttonContinue.setOnClickListener {
                viewModel.continueAsGuest()
            }
            buttonLogIn.setOnClickListener {
                val email = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()
                viewModel.logIn(email, password)
            }
            passwordEditText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    viewModel.logIn(
                        binding.emailEditText.text.toString(),
                        binding.passwordEditText.text.toString()
                    )
                }
                false
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isAuthenticated.onEach {
                    if (it) navigateToMain()
                }.launchIn(this)

                viewModel.uiEvent.onEach {
                    when (it) {
                        is UiEvent.ErrorInvalidEmail -> {
                            Snackbar.make(view, R.string.error_invalid_email, Snackbar.LENGTH_LONG)
                                .show()
                        }

                        is UiEvent.ErrorEmptyPassword -> {
                            Snackbar.make(view, R.string.error_empty_password, Snackbar.LENGTH_LONG)
                                .show()
                        }

                        is UiEvent.ErrorPasswordTooShort -> {
                            val message = getString(
                                R.string.error_password_too_short,
                                PasswordValidator.MIN_PASSWORD_LENGTH
                            )
                            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
                        }
                    }
                }.launchIn(this)
            }
        }
    }

    private fun setWindowInsetsListeners() {
        ViewCompat.setOnApplyWindowInsetsListener(requireView()) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.updatePadding(
                bottom = insets.bottom
            )

            windowInsets
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.headerImage) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            val topPadding = resources.getDimension(R.dimen.auth_screen_spacing).toInt()
            view.updatePadding(
                top = topPadding + insets.top
            )

            windowInsets
        }
    }

    private fun navigateToMain() {
        val action = NavGraphAuthDirections.actionOpenMainScreen()
        findNavController().navigate(action)
        requireActivity().finish()
    }
}
