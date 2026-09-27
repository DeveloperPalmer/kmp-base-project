package com.urent.feature.app.routing.decompose

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.urent.core.routing.flow.decompose.FlowNavigationComponent
import com.urent.core.routing.flow.decompose.Node
import com.urent.core.routing.flow.decompose.Screen
import com.urent.core.routing.flow.decompose.viewModel
import com.urent.feature.app.routing.AppFlowComponent
import com.urent.feature.app.routing.decompose.AppFlowNavigationComponent.Child
import com.urent.feature.app.routing.decompose.AppFlowNavigationComponent.Config
import com.urent.feature.app.ui.screen.home.HomeViewModel

@Stable
class AppFlowNavigationComponent(
  context: ComponentContext,
  val component: AppFlowComponent,
) : FlowNavigationComponent<Config, Child>(context) {
  override fun initialConfig(): List<Config> = listOf(Config.Home)

  override val childFactory: (Config, ComponentContext) -> Child = { config, _ ->
    when (config) {
      is Config.Home -> Child.Home(component.viewModel<HomeViewModel>())
    }
  }

  sealed interface Config {
    data object Home : Config
  }

  sealed interface Child : Node {
    data class Home(override val viewModel: HomeViewModel) : Child, Screen
  }
}
