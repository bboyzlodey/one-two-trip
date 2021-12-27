package com.skarlat.flights.domain.converter

import android.content.Context
import com.skarlat.flights.R
import com.skarlat.flights.domain.PriceType
import javax.inject.Inject

class PriceTypeConverter @Inject constructor(private val applicationContext: Context) {

    fun toUIModel(priceType: String): String {
        return when (PriceType.fromIdentifier(priceType)) {
            PriceType.BUSINESS -> applicationContext.getString(R.string.price_type_business)
            PriceType.ECONOM -> applicationContext.getString(R.string.price_type_economy)
        }
    }
}