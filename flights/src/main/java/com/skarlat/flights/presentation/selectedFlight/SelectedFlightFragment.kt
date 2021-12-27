package com.skarlat.flights.presentation.selectedFlight

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.skarlat.core.ToolbarSettings
import com.skarlat.core.extension.`as`
import com.skarlat.core.util.fragment.ComposeFragment
import com.skarlat.flights.R
import com.skarlat.flights.di.ComponentManager
import com.skarlat.flights.presentation.model.FlightInfo
import javax.inject.Inject

class SelectedFlightFragment : ComposeFragment() {

    private val viewModel: SelectedFlightViewModel by viewModels()

    @Inject
    lateinit var toolbarSettings: ToolbarSettings

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.arguments = arguments
        viewLifecycleOwner.lifecycle.addObserver(viewModel)
        ComponentManager.localComponent?.inject(this)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarSettings.setTitle(getString(R.string.selected_flight_screen_title))
        view.`as`<ComposeView> {
            Log.d("Compose View", "ComposeView")
            setContent {
                val state by viewModel.selectedFlightFlow.collectAsState(initial = null)
                Log.d("Compose View", "State is: $state")
                state?.let { SelectedFlightScreen(selectedFlightUI = it) }
            }
        }
    }
}

@Composable
fun SelectedFlightScreen(selectedFlightUI: FlightInfo) {
    Column(
        modifier = Modifier.scrollable(
            rememberScrollableState(consumeScrollDelta = { it }),
            Orientation.Vertical
        )
    ) {
        val modifier = Modifier.padding(top = 16.dp)
        TextFieldReadOnly(
            value = selectedFlightUI.from,
            hint = stringResource(id = R.string.from),
            modifier = modifier
        )
        TextFieldReadOnly(
            value = selectedFlightUI.to,
            hint = stringResource(id = R.string.to),
            modifier = modifier
        )
        Text(text = selectedFlightUI.amount, modifier = modifier)
        Text(text = selectedFlightUI.tripType, modifier = modifier)
        Text(text = selectedFlightUI.tripCount, modifier = modifier)
    }
}

@Composable
fun TextFieldReadOnly(value: String, hint: String, modifier: Modifier = Modifier) {
    TextField(
        value = value,
        onValueChange = {},
        readOnly = true,
        label = { Text(text = hint) },
        modifier = modifier
    )
}