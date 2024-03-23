package com.gomaping.navigation.ui.map.bottomsheetfragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gomaping.R
import com.gomaping.databinding.FragmentLocationBottomSheetBinding
import com.gomaping.navigation.ui.events.eventcard.ImageModel
import com.gomaping.navigation.ui.map.MapViewModel
import com.gomaping.navigation.ui.map.adapter.LocationBottomSheetFilterAdapter
import com.gomaping.navigation.ui.map.adapter.LocationBottomSheetPhotoAdapter
import com.gomaping.navigation.ui.map.adapter.OnClickLocationListener
import com.gomaping.navigation.ui.map.model.MapFilter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LocationBottomSheetFragment : BottomSheetDialogFragment(R.layout.fragment_location_bottom_sheet) {
    private val viewModel: MapViewModel by viewModels { MapViewModel.Factory }
    private val binding by viewBinding(FragmentLocationBottomSheetBinding::bind)
    private val adapter: LocationBottomSheetPhotoAdapter by lazy { LocationBottomSheetPhotoAdapter(getPhoto(), object : OnClickLocationListener{
        override fun onClickLocation() {
            findNavController().navigate(R.id.action_locationBottomSheetFragment_to_locationCardFragment)
        }
    }) }
    private val filterAdapter: LocationBottomSheetFilterAdapter by lazy { LocationBottomSheetFilterAdapter(getFilterPhoto()) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.filterPhoto.adapter = adapter
        binding.filterLocation.adapter = filterAdapter
        binding.locationClose.setOnClickListener {
            dismiss()
        }
    }

    private fun getPhoto(): List<ImageModel> {
        return listOf(
            ImageModel(R.drawable.playground_one),
            ImageModel(R.drawable.playground_three),
            ImageModel(R.drawable.playground_two),
            ImageModel(R.drawable.playground_four),
            ImageModel(R.drawable.playground_five),
        )
    }

    private fun getFilterPhoto(): List<MapFilter> {
        return viewModel.getListOfKeyIsNonEmpty()
    }
}