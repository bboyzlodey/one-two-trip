package com.skarlat.onetwotrip

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skarlat.core.ToolbarSettings
import com.skarlat.flights.di.ComponentManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ComponentManager.applicationContext = applicationContext
        ComponentManager.toolbarSettings =
            ToolbarSettings { title: String -> supportActionBar?.title = title }
        supportFragmentManager.beginTransaction().add(R.id.fragmentHost, MainFragment(), null)
            .addToBackStack(null)
            .commit()
    }
}