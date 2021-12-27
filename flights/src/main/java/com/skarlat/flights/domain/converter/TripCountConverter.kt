package com.skarlat.flights.domain.converter

import android.content.Context
import com.skarlat.core.R
import com.skarlat.flights.data.model.Flight
import com.skarlat.flights.di.FlightsScope
import javax.inject.Inject

@FlightsScope
class TripCountConverter @Inject constructor(private val applicationContext: Context) {

    fun toUIModel(dataModel: Flight): String {
        return applicationContext.getString(
            R.string.trip_count_mask,
            dataModel.trips.size
        )
    }
}