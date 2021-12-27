package com.skarlat.flights.data.repository

import com.skarlat.flights.data.local.FlightsLocalStorage
import com.skarlat.flights.data.model.Flight
import com.skarlat.flights.data.model.PriceFlight
import com.skarlat.flights.data.network.FlightService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FlightRepository @Inject constructor(
    private val service: FlightService,
    private val localStorage: FlightsLocalStorage
) : IFlightsRepository {


    override suspend fun getFlights(): List<Flight> {
        localStorage.putFlights(service.getFlights() ?: emptyList())
        return localStorage.getFlights()
    }

    override suspend fun getFlightPrices(flightId: String): List<PriceFlight> {
        return localStorage.getFlights().find { it.id == flightId }?.prices ?: emptyList()
    }

    override suspend fun getFlightInfo(flightId: String): Flight {
        return localStorage.getFlights().find { it.id == flightId }!!
    }
}