package com.gomaping.navigation.ui.map.bottomsheetfragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gomaping.R
import com.gomaping.common.ui.ActionButton
import com.gomaping.common.ui.ActionButtonAdapter
import com.gomaping.common.ui.AdaptiveSpacingItemDecoration
import com.gomaping.databinding.FragmentMapCategoriesBinding
import com.gomaping.navigation.model.MapCategory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MapCategoriesFragment : BottomSheetDialogFragment(R.layout.fragment_map_categories) {

    private val actionButtonList = listOf(
        ActionButton(
            MapCategory.PLAYGROUND.name,
            R.drawable.img_playground,
            R.string.category_playground
        ),
        ActionButton(
            MapCategory.FOOD.name,
            R.drawable.img_food,
            R.string.category_food
        ),
        ActionButton(
            MapCategory.DEVELOPMENT.name,
            R.drawable.img_development,
            R.string.category_development
        ),
        ActionButton(
            MapCategory.EDUCATION.name,
            R.drawable.img_education,
            R.string.category_education
        ),
        ActionButton(
            MapCategory.HEALTH.name,
            R.drawable.img_health,
            R.string.category_health
        ),
        ActionButton(
            MapCategory.SHOP.name,
            R.drawable.img_shop,
            R.string.category_shop
        )
    )

    private val binding by viewBinding(FragmentMapCategoriesBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val itemHeight = resources.getDimensionPixelSize(R.dimen.categories_action_button_size)
        val adapter = ActionButtonAdapter(itemHeight) { actionId ->

            when (actionId) {
                MapCategory.PLAYGROUND.name -> {
                    findNavController().navigate(R.id.action_mapCategoriesFragment2_to_playgroundFilterFragment2)
                }
            }
        }
        with(binding) {
            categoriesList.addItemDecoration(
                AdaptiveSpacingItemDecoration(
                    resources.getDimension(R.dimen.common_spacing).toInt()
                )
            )
            categoriesList.adapter = adapter
        }
        adapter.submitList(actionButtonList)
    }
}
