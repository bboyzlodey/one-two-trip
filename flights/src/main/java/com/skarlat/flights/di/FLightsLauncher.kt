package com.skarlat.flights.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.skarlat.flights.presentation.flightList.FlightListFragment
import com.skarlat.flights_api.FlightsLauncher
import javax.inject.Inject

@FlightsScope
class FLightsLauncher @Inject constructor() : FlightsLauncher {
    override fun openFragment(fragmentManager: FragmentManager) {
    }

    override fun getFragment(): Fragment = FlightListFragment()
}