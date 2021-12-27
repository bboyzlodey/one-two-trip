package com.skarlat.flights.di

import com.skarlat.flights.presentation.flightList.FlightListFragment
import com.skarlat.flights.presentation.flightList.FlightListViewModel
import com.skarlat.flights.presentation.selectedFlight.SelectedFlightFragment
import com.skarlat.flights_api.FlightsApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FlightModule::class])
interface FlightComponent: FlightsApi {
    fun inject(viewModel: FlightListViewModel)
    fun inject(fragment: FlightListFragment)
    fun inject(fragment: SelectedFlightFragment)
}