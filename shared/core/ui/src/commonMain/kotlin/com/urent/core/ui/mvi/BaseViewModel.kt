package com.urent.core.ui.mvi

import androidx.compose.runtime.Stable
import com.urent.core.ui.routing.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.receiveAsFlow
import org.orbitmvi.orbit.OrbitContainerHost
import org.orbitmvi.orbit.blockingIntent
import org.orbitmvi.orbit.syntax.Syntax

@Stable
abstract class BaseViewModel<S : Any, I : Any, SE : Any> : OrbitContainerHost<S, S, SE> {
  val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

  private val eventChannel = Channel<Event>(Channel.UNLIMITED)
  val events: Flow<Event> = eventChannel.receiveAsFlow()

  @PublishedApi
  internal val viewIntents = MutableSharedFlow<I>(extraBufferCapacity = Channel.UNLIMITED)

  fun dispatch(viewIntent: I) {
    if (viewIntent is BlockingViewIntent) {
      blockingIntent {
        viewIntents.emit(viewIntent)
        handle(viewIntent)
      }
    } else {
      intent {
        viewIntents.emit(viewIntent)
        handle(viewIntent)
      }
    }
  }

  protected abstract suspend fun Syntax<S, SE>.handle(viewIntent: I)

  // View intents as a stream, for operators like debounce. Collect it first thing in the container's onCreate:
  // Orbit runs onCreate before any dispatched intent, so no view intent is missed
  protected inline fun <reified T : I> intents(): Flow<T> = viewIntents.filterIsInstance<T>()

  // The Syntax receiver keeps events inside Orbit intents, after whatever the intent awaits
  protected suspend fun Syntax<S, SE>.sendEvent(event: Event) {
    eventChannel.send(event)
  }

  fun destroy() {
    viewModelScope.cancel()
  }
}
