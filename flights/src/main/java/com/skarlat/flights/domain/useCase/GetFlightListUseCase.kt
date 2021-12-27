package com.skarlat.flights.domain.useCase

import com.skarlat.flights.data.repository.IFlightsRepository
import com.skarlat.flights.domain.converter.FlightConverter
import com.skarlat.flights.presentation.model.FlightUI
import javax.inject.Inject

class GetFlightListUseCase @Inject constructor(
    private val flightRepository: IFlightsRepository,
    private val flightConverter: FlightConverter
) {

    suspend fun getFlightList(): List<FlightUI> {
        return flightRepository.getFlights().map(flightConverter::toUIModel)
    }

}