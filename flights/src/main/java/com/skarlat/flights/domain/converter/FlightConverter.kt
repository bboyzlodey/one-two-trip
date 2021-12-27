package com.skarlat.flights.domain.converter

import android.content.Context
import com.skarlat.core.R
import com.skarlat.flights.data.model.Flight
import com.skarlat.flights.data.model.PriceFlight
import com.skarlat.flights.di.FlightsScope
import com.skarlat.flights.presentation.model.FlightUI
import javax.inject.Inject

@FlightsScope
class FlightConverter @Inject constructor(
    private val applicationContext: Context,
    private val tripCountConverter: TripCountConverter,
    private val fromToConverter: FromToConverter
) {

    fun toUIModel(dataModel: Flight): FlightUI {
        val priceFrom = dataModel.prices.minWithOrNull { o1, o2 -> o1.amount.compareTo(o2.amount) }
            ?: PriceFlight(type = "economy", amount = 0)
        val (from, to) = fromToConverter.toUIModel(dataModel)
        return FlightUI(
            id = dataModel.id,
            amount = applicationContext.getString(
                R.string.price_mask,
                priceFrom.amount,
                dataModel.currency
            ),
            tripCount = tripCountConverter.toUIModel(dataModel = dataModel),
            from = from,
            to = to
        )
    }

}
