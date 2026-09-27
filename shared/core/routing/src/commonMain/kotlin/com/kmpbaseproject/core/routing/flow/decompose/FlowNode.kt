package com.kmpbaseproject.core.routing.flow.decompose

import com.kmpbaseproject.core.ui.routing.Event

// A flow that answers events with transitions and passes the ignored ones up to its parent flow
abstract class FlowNode<Config : Any> {
  private var parent: FlowNode<*>? = null

  abstract fun transition(event: Event): FlowTransition<Config>

  protected abstract fun navigateTo(config: Config)

  protected fun adopt(child: Flow) {
    child.component.parent = this
  }

  protected fun dispatch(event: Event) {
    when (val transition = transition(event)) {
      is FlowTransition.Ignore -> {
        parent?.dispatch(event)
      }
      is FlowTransition.NavigateTo -> {
        navigateTo(transition.config)
      }
    }
  }
}
