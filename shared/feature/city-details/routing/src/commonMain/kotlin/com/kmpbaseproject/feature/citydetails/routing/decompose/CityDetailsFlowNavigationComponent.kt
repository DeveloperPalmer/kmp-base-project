package com.kmpbaseproject.feature.citydetails.routing.decompose

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.kmpbaseproject.core.routing.flow.decompose.FlowNavigationComponent
import com.kmpbaseproject.core.routing.flow.decompose.FlowTransition
import com.kmpbaseproject.core.routing.flow.decompose.Node
import com.kmpbaseproject.core.routing.flow.decompose.Screen
import com.kmpbaseproject.core.routing.flow.decompose.viewModel
import com.kmpbaseproject.core.ui.routing.Event
import com.kmpbaseproject.feature.citydetails.routing.CityDetailsFlowComponent
import com.kmpbaseproject.feature.citydetails.routing.decompose.CityDetailsFlowNavigationComponent.Child
import com.kmpbaseproject.feature.citydetails.routing.decompose.CityDetailsFlowNavigationComponent.Config
import com.kmpbaseproject.feature.citydetails.ui.screen.cityDetails.CityDetailsViewModel

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
