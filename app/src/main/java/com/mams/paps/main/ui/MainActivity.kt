package com.mams.paps.main.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mams.paps.R
import com.mams.paps.common.ui.AdaptiveSpacingItemDecoration
import com.mams.paps.databinding.ActivityMainBinding
import com.mams.paps.navigation.ui.NavigationActivity
import com.mams.paps.onboarding.ui.OnboardingActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)
    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }

    private var shouldKeepSplashScreen = true

    private val actionButtonList = listOf(
        ActionButton(
            ACTION_BUTTON_ID_NAVIGATION,
            R.drawable.img_navigator,
            R.string.navigator
        ),
        ActionButton(
            ACTION_BUTTON_ID_EVENTS,
            R.drawable.img_events,
            R.string.events
        ),
        ActionButton(
            ACTION_BUTTON_ID_SERVICES,
            R.drawable.img_services,
            R.string.services
        ),
        ActionButton(
            ACTION_BUTTON_ID_USEFUL,
            R.drawable.img_useful,
            R.string.useful
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen().takeIf { savedInstanceState == null }
        setupEdgeToEdge()
        super.onCreate(savedInstanceState)

        setWindowInsetsListeners()

        splashScreen?.apply {
            setKeepOnScreenCondition {
                shouldKeepSplashScreen
            }
            setOnExitAnimationListener { provider ->
                setupEdgeToEdge()

                val iconView = provider.iconView as ImageView
                iconView.setImageDrawable(binding.logo.drawable)

                val backgroundColor = (provider.view.background as ColorDrawable).color
                val bgFadeAnim = ObjectAnimator.ofArgb(
                    provider.view,
                    "backgroundColor",
                    backgroundColor, 0x00FFFFFF
                ).apply {
                    duration = 250L
                }

                val iconMoveAnim = ObjectAnimator.ofPropertyValuesHolder(
                    iconView,
                    PropertyValuesHolder.ofFloat(
                        View.Y,
                        provider.iconView.y,
                        binding.logo.y + resources.getDimension(R.dimen.main_screen_logo_spacing)
                    ),
                    PropertyValuesHolder.ofFloat(
                        View.SCALE_Y,
                        provider.iconView.height.toFloat() / binding.logo.height,
                        1f
                    ),
                    PropertyValuesHolder.ofFloat(
                        View.SCALE_X,
                        provider.iconView.height.toFloat() / binding.logo.height,
                        1f
                    )
                ).apply {
                    duration = 250L
                }

                AnimatorSet().apply {
                    interpolator = DecelerateInterpolator()

                    play(bgFadeAnim).after(iconMoveAnim)
                    doOnEnd {
                        provider.remove()
                    }
                }.start()
            }
        }

        val adapter = ActionButtonAdapter { actionId ->
            when (actionId) {
                ACTION_BUTTON_ID_NAVIGATION -> {
                    val intent = Intent(this, NavigationActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                ACTION_BUTTON_ID_EVENTS -> {
                    // TODO: Open events screen
                }

                ACTION_BUTTON_ID_SERVICES -> {
                    // TODO: Open services screen
                }

                ACTION_BUTTON_ID_USEFUL -> {
                    // TODO: Open useful screen
                }
            }
        }
        with(binding) {
            actionButtonList.addItemDecoration(
                AdaptiveSpacingItemDecoration(
                    resources.getDimension(R.dimen.common_spacing).toInt()
                )
            )
            actionButtonList.adapter = adapter
            // TODO: Replace placeholders with real data
            locationName.text = "Москва"
            name.text = "Дмитрий"
            avatar.setImageResource(R.drawable.ic_logo)
        }

        adapter.submitList(actionButtonList)

        lifecycleScope.launch {
            withContext(Dispatchers.Main.immediate) {
                viewModel.uiEvent.collect {
                    when (it) {
                        UiEvent.NavigateToHome -> {
                            shouldKeepSplashScreen = false
                        }

                        UiEvent.NavigateToOnboarding -> {
                            val intent = Intent(this@MainActivity, OnboardingActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        }
    }

    private fun setupEdgeToEdge() {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
    }

    private fun setWindowInsetsListeners() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.avatar) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.updateLayoutParams<MarginLayoutParams> {
                topMargin = resources.getDimension(R.dimen.common_spacing).toInt() + insets.top
            }

            windowInsets
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.actionButtonList) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.updatePadding(
                bottom = view.paddingTop + insets.bottom
            )

            windowInsets
        }
    }

    companion object {
        private const val ACTION_BUTTON_ID_NAVIGATION = "navigation"
        private const val ACTION_BUTTON_ID_EVENTS = "events"
        private const val ACTION_BUTTON_ID_SERVICES = "services"
        private const val ACTION_BUTTON_ID_USEFUL = "useful"
    }
}
