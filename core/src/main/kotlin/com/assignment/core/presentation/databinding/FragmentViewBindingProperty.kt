package com.assignment.core.presentation.databinding

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Extension for injecting fragment data binding (my experiment)
 */
class FragmentViewBindingProperty<T : ViewBinding>(

    private val viewBindingClass: Class<T>
) : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {

    private var lifecycleObserverInitialized = false
    private var viewBindingInitialized = false

    private val bindViewMethod by lazy(LazyThreadSafetyMode.NONE) {
        viewBindingClass.getMethod("bind", View::class.java)
    }
    private var viewBinding: T? = null

    private val mainHandler = Handler(Looper.getMainLooper())

    @MainThread
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        isMainThread()
        val view = thisRef.requireView()
        check((viewBinding != null && viewBindingInitialized) || (viewBinding == null && !viewBindingInitialized))
        initLifecycleObserver(thisRef.viewLifecycleOwner.lifecycle)
        return viewBinding ?: initViewBinding(view)
    }

    private fun initLifecycleObserver(lifecycle: Lifecycle) {
        if (!lifecycleObserverInitialized) {
            lifecycle.addObserver(this)
            lifecycleObserverInitialized = true
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun initViewBinding(view: View): T {
        viewBindingInitialized = true
        return (bindViewMethod(null, view) as T).also { this.viewBinding = it }
    }

    @MainThread
    override fun onDestroy(owner: LifecycleOwner) {
        isMainThread()
        owner.lifecycle.removeObserver(this)
        // Fragment.viewLifecycleOwner call LifecycleObserver.onDestroy() before Fragment.onDestroyView().
        // That's why postpone reset is needed
        mainHandler.post {
            lifecycleObserverInitialized = false
            viewBinding = null
            viewBindingInitialized = false
        }
    }
}

fun isMainThread(): Boolean {
    val myLooper = Looper.myLooper() ?: return false
    return myLooper === Looper.getMainLooper()
}

inline fun <reified T : ViewBinding> Fragment.viewBinding(): ReadOnlyProperty<Fragment, T> {
    return FragmentViewBindingProperty(T::class.java)
}
