package com.kmpbaseproject.core.ui.mvi

import androidx.compose.runtime.Stable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import org.orbitmvi.orbit.OrbitContainerHost

@Stable
abstract class BaseViewModel<S : Any, I : Any, SE : Any> : OrbitContainerHost<S, S, SE> {
  val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

  abstract fun dispatch(viewIntent: I)

  fun destroy() {
    viewModelScope.cancel()
  }
}
