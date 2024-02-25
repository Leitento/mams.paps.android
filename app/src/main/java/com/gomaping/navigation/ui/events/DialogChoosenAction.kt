package com.gomaping.navigation.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.gomaping.databinding.ItemChooseActionBinding

class DialogChooseAction: DialogFragment() {

    private var _binding: ItemChooseActionBinding? = null
    private val binding get() = _binding!!

    init {
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ItemChooseActionBinding.inflate(inflater, container, false)
        binding.ivClose.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}