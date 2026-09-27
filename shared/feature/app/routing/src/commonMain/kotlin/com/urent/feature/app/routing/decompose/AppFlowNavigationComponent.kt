package com.urent.feature.app.routing.decompose

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.urent.core.routing.flow.decompose.FlowNavigationComponent
import com.urent.core.routing.flow.decompose.Node
import com.urent.core.ui.routing.Event
import com.urent.feature.app.routing.AppFlowComponent
import com.urent.feature.app.routing.decompose.AppFlowNavigationComponent.Child
import com.urent.feature.app.routing.decompose.AppFlowNavigationComponent.Config
import com.urent.feature.home.routing.decompose.HomeFlowNavigationComponent

@Stable
class AppFlowNavigationComponent(
  context: ComponentContext,
  val component: AppFlowComponent,
) : FlowNavigationComponent<Config, Child>(context) {
  override fun initialConfig(): List<Config> = listOf(Config.Home)

  override fun transition(event: Event) = Unit

  override val childFactory: (Config, ComponentContext) -> Child = { config, componentContext ->
    when (config) {
      is Config.Home -> {
        Child.Home(HomeFlowNavigationComponent(componentContext, component.homeFlowComponent()))
      }
    }
  }

  sealed interface Config {
    data object Home : Config
  }

  sealed interface Child : Node {
    data class Home(val component: HomeFlowNavigationComponent) : Child
  }
}
