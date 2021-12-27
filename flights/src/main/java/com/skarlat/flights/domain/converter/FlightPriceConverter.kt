package com.skarlat.flights.domain.converter

import android.content.Context
import com.skarlat.core.util.dialog.SingleChoiceItem
import com.skarlat.flights.R
import com.skarlat.flights.data.model.PriceFlight
import com.skarlat.flights.domain.TripType
import javax.inject.Inject

class FlightPriceConverter @Inject constructor(private val applicationContext: Context) {

    fun toUIModel(price: PriceFlight): SingleChoiceItem {
        val priceType = TripType.fromIdentifier(price.type)
        return SingleChoiceItem(
            price.id, when (priceType) {
                TripType.BUSINESS -> applicationContext.getString(R.string.price_type_business)
                TripType.ECONOM -> applicationContext.getString(R.string.price_type_economy)
            } + " - ${price.amount} RUB"
        )
    }

}