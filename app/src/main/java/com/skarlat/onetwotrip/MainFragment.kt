package com.skarlat.onetwotrip

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.skarlat.core.extension.`as`
import com.skarlat.flights.presentation.host.HostFragment

class MainFragment : Fragment(R.layout.fragment_main) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.fligtsListBtn)?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentHost, HostFragment(), null)
                .commit()
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (parentFragmentManager.backStackEntryCount > 1)
                        parentFragmentManager.popBackStack()
                    else
                        requireActivity().finish()
                }
            })
    }

    override fun onStart() {
        super.onStart()
        requireActivity().`as`<MainActivity> {
            supportActionBar?.title = getString(R.string.app_name)
        }
    }
}