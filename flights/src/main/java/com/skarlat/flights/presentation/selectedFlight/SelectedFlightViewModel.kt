package com.skarlat.flights.presentation.selectedFlight

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skarlat.core.ToolbarSettings
import com.skarlat.core.extension.`as`
import com.skarlat.core.util.Const
import com.skarlat.flights.presentation.model.FlightInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectedFlightViewModel : ViewModel(), DefaultLifecycleObserver {

    var arguments: Bundle? = null

    val selectedFlightFlow: Flow<FlightInfo?> get() = selectedFlightMutableFlow
    private val selectedFlightMutableFlow = MutableSharedFlow<FlightInfo>(replay = 1, extraBufferCapacity = 1)

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        arguments?.get(Const.BUNDLE_DATA_KEY)?.`as`<FlightInfo> {
            viewModelScope.launch {
                Log.d("SelectedFlightViewModel", "info: ${this@`as`}")
                selectedFlightMutableFlow.emit(this@`as`)
            }
        }
    }

}