package com.skarlat.flights.data.network

import com.skarlat.flights.data.model.Flight
import retrofit2.http.GET

interface FlightService {

    @GET("ott/search")
    suspend fun getFlights() : List<Flight>?

}