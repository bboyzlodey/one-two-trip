package com.skarlat.flights_api

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface FlightsLauncher {
    fun openFragment(fragmentManager: FragmentManager)
    fun getFragment() : Fragment
}