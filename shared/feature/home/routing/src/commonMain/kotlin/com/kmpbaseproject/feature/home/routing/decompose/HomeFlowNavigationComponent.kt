package com.kmpbaseproject.feature.home.routing.decompose

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.kmpbaseproject.core.routing.flow.decompose.Flow
import com.kmpbaseproject.core.routing.flow.decompose.FlowNavigationComponent
import com.kmpbaseproject.core.routing.flow.decompose.FlowTransition
import com.kmpbaseproject.core.ui.routing.Event
import com.kmpbaseproject.feature.citydetails.routing.decompose.CityDetailsFlowNavigationComponent
import com.kmpbaseproject.feature.home.routing.HomeFlowComponent
import com.kmpbaseproject.feature.home.routing.decompose.HomeFlowNavigationComponent.Child
import com.kmpbaseproject.feature.home.routing.decompose.HomeFlowNavigationComponent.Config
import com.kmpbaseproject.feature.hometabs.routing.decompose.HomeTabsFlowNavigationComponent
import com.kmpbaseproject.feature.cities.ui.routing.FlowEvent as CitiesFlowEvent
import com.kmpbaseproject.feature.citydetails.ui.routing.FlowEvent as CityDetailsFlowEvent

@Stable
class HomeFlowNavigationComponent(
  context: ComponentContext,
  override val component: HomeFlowComponent,
) : FlowNavigationComponent<Config, Child>(context) {
  override fun initialConfig(): List<Config> = listOf(Config.HomeTabs)

  override fun transition(event: Event): FlowTransition<Config> {
    return when (event) {
      is CitiesFlowEvent.CityDetailsRequested -> {
        FlowTransition.NavigateTo(Config.CityDetails(event.cityId))
      }
      is CityDetailsFlowEvent.CityDetailsDismissed -> {
        FlowTransition.Back
      }
      else -> {
        FlowTransition.Ignore
      }
    }
  }

  override val childFactory: (Config, ComponentContext) -> Child = { config, componentContext ->
    when (config) {
      is Config.HomeTabs -> {
        val component = HomeTabsFlowNavigationComponent(
          context = componentContext,
          component = component.homeTabsFlowComponent()
        )
        Child.HomeTabs(component)
      }
      is Config.CityDetails -> {
        val component = CityDetailsFlowNavigationComponent(
          cityId = config.cityId,
          context = componentContext,
          component = component.cityDetailsFlowComponent()
        )
        Child.CityDetails(component)
      }
    }
  }

  sealed interface Config {
    data object HomeTabs : Config
    data class CityDetails(val cityId: Long) : Config
  }

  sealed interface Child : Flow {
    data class HomeTabs(override val component: HomeTabsFlowNavigationComponent) : Child
    data class CityDetails(override val component: CityDetailsFlowNavigationComponent) : Child
  }
}
