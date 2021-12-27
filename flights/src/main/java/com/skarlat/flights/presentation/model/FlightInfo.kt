package com.skarlat.flights.presentation.model

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class FlightInfo(
    val id: String,
    val amount: String,
    val tripCount: String,
    val from: String,
    val to: String,
    val tripType: String
) : Parcelable
