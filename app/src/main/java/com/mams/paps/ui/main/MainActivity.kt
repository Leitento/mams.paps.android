package com.mams.paps.ui.main

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mams.paps.R
import com.mams.paps.databinding.ActivityMainBinding
import com.mams.paps.ui.AdaptiveSpacingItemDecoration

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)

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
        installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.guidelineTop) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            binding.guidelineTop.setGuidelineBegin(insets.top)

            windowInsets
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.actionButtonList) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.setPadding(
                view.paddingLeft, view.paddingTop,
                view.paddingRight, view.paddingTop + insets.bottom
            )

            windowInsets
        }

        val adapter = ActionButtonAdapter { actionId ->
            when (actionId) {
                ACTION_BUTTON_ID_NAVIGATION -> {
                    // TODO: Open navigation screen
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
    }

    companion object {
        private const val ACTION_BUTTON_ID_NAVIGATION = "navigation"
        private const val ACTION_BUTTON_ID_EVENTS = "events"
        private const val ACTION_BUTTON_ID_SERVICES = "services"
        private const val ACTION_BUTTON_ID_USEFUL = "useful"
    }
}
