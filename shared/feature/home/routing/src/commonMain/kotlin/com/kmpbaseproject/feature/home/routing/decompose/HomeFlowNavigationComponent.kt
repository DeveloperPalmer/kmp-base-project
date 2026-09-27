package com.kmpbaseproject.feature.home.routing.decompose

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.kmpbaseproject.core.routing.flow.decompose.FlowNavigationComponent
import com.kmpbaseproject.core.routing.flow.decompose.Node
import com.kmpbaseproject.core.ui.routing.Event
import com.kmpbaseproject.feature.home.routing.HomeFlowComponent
import com.kmpbaseproject.feature.home.routing.decompose.HomeFlowNavigationComponent.Child
import com.kmpbaseproject.feature.home.routing.decompose.HomeFlowNavigationComponent.Config
import com.kmpbaseproject.feature.hometabs.routing.decompose.HomeTabsFlowNavigationComponent

@Stable
class HomeFlowNavigationComponent(
  context: ComponentContext,
  val component: HomeFlowComponent,
) : FlowNavigationComponent<Config, Child>(context) {
  override fun initialConfig(): List<Config> = listOf(Config.HomeTabs)

  override fun transition(event: Event) = Unit

  override val childFactory: (Config, ComponentContext) -> Child = { config, componentContext ->
    when (config) {
      is Config.HomeTabs -> {
        Child.HomeTabs(HomeTabsFlowNavigationComponent(componentContext, component.homeTabsFlowComponent()))
      }
    }
  }

  sealed interface Config {
    data object HomeTabs : Config
  }

  sealed interface Child : Node {
    data class HomeTabs(val component: HomeTabsFlowNavigationComponent) : Child
  }
}
