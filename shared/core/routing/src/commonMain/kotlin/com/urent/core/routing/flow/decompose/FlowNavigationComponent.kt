package com.urent.core.routing.flow.decompose

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.urent.core.ui.routing.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@Stable
abstract class FlowNavigationComponent<Config : Any, Child : Node>(
  protected val context: ComponentContext,
) {
  abstract fun initialConfig(): List<Config>

  abstract fun transition(event: Event)

  protected val nav = StackNavigation<Config>()

  protected abstract val childFactory: (Config, ComponentContext) -> Child

  // Orbit runs intents on Dispatchers.Default, Decompose navigates on the main thread
  private val eventScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

  init {
    context.lifecycle.doOnDestroy { eventScope.cancel() }
  }

  // There is an initialization conflict between stack and childFactory fields,
  // using lazy as a workaround to super class field initialization order problem
  val stack: Value<ChildStack<*, Child>> by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
    context.childStack(
      source = nav,
      serializer = null,
      initialStack = { initialConfig() },
      handleBackButton = true,
    ) { config, componentContext ->
      val child = childFactory(config, componentContext)
      if (child is Screen) {
        val events = eventScope.launch {
          child.viewModel.events.collect { event -> transition(event) }
        }
        componentContext.lifecycle.doOnDestroy {
          events.cancel()
          child.viewModel.destroy()
        }
      }
      child
    }
  }
}
