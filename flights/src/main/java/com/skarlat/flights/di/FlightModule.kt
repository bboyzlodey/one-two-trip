package com.skarlat.flights.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skarlat.core.ToolbarSettings
import com.skarlat.flights.data.network.FlightService
import com.skarlat.flights.data.repository.FlightRepository
import com.skarlat.flights.data.repository.IFlightsRepository
import com.skarlat.flights_api.FlightsLauncher
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create

@Module(includes = [FlightBindsModule::class])
class FlightModule(
    private val applicationContext: Context,
    private val toolbarSettings: ToolbarSettings
) {

    @ExperimentalSerializationApi
    @Provides
    fun provideFlightService(): FlightService {
        return Retrofit.Builder()
            .baseUrl("https://603e34c648171b0017b2ec55.mockapi.io/")
            .client(OkHttpClient())
            .addConverterFactory(Json.asConverterFactory(MediaType.parse("application/json")!!))
            .build()
            .create()
    }

    @Provides
    fun provideApplicationContext(): Context {
        return applicationContext
    }

    @Provides
    fun provideToolbarSettings(): ToolbarSettings {
        return toolbarSettings
    }

}

@Module
interface FlightBindsModule {

    @Binds
    fun bindFlightsLauncher(impl: FLightsLauncher): FlightsLauncher

    @Binds
    fun bindFLightRepository(impl: FlightRepository): IFlightsRepository


}