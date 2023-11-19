package com.mams.paps.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mams.paps.R

class ThirdOnboardingFragment : Fragment(R.layout.third_onboarding_screen) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_to_authorization_screen).setOnClickListener {
            findNavController().navigate(R.id.action_to_authorization_screen)
        }
    }
}
