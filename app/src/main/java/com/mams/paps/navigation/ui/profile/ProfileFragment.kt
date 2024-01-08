package com.mams.paps.navigation.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.ActivityNavigator
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.mams.paps.NavMainDirections
import com.mams.paps.R
import com.mams.paps.databinding.FragmentProfileBinding
import kotlinx.coroutines.launch


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel: ProfileViewModel by viewModels { ProfileViewModel.Factory }

    private val menuClickListener = ProfileMenuItemClickListener { profileMenuItem, _ ->
        when (profileMenuItem.titleResId) {
            R.string.profile_menu_favourites -> {}
            R.string.profile_menu_notifications -> {}
            R.string.profile_menu_public_offer -> {}
            R.string.profile_menu_about_app -> {}
            R.string.profile_menu_support -> {}
            R.string.profile_menu_log_out -> {
                viewModel.logOut()
            }
        }
    }

    private val menuItems: List<ProfileMenuItem> = listOf(
        ProfileMenuItem(
            R.string.profile_menu_favourites,
            R.drawable.ic_favourites
        ),
        ProfileMenuItem(
            R.string.profile_menu_notifications,
            R.drawable.ic_notifications
        ),
        ProfileMenuItem(
            R.string.profile_menu_public_offer,
            R.drawable.ic_document
        ),
        ProfileMenuItem(
            R.string.profile_menu_about_app,
            R.drawable.ic_about
        ),
        ProfileMenuItem(
            R.string.profile_menu_support,
            R.drawable.ic_support
        ),
        ProfileMenuItem(
            R.string.profile_menu_log_out,
            R.drawable.ic_profile_logout,
            R.color.blue
        )
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = ProfileMenuAdapter(menuClickListener)

        with(binding) {
            becomeAgentButton.setOnClickListener {
                Toast.makeText(requireContext(), R.string.profile_become_agent, Toast.LENGTH_SHORT)
                    .show()
            }

            menuRecyclerView.adapter = adapter
            adapter.submitList(menuItems)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect(::handleUiState)
                }

                launch {
                    viewModel.uiEvent.collect {
                        when (it) {
                            UiEvent.NavigateToAuth -> {
                                val extras = ActivityNavigator.Extras.Builder()
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                    .build()

                                val action = NavMainDirections.actionOpenAuthScreen()
                                findNavController().navigate(action, extras)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun handleUiState(uiState: UiState) {
        with(binding) {
            photo.load(uiState.photo) {
                placeholder(R.drawable.ic_no_photo)
            }
            name.text = uiState.name
            town.text = uiState.town
            email.text = uiState.email
        }
    }
}
