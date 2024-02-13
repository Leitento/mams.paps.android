package com.gomaping.navigation.ui.events

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import com.gomaping.databinding.ItemChooseActionBinding

class DialogChooseAction (context: Context) :
    Dialog(context) {
    init {
        setCancelable(false)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ItemChooseActionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setGravity(Gravity.CENTER_HORIZONTAL)
        binding.ivClose.setOnClickListener {
            dismiss()
        }
    }
}