package com.mams.paps.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mams.paps.R
import com.mams.paps.auth.ui.AuthActivity
import com.mams.paps.databinding.ActivityOnboardingBinding
import com.mams.paps.ui.main.MainActivity

class OnboardingActivity : AppCompatActivity(R.layout.activity_onboarding) {

    private val binding by viewBinding(ActivityOnboardingBinding::bind)

    private val onboardingStages: List<OnboardingStage> = listOf(
        OnboardingStage(
            R.drawable.img_first_screen_onb,
            R.drawable.ic_dots_state_first,
            R.string.onboarding_first_screen
        ),
        OnboardingStage(
            R.drawable.img_second_screen_onb,
            R.drawable.ic_dots_state_second,
            R.string.onboarding_second_screen
        ),
        OnboardingStage(
            R.drawable.img_third_screen_onb,
            R.drawable.ic_dots_state_third,
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

        binding.screenImage.setImageResource(stage.imageId)
        binding.dotsState.setImageResource(stage.dotsId)
        binding.textScreen.text = getString(stage.textId)
    }

    fun moveToAuth() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }
}
data class OnboardingStage(
    val imageId: Int,
    val dotsId: Int,
    val textId: Int
)
