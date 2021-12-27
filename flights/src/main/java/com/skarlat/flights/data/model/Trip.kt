package com.skarlat.flights.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Trip(val from: String, val to: String)
