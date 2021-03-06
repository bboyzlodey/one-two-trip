package com.skarlat.flights.presentation.flightList

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skarlat.core.ScreenState
import com.skarlat.core.util.dialog.DialogData
import com.skarlat.flights.R
import com.skarlat.flights.di.ComponentManager
import com.skarlat.flights.domain.useCase.GetFlightInfoUseCase
import com.skarlat.flights.domain.useCase.GetFlightListUseCase
import com.skarlat.flights.domain.useCase.GetFlightPrices
import com.skarlat.flights.presentation.model.FlightInfo
import com.skarlat.flights.presentation.model.FlightUI
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FlightListViewModel : ViewModel() {


    private val context: Context get() = ComponentManager.applicationContext!!

    val dialogDataFlow: Flow<DialogData> get() = dialogDataMutableFLow
    private val dialogDataMutableFLow = MutableSharedFlow<DialogData>(extraBufferCapacity = 1)

    val flightInfoFlow: Flow<FlightInfo> get() = flightInfoMutableFlow
    private val flightInfoMutableFlow = MutableSharedFlow<FlightInfo>(extraBufferCapacity = 1)

    val screenStateFlow: Flow<ScreenState> get() = screenStateMutableFlow
    private val screenStateMutableFlow =
        MutableSharedFlow<ScreenState>(extraBufferCapacity = 1, replay = 1)

    init {
        ComponentManager.localComponent?.inject(this)
        viewModelScope.launch(Dispatchers.IO) {
            flow {
                emit(ScreenState.Success(getFlightListUseCase.getFlightList()))
            }
                .onStart { screenStateMutableFlow.emit(ScreenState.Loading) }
                .catch { screenStateMutableFlow.emit(ScreenState.Error(context.getString(R.string.error))) }
                .collect { result -> screenStateMutableFlow.emit(result) }
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
        val prices = getFlightPricesUseCase.getFlightPrices(flightUI)
        selectedFlightTypeId = prices.first().uid
        return DialogData.SingleChoice(
            positiveButtonText = context.getString(R.string.select),
            negativeButtonText = context.getString(R.string.cancelation),
            onPositiveButtonClicked = {
                selectedFlightTypeId?.let { openFLight(flightUI, it) }
            },
            onNegativeButtonClicked = { selectedFlightTypeId = null },
            items = prices,
            onItemSelectedListener = { selectedFlightTypeId = it.uid }
        )
    }

    private fun openFLight(flightUI: FlightUI, tripType: String) {
        viewModelScope.launch {
            flightInfoMutableFlow.emit(
                getFlightInfoUseCase.getFlightInfo(
                    flightUI.id,
                    priceId = tripType
                )
            )
        }
    }
}