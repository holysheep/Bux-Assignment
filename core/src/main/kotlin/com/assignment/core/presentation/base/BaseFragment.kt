package com.assignment.core.presentation.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.assignment.core.infrastructure.DeviceConnectivityState
import com.assignment.core.presentation.utils.restartingLaunch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

open class BaseFragment : Fragment() {

    private val networkState: DeviceConnectivityState by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.restartingLaunch {
            networkState.changes()
                .onEach { isConnected ->

                }
                .launchIn(this)
        }
    }

    fun showConnectionStateView(isConnected: Boolean) {
        // todo
    }
}