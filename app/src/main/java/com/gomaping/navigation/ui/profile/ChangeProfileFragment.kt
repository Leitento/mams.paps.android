package com.gomaping.navigation.ui.profile

import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.gomaping.R
import com.gomaping.databinding.FragmentChangeProfileBinding
import com.gomaping.navigation.ui.profile.DialogChoosePhoto.Companion.showChangeAvatarDialog
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

class ChangeProfileFragment : Fragment(R.layout.fragment_change_profile) {
    private val binding by viewBinding(FragmentChangeProfileBinding::bind)
    private val viewModel: ProfileViewModel by viewModels { ProfileViewModel.Factory }
    private var tempFilePhotoUri: Uri? = null
    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                binding.photo.setImageURI(tempFilePhotoUri)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack(R.id.nav_change_profile, true)
            }
            phoneEditText.addTextChangedListener(NumberPhoneTextWatcher("+#(###)###-##-##"))
            buttonSave.setOnClickListener {
                findNavController().popBackStack(R.id.nav_change_profile, true)
            }
            photo.setOnClickListener { callDialog() }
            birthdayEditText.setOnClickListener { createDatePicker() }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect(::handleUiState)
        }
    }

    private fun handleUiState(uiState: UiState) {
        with(binding) {
            photo.load(uiState.photo) {
                placeholder(R.drawable.ic_no_photo)
            }
            nameEditText.inputType = InputType.TYPE_CLASS_TEXT
            nameEditText.setText(uiState.name)
            cityEditText.setText(uiState.town)
            emailEditText.setText(uiState.email)
            phoneEditText.setText(uiState.phone)
            birthdayEditText.setText(uiState.birthday)
        }
    }

    private fun callDialog() {
        val drawable = binding.photo.drawable
        if (drawable == null) {
            showChangeAvatarDialog(ProfilePhoto.ADD_PHOTO) {
                tempFilePhotoUri = getTmpFileUri()
                cameraLauncher.launch(tempFilePhotoUri)
            }
        } else {
            showChangeAvatarDialog(ProfilePhoto.CHANGE_PHOTO) {
                when (it) {
                    ProfilePhoto.CHANGE_PHOTO -> {
                        tempFilePhotoUri = getTmpFileUri()
                        cameraLauncher.launch(tempFilePhotoUri)
                    }

                    ProfilePhoto.DELETE_PHOTO -> {
                        binding.photo.setImageDrawable(null)
                    }

                    else -> {}
                }
            }
        }
    }

    private fun getTmpFileUri(): Uri {
        val tmpFile = File.createTempFile("tmp_image_file", ".jpg", context?.cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }
        return FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            tmpFile
        )
    }

    private fun createDatePicker() {
        val initialDate = System.currentTimeMillis()
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setSelection(initialDate)
            .setTheme(R.style.ThemeOverlay_App_DatePicker)
            .build()
        datePicker.addOnPositiveButtonClickListener { long ->
            val dateChosen = SimpleDateFormat(
                "dd.MM.yyyy",
                Locale("ru")
            ).format(long)
            binding.birthdayEditText.setText(dateChosen)
        }
        datePicker.show(childFragmentManager, "Birthday")
    }
}