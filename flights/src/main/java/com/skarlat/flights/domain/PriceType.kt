package com.skarlat.flights.domain

enum class PriceType(val identifier: String) {
    BUSINESS("bussiness"),
    ECONOM("economy");

    companion object {
        fun fromIdentifier(identifier: String): PriceType {
            return PriceType.values().find { it.identifier == identifier }
                ?: throw IllegalStateException("Unknown identifier: $identifier")
        }
    }
}