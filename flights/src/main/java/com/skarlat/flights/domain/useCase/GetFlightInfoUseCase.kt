package com.skarlat.flights.domain.useCase

import com.skarlat.flights.data.repository.IFlightsRepository
import com.skarlat.flights.domain.TripType
import com.skarlat.flights.presentation.model.FlightInfo
import java.util.*
import javax.inject.Inject

class GetFlightInfoUseCase @Inject constructor(
    private val flightRepository: IFlightsRepository,
) {
    suspend fun getFlightInfo(flightId: String, tripTypeId: String): FlightInfo {
        val flight = flightRepository.getFlightInfo(flightId = flightId)
        val price = flight.prices.find { it.id == tripTypeId }
        return FlightInfo(
            id = UUID.randomUUID().toString(),
            amount = "Стоимость: ${price?.amount}",
            tripCount = "Количество пересадок: ${flight.trips.count()}",
            from = flight.trips.firstOrNull()?.from
                ?: "Undefined",
            to = flight.trips.lastOrNull()?.from
                ?: "Undefined",
            tripType = when (TripType.fromIdentifier(price?.type!!)) {
                TripType.BUSINESS -> "Бизнес"
                TripType.ECONOM -> "Эконом"
                else -> ""
            }
        )
    }
}