package com.kmpbaseproject.feature.cities.routing.decompose

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.kmpbaseproject.core.routing.flow.decompose.FlowNavigationComponent
import com.kmpbaseproject.core.routing.flow.decompose.FlowTransition
import com.kmpbaseproject.core.routing.flow.decompose.Node
import com.kmpbaseproject.core.routing.flow.decompose.Screen
import com.kmpbaseproject.core.routing.flow.decompose.viewModel
import com.kmpbaseproject.core.ui.routing.Event
import com.kmpbaseproject.feature.cities.routing.CitiesFlowComponent
import com.kmpbaseproject.feature.cities.routing.decompose.CitiesFlowNavigationComponent.Child
import com.kmpbaseproject.feature.cities.routing.decompose.CitiesFlowNavigationComponent.Config
import com.kmpbaseproject.feature.cities.ui.screen.cities.CitiesViewModel

@Stable
class CitiesFlowNavigationComponent(
  context: ComponentContext,
  val component: CitiesFlowComponent,
) : FlowNavigationComponent<Config, Child>(context) {
  override fun initialConfig(): List<Config> = listOf(Config.Cities)

  override fun transition(event: Event): FlowTransition<Config> {
    return FlowTransition.Ignore
  }

  override val childFactory: (Config, ComponentContext) -> Child = { config, _ ->
    when (config) {
      is Config.Cities -> Child.Cities(component.viewModel<CitiesViewModel>())
    }
  }

  sealed interface Config {
    data object Cities : Config
  }

  sealed interface Child : Node {
    data class Cities(override val viewModel: CitiesViewModel) : Child, Screen
  }
}
