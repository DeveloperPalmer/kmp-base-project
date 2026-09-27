package com.kmpbaseproject.feature.map.routing.decompose

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.kmpbaseproject.core.routing.flow.decompose.FlowNavigationComponent
import com.kmpbaseproject.core.routing.flow.decompose.FlowTransition
import com.kmpbaseproject.core.routing.flow.decompose.Node
import com.kmpbaseproject.core.routing.flow.decompose.Screen
import com.kmpbaseproject.core.routing.flow.decompose.viewModel
import com.kmpbaseproject.core.ui.routing.Event
import com.kmpbaseproject.feature.map.routing.MapFlowComponent
import com.kmpbaseproject.feature.map.routing.decompose.MapFlowNavigationComponent.Child
import com.kmpbaseproject.feature.map.routing.decompose.MapFlowNavigationComponent.Config
import com.kmpbaseproject.feature.map.ui.screen.map.MapViewModel

@Stable
class MapFlowNavigationComponent(
  context: ComponentContext,
  val component: MapFlowComponent,
) : FlowNavigationComponent<Config, Child>(context) {
  override fun initialConfig(): List<Config> = listOf(Config.Map)

  override fun transition(event: Event): FlowTransition<Config> {
    return FlowTransition.Ignore
  }

  override val childFactory: (Config, ComponentContext) -> Child = { config, _ ->
    when (config) {
      is Config.Map -> Child.Map(component.viewModel<MapViewModel>())
    }
  }

  sealed interface Config {
    data object Map : Config
  }

  sealed interface Child : Node {
    data class Map(override val viewModel: MapViewModel) : Child, Screen
  }
}
