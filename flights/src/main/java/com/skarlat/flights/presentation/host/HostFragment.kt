package com.skarlat.flights.presentation.host

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.skarlat.flights.R
import com.skarlat.flights.presentation.flightList.FlightListFragment

class HostFragment : Fragment(R.layout.fragment_host) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.beginTransaction()
            .add(R.id.fragmentHost, FlightListFragment.newInstance(null), "flight_list")
            .addToBackStack(null)
            .commit()
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (childFragmentManager.backStackEntryCount > 1)
                        childFragmentManager.popBackStack()
                    else
                        parentFragmentManager.popBackStack()
                }
            })
    }
}