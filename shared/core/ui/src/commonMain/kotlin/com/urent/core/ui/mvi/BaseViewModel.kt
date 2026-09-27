package com.urent.core.ui.mvi

import androidx.compose.runtime.Stable
import com.urent.core.ui.routing.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import org.orbitmvi.orbit.OrbitContainerHost

@Stable
abstract class BaseViewModel<S : Any, I : Any, SE : Any> : OrbitContainerHost<S, S, SE> {
  val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

  private val eventChannel = Channel<Event>(Channel.UNLIMITED)
  val events: Flow<Event> = eventChannel.receiveAsFlow()

  abstract fun dispatch(viewIntent: I)

  protected fun sendEvent(event: Event) {
    eventChannel.trySend(event)
  }

  fun destroy() {
    viewModelScope.cancel()
  }
}
