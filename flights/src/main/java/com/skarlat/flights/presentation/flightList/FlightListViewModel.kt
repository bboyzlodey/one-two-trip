package com.skarlat.flights.presentation.flightList

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skarlat.core.util.dialog.DialogData
import com.skarlat.flights.di.ComponentManager
import com.skarlat.flights.domain.useCase.GetFlightInfoUseCase
import com.skarlat.flights.domain.useCase.GetFlightListUseCase
import com.skarlat.flights.domain.useCase.GetFlightPrices
import com.skarlat.flights.presentation.model.FlightInfo
import com.skarlat.flights.presentation.model.FlightUI
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FlightListViewModel : ViewModel(), DefaultLifecycleObserver {

    private val debug = object : CoroutineExceptionHandler {
        override val key: CoroutineContext.Key<*>
            get() = CoroutineExceptionHandler.Key

        override fun handleException(context: CoroutineContext, exception: Throwable) {
            Log.e("ERROR", null, exception)
        }
    }

    val flightsFlow: Flow<List<FlightUI>> get() = flightsMutableFLow
    private val flightsMutableFLow = MutableStateFlow<List<FlightUI>>(emptyList())

    val dialogDataFlow: Flow<DialogData> get() = dialogDataMutableFLow
    private val dialogDataMutableFLow = MutableSharedFlow<DialogData>(extraBufferCapacity = 1)

    init {
        ComponentManager.localComponent?.inject(this)
        viewModelScope.launch(Dispatchers.IO + debug) {
            flightsMutableFLow.emit(getFlightListUseCase.getFlightList())
        }
    }

    @Inject
    lateinit var getFlightPricesUseCase: GetFlightPrices

    @Inject
    lateinit var getFlightListUseCase: GetFlightListUseCase

    @Inject
    lateinit var getFlightInfoUseCase: GetFlightInfoUseCase

    private var selectedFlightTypeId: String? = null

    fun onFlightItemClicked(flightUI: FlightUI) {
        viewModelScope.launch {
            dialogDataMutableFLow.emit(generateDialogData(flightUI))
        }
    }

    private suspend fun generateDialogData(flightUI: FlightUI): DialogData {
        return DialogData.SingleChoice(
            positiveButtonText = "выбрать",
            negativeButtonText = "отмена",
            onPositiveButtonClicked = {
                selectedFlightTypeId?.let { openFLight(flightUI, it) }
                selectedFlightTypeId
            },
            onNegativeButtonClicked = { selectedFlightTypeId = null },
            items = getFlightPricesUseCase.getFlightPrices(flightUI),
            onItemSelectedListener = { selectedFlightTypeId = it.uid }
        )
    }

    val flightInfoFlow: Flow<FlightInfo> get() = flightInfoMutableFlow
    private val flightInfoMutableFlow = MutableSharedFlow<FlightInfo>(extraBufferCapacity = 1)

    private fun openFLight(flightUI: FlightUI, tripType: String) {
        viewModelScope.launch {
            flightInfoMutableFlow.emit(
                getFlightInfoUseCase.getFlightInfo(
                    flightUI.id,
                    tripTypeId = tripType
                )
            )
        }
    }
}