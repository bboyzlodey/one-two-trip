package com.skarlat.flights.domain.converter

import android.content.Context
import com.skarlat.core.R
import com.skarlat.flights.data.model.Flight
import com.skarlat.flights.data.model.PriceFlight
import com.skarlat.flights.presentation.model.FlightUI
import javax.inject.Inject

class FlightConverter @Inject constructor(private val applicationContext: Context) {

    fun toUIModel(dataModel: Flight): FlightUI {
        val priceFrom = dataModel.prices.minWithOrNull { o1, o2 -> o1.amount.compareTo(o2.amount) }
            ?: PriceFlight(type = "economy", amount = 0)

        return FlightUI(
            id = dataModel.id,
            amount = applicationContext.getString(
                R.string.price_mask,
                priceFrom.amount,
                dataModel.currency
            ),
            tripCount = applicationContext.getString(
                R.string.trip_count_mask,
                dataModel.trips.size
            ),
            from = dataModel.trips.firstOrNull()?.from
                ?: applicationContext.getString(R.string.undefined),
            to = dataModel.trips.lastOrNull()?.to
                ?: applicationContext.getString(R.string.undefined)
        )
    }

}
