package com.skarlat.flights.domain

enum class TripType(val identifier: String) {
    BUSINESS("bussiness"),
    ECONOM("economy");

    companion object {
        fun fromIdentifier(identifier: String): TripType {
            return TripType.values().find { it.identifier == identifier }
                ?: throw IllegalStateException("Unknown identifier: $identifier")
        }
    }
}