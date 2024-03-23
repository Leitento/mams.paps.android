package com.gomaping.onboarding.ui

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gomaping.R
import com.gomaping.auth.ui.AuthActivity
import com.gomaping.databinding.ActivityOnboardingBinding
import com.gomaping.navigation.ui.events.SharedPrefUtils

class OnboardingActivity : AppCompatActivity(R.layout.activity_onboarding) {

    private val binding by viewBinding(ActivityOnboardingBinding::bind)

    private val onboardingStages: List<OnboardingStage> = listOf(
        OnboardingStage(
            R.drawable.img_onboarding_1,
            R.drawable.ic_onboarding_dots_1,
            R.string.onboarding_first_screen
        ),
        OnboardingStage(
            R.drawable.img_onboarding_2,
            R.drawable.ic_onboarding_dots_2,
            R.string.onboarding_second_screen
        ),
        OnboardingStage(
            R.drawable.img_onboarding_3,
            R.drawable.ic_onboarding_dots_3,
            R.string.onboarding_third_screen
        ),
    )

    private var currentStageIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
        super.onCreate(savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.updatePadding(
                top = insets.top,
                bottom = insets.bottom
            )

            windowInsets
        }

        binding.buttonToNextScreen.setOnClickListener {
            if (currentStageIndex < onboardingStages.size - 1) {
                currentStageIndex++
                setupOnboardingStage(currentStageIndex)
            } else {
                moveToAuth()
            }
        }
        setupOnboardingStage(currentStageIndex)
    }
    private fun setupOnboardingStage(index: Int) {
        val stage = onboardingStages[index]

        binding.screenImage.setImageResource(stage.imageResId)
        binding.dotsState.setImageResource(stage.dotsResId)
        binding.textScreen.text = getString(stage.textResId)
    }

    fun moveToAuth() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }
}

data class OnboardingStage(
    @DrawableRes val imageResId: Int,
    @DrawableRes val dotsResId: Int,
    @StringRes val textResId: Int
)
