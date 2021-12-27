package com.skarlat.flights.presentation.flightList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalAirport
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.skarlat.core.ScreenState
import com.skarlat.core.ToolbarSettings
import com.skarlat.core.extension.`as`
import com.skarlat.core.util.Const
import com.skarlat.core.util.dialog.DialogFactory
import com.skarlat.core.util.fragment.ComposeFragment
import com.skarlat.flights.R
import com.skarlat.flights.di.ComponentManager
import com.skarlat.flights.presentation.model.FlightUI
import com.skarlat.flights.presentation.selectedFlight.SelectedFlightFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FlightListFragment : ComposeFragment() {

    private val viewModel: FlightListViewModel by viewModels()

    @Inject
    lateinit var toolbarSettings: ToolbarSettings

    companion object {
        fun newInstance(arguments: Bundle?): FlightListFragment {
            return FlightListFragment().apply { this.arguments = arguments }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ComponentManager.localComponent?.inject(this)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @ExperimentalMaterialApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarSettings.setTitle(getString(R.string.flight_screen_title))
        view.`as`<ComposeView> {
            setContent {
                val state by viewModel.screenStateFlow.collectAsState(
                    initial = ScreenState.Loading,
                    context = viewLifecycleOwner.lifecycle.coroutineScope.coroutineContext
                )
                when (state) {
                    is ScreenState.Loading -> Box(Modifier.fillMaxSize()) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                    is ScreenState.Error -> ShowMessage(message = (state as ScreenState.Error).message)
                    is ScreenState.Success -> (state as ScreenState.Success).result.`as`<List<FlightUI>> {
                        FlightsList(
                            flightsList = this,
                            onItemClicked = viewModel::onFlightItemClicked
                        )
                    }

                }
            }
        }
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenCreated {
            launch {
                viewModel.dialogDataFlow.collect { DialogFactory.showDialog(requireContext(), it) }
            }
            launch {
                viewModel.flightInfoFlow.collect {
                    parentFragmentManager.beginTransaction()
                        .replace(
                            R.id.fragmentHost,
                            SelectedFlightFragment::class.java,
                            Bundle().apply {
                                putParcelable(Const.BUNDLE_DATA_KEY, it)
                            }, "selected_flight"
                        )
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun FlightsList(flightsList: List<FlightUI>, onItemClicked: (FlightUI) -> Unit = {}) {
    LazyColumn(
        modifier = Modifier
            .background(Color.LightGray)
            .padding(bottom = 16.dp)
    ) {
        items(flightsList) { item: FlightUI ->
            FlightItem(item = item) {
                onItemClicked.invoke(it)
            }
        }
    }
}

@Composable
fun ShowMessage(message: String) {
    Snackbar() {
        Text(text = message)
    }
}

@Composable
fun ColumnScope.FromToItem(modifier: Modifier = Modifier, from: String, to: String) {
    Text(text = from)
    Icon(
        Icons.Default.LocalAirport,
        contentDescription = null,
        modifier = Modifier.rotate(180f)
    )
    Text(text = to)
}

@ExperimentalMaterialApi
@Composable
fun FlightItem(item: FlightUI, onItemClicked: (FlightUI) -> Unit = {}) {
    Card(modifier = Modifier
        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        .fillMaxWidth(),
        onClick = { onItemClicked(item) }
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        ) {
            FromToItem(from = item.from, to = item.to)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = item.tripCount,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier
                .padding(end = 16.dp, bottom = 16.dp, top = 16.dp)
                .fillMaxWidth()
        ) {
            Text(text = item.amount)
        }
    }
}
