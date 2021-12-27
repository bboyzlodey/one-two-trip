package com.skarlat.flights.domain.useCase

import com.skarlat.core.util.dialog.SingleChoiceItem
import com.skarlat.flights.data.repository.FlightRepository
import com.skarlat.flights.di.FlightsScope
import com.skarlat.flights.domain.converter.FlightPriceConverter
import com.skarlat.flights.presentation.model.FlightUI
import javax.inject.Inject

@FlightsScope
class GetFlightPrices @Inject constructor(
    private val flightRepository: FlightRepository,
    private val converter: FlightPriceConverter
) {
    suspend fun getFlightPrices(flight: FlightUI): List<SingleChoiceItem> {
        val info = flightRepository.getFlightPrices(flight.id)
        return info.map(converter::toUIModel)
    }
}