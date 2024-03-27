package com.gomaping.navigation.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.gomaping.R
import com.gomaping.databinding.ItemChangePhotoBinding

class DialogChoosePhoto(
    private val action: ProfilePhoto,
    private val changePhotoListener: (ProfilePhoto) -> Unit,) : DialogFragment() {
    private var _binding: ItemChangePhotoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ItemChangePhotoBinding.inflate(inflater, container, false)
        disableItem(action)
        return binding.root
    }

    private fun disableItem(action: ProfilePhoto) = with(binding) {
        when (action) {
            ProfilePhoto.ADD_PHOTO -> {
                changeIconColor(
                    requireContext(),
                    profileChangePhoto,
                    R.drawable.ic_change_photo,
                    R.color.dark_gray
                )
                changeIconColor(
                    requireContext(),
                    profileDeletePhoto,
                    R.drawable.ic_delete_photo,
                    R.color.dark_gray
                )
                binding.profileAddPhoto.setOnClickListener {
                    changePhotoListener(ProfilePhoto.ADD_PHOTO)
                    dismiss()
                }
            }

            ProfilePhoto.CHANGE_PHOTO, ProfilePhoto.DELETE_PHOTO -> {
                changeIconColor(
                    requireContext(),
                    profileAddPhoto,
                    R.drawable.ic_add_photo,
                    R.color.dark_gray
                )
                binding.profileChangePhoto.setOnClickListener {
                    changePhotoListener(ProfilePhoto.CHANGE_PHOTO)
                    dismiss()
                }

                binding.profileDeletePhoto.setOnClickListener {
                    changePhotoListener(ProfilePhoto.DELETE_PHOTO)
                    dismiss()
                }
            }
        }
    }

    private fun changeIconColor(
        context: Context,
        textView: TextView,
        drawableResId: Int,
        colorResId: Int
    ) {
        val drawable = AppCompatResources.getDrawable(context, drawableResId)
        drawable?.let {
            val wrappedDrawable = DrawableCompat.wrap(it).mutate()
            DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(context, colorResId))
            textView.setCompoundDrawablesWithIntrinsicBounds(wrappedDrawable, null, null, null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        fun Fragment.showChangeAvatarDialog(action: ProfilePhoto,changePhotoListener: (ProfilePhoto) -> Unit) {
            DialogChoosePhoto(
                action,
                changePhotoListener = changePhotoListener,
            ).show(childFragmentManager, "Action")
        }
    }
}