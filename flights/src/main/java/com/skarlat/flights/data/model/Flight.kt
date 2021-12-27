package com.skarlat.flights.data.model

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Flight(
    val currency: String,
    val prices: List<PriceFlight>,
    val trips: List<Trip>,
    val id: String = UUID.randomUUID().toString()
)
