package com.urent.feature.citydetails.routing.decompose

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.urent.core.routing.flow.decompose.FlowNavigationComponent
import com.urent.core.routing.flow.decompose.FlowTransition
import com.urent.core.routing.flow.decompose.Node
import com.urent.core.routing.flow.decompose.Screen
import com.urent.core.routing.flow.decompose.viewModel
import com.urent.core.ui.routing.Event
import com.urent.feature.citydetails.routing.CityDetailsFlowComponent
import com.urent.feature.citydetails.routing.decompose.CityDetailsFlowNavigationComponent.Child
import com.urent.feature.citydetails.routing.decompose.CityDetailsFlowNavigationComponent.Config
import com.urent.feature.citydetails.ui.screen.cityDetails.CityDetailsViewModel

@Stable
class CityDetailsFlowNavigationComponent(
  context: ComponentContext,
  override val component: CityDetailsFlowComponent,
  private val cityId: Long,
) : FlowNavigationComponent<Config, Child>(context) {
  override fun initialConfig(): List<Config> = listOf(Config.CityDetails)

  override fun transition(event: Event): FlowTransition<Config> {
    return FlowTransition.Ignore
  }

  override val childFactory: (Config, ComponentContext) -> Child = { config, _ ->
    when (config) {
      is Config.CityDetails -> Child.CityDetails(component.viewModel<CityDetailsViewModel>(cityId))
    }
  }

  sealed interface Config {
    data object CityDetails : Config
  }

  sealed interface Child : Node {
    data class CityDetails(override val viewModel: CityDetailsViewModel) : Child, Screen
  }
}
