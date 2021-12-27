package com.skarlat.flights.data.repository

import com.skarlat.flights.data.model.Flight
import com.skarlat.flights.data.model.PriceFlight

interface IFlightsRepository {
    suspend fun getFlights(): List<Flight>
    suspend fun getFlightPrices(flightId: String): List<PriceFlight>
    suspend fun getFlightInfo(flightId: String) : Flight
}