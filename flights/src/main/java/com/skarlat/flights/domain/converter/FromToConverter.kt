package com.skarlat.flights.domain.converter

import android.content.Context
import com.skarlat.core.R
import com.skarlat.flights.data.model.Flight
import javax.inject.Inject

class FromToConverter @Inject constructor(private val applicationContext: Context) {

    fun toUIModel(dataModel: Flight): Pair<String/*from*/, String/*to*/> {
        val undefined = applicationContext.getString(R.string.undefined)
        return (dataModel.trips.firstOrNull()?.from ?: undefined) to (dataModel.trips.lastOrNull()?.to ?: undefined)
    }
}