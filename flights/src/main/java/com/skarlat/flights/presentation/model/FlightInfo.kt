package com.skarlat.flights.presentation.model

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class FlightInfo(
    val amount: String,
    val tripCount: String,
    val from: String,
    val to: String,
    val priceType: String,
    val transfers: List<Pair<String/*from*/, String/*to*/>>
) : Parcelable
