package com.skarlat.flights.domain.useCase

import com.skarlat.flights.data.model.PriceFlight
import com.skarlat.flights.data.repository.IFlightsRepository
import com.skarlat.flights.domain.PriceType
import com.skarlat.flights.domain.converter.FlightInfoConverter
import com.skarlat.flights.presentation.model.FlightInfo
import javax.inject.Inject

class GetFlightInfoUseCase @Inject constructor(
    private val flightRepository: IFlightsRepository,
    private val flightInfoConverter: FlightInfoConverter
) {
    suspend fun getFlightInfo(flightId: String, priceId: String): FlightInfo {
        val flight = flightRepository.getFlightInfo(flightId = flightId)
        val price = flight.prices.find { it.id == priceId }
        return flightInfoConverter.toUIModel(
            price = price ?: PriceFlight(
                type = PriceType.ECONOM.identifier,
                amount = 0
            ), flight = flight
        )
    }
}