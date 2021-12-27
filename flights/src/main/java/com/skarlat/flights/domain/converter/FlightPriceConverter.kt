package com.skarlat.flights.domain.converter

import com.skarlat.core.util.dialog.SingleChoiceItem
import com.skarlat.flights.data.model.PriceFlight
import com.skarlat.flights.di.FlightsScope
import javax.inject.Inject

@FlightsScope
class FlightPriceConverter @Inject constructor(private val priceTypeConverter: PriceTypeConverter) {

    fun toUIModel(price: PriceFlight): SingleChoiceItem {
        return SingleChoiceItem(price.id, "${priceTypeConverter.toUIModel(price.type)} - ${price.amount} RUB")
    }

}