package com.skarlat.flights.data.model

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class PriceFlight(val id: String = UUID.randomUUID().toString(), val type: String, val amount: Int)
