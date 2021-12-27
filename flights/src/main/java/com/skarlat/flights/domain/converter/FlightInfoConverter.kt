package com.skarlat.flights.domain.converter

import com.skarlat.flights.data.model.Flight
import com.skarlat.flights.data.model.PriceFlight
import com.skarlat.flights.di.FlightsScope
import com.skarlat.flights.presentation.model.FlightInfo
import javax.inject.Inject

@FlightsScope
class FlightInfoConverter @Inject constructor(
    private val fromToConverter: FromToConverter,
    private val tripCountConverter: TripCountConverter,
    private val priceTypeConverter: PriceTypeConverter,
) {
    fun toUIModel(price: PriceFlight, flight: Flight): FlightInfo {
        val (from, to) = fromToConverter.toUIModel(flight)

        return FlightInfo(
            amount = "${price.amount} ${flight.currency}",
            tripCount = tripCountConverter.toUIModel(flight),
            from = from,
            to = to,
            priceType = priceTypeConverter.toUIModel(price.type),
            transfers = flight.trips.map { it.from to it.to }
        )
    }
}