package com.urent.feature.cities.routing.decompose

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.pushToFront
import com.urent.core.routing.flow.decompose.FlowNavigationComponent
import com.urent.core.routing.flow.decompose.Node
import com.urent.core.routing.flow.decompose.Screen
import com.urent.core.routing.flow.decompose.viewModel
import com.urent.core.ui.routing.Event
import com.urent.feature.cities.routing.CitiesFlowComponent
import com.urent.feature.cities.routing.decompose.CitiesFlowNavigationComponent.Child
import com.urent.feature.cities.routing.decompose.CitiesFlowNavigationComponent.Config
import com.urent.feature.cities.ui.routing.FlowEvent
import com.urent.feature.cities.ui.screen.cities.CitiesViewModel
import com.urent.feature.cities.ui.screen.cityDetails.CityDetailsViewModel

@Stable
class CitiesFlowNavigationComponent(
  context: ComponentContext,
  val component: CitiesFlowComponent,
) : FlowNavigationComponent<Config, Child>(context) {
  override fun initialConfig(): List<Config> = listOf(Config.Cities)

  override fun transition(event: Event) {
    return when (event) {
      is FlowEvent.CityDetailsRequested -> {
        nav.pushToFront(Config.CityDetails)
      }
      else -> {
        Unit
      }
    }
  }

  override val childFactory: (Config, ComponentContext) -> Child = { config, _ ->
    when (config) {
      is Config.Cities -> Child.Cities(component.viewModel<CitiesViewModel>())
      is Config.CityDetails -> Child.CityDetails(component.viewModel<CityDetailsViewModel>())
    }
  }

  sealed interface Config {
    data object Cities : Config
    data object CityDetails : Config
  }

  sealed interface Child : Node {
    data class Cities(override val viewModel: CitiesViewModel) : Child, Screen
    data class CityDetails(override val viewModel: CityDetailsViewModel) : Child, Screen
  }
}
