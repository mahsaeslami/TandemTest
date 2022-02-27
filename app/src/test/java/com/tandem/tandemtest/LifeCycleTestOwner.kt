package com.tandem.tandemtest

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

/**
 * Created by Mahsa on 2022.02.27
 */
class LifeCycleTestOwner : LifecycleOwner {

    private val registry = LifecycleRegistry(this)

    override fun getLifecycle(): Lifecycle {
        return registry
    }

    fun onCreate() {
        registry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    fun onResume() {
        registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    fun onDestroy() {
        registry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }
}