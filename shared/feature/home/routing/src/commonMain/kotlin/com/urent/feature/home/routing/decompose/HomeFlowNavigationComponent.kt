package com.urent.feature.home.routing.decompose

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.urent.core.routing.flow.decompose.Flow
import com.urent.core.routing.flow.decompose.FlowNavigationComponent
import com.urent.core.routing.flow.decompose.FlowTransition
import com.urent.core.ui.routing.Event
import com.urent.feature.home.routing.HomeFlowComponent
import com.urent.feature.home.routing.decompose.HomeFlowNavigationComponent.Child
import com.urent.feature.home.routing.decompose.HomeFlowNavigationComponent.Config
import com.urent.feature.hometabs.routing.decompose.HomeTabsFlowNavigationComponent

@Stable
class HomeFlowNavigationComponent(
  context: ComponentContext,
  val component: HomeFlowComponent,
) : FlowNavigationComponent<Config, Child>(context) {
  override fun initialConfig(): List<Config> = listOf(Config.HomeTabs)

  override fun transition(event: Event): FlowTransition<Config> {
    return FlowTransition.Ignore
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
    }
  }

  sealed interface Config {
    data object HomeTabs : Config
  }

  sealed interface Child : Flow {
    data class HomeTabs(override val component: HomeTabsFlowNavigationComponent) : Child
  }
}
