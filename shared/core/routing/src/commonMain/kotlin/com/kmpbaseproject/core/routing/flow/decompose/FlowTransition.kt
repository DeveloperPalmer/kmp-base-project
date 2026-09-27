package com.kmpbaseproject.core.routing.flow.decompose

sealed interface FlowTransition<out Config : Any> {
  data class NavigateTo<out Config : Any>(val config: Config) : FlowTransition<Config>
  data object Ignore : FlowTransition<Nothing>
}
