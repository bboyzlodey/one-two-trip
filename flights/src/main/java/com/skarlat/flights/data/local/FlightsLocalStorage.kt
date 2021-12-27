package com.skarlat.flights.data.local

import com.skarlat.flights.data.model.Flight
import com.skarlat.flights.di.FlightsScope
import javax.inject.Inject

@FlightsScope
class FlightsLocalStorage @Inject constructor() {

    private val flightsCache = mutableListOf<Flight>()

    suspend fun getFlights(): List<Flight> {
        return flightsCache
    }

    suspend fun putFlights(flights: List<Flight>) {
        flightsCache.clear()
        flightsCache.addAll(flights)
    }
}