package com.skarlat.flights.di

import android.content.Context
import com.skarlat.core.ToolbarSettings

object ComponentManager {

    var applicationContext: Context? = null
    var toolbarSettings: ToolbarSettings? = null


    var localComponent: FlightComponent? = null
        get() {
            if (field == null)
                field =
                    DaggerFlightComponent.builder().flightModule(
                        FlightModule(
                            applicationContext!!,
                            toolbarSettings = toolbarSettings!!
                        )
                    )
                        .build()
            return field
        }
}