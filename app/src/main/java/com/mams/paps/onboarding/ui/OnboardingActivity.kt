package com.mams.paps.onboarding.ui

import android.content.Intent
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mams.paps.R
import com.mams.paps.auth.ui.AuthActivity
import com.mams.paps.databinding.ActivityOnboardingBinding

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
        super.onCreate(savedInstanceState)

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
