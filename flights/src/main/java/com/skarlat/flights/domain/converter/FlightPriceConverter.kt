package com.skarlat.flights.domain.converter

import com.skarlat.core.util.dialog.SingleChoiceItem
import com.skarlat.flights.data.model.PriceFlight
import javax.inject.Inject

class FlightPriceConverter @Inject constructor(private val priceTypeConverter: PriceTypeConverter) {

    fun toUIModel(price: PriceFlight): SingleChoiceItem {
        return SingleChoiceItem(price.id, "${priceTypeConverter.toUIModel(price.type)} - ${price.amount} RUB")
    }

}