package com.mams.paps.navigation.ui.map

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mams.paps.R
import com.mams.paps.common.ui.ActionButton
import com.mams.paps.common.ui.ActionButtonAdapter
import com.mams.paps.common.ui.AdaptiveSpacingItemDecoration
import com.mams.paps.databinding.FragmentMapCategoriesBinding
import com.mams.paps.navigation.model.MapCategory

class MapCategoriesFragment : Fragment(R.layout.fragment_map_categories) {

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
            setFragmentResult(REQUEST_KEY_CATEGORY, bundleOf(
                RESULT_KEY_CATEGORY_NAME to actionId
            ))
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

    companion object {
        const val REQUEST_KEY_CATEGORY = "requestKeyCategory"
        const val RESULT_KEY_CATEGORY_NAME = "category"
    }
}
