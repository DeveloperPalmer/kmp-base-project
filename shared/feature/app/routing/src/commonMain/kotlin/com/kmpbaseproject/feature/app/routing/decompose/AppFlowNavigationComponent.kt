package com.kmpbaseproject.feature.app.routing.decompose

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.kmpbaseproject.core.routing.flow.decompose.FlowNavigationComponent
import com.kmpbaseproject.core.routing.flow.decompose.Node
import com.kmpbaseproject.core.routing.flow.decompose.Screen
import com.kmpbaseproject.core.routing.flow.decompose.viewModel
import com.kmpbaseproject.feature.app.routing.AppFlowComponent
import com.kmpbaseproject.feature.app.routing.decompose.AppFlowNavigationComponent.Child
import com.kmpbaseproject.feature.app.routing.decompose.AppFlowNavigationComponent.Config
import com.kmpbaseproject.feature.app.ui.screen.main.MainViewModel

@Stable
class AppFlowNavigationComponent(
  context: ComponentContext,
  val component: AppFlowComponent,
) : FlowNavigationComponent<Config, Child>(context) {
  override fun initialConfig(): List<Config> = listOf(Config.Main)

  override val childFactory: (Config, ComponentContext) -> Child = { config, _ ->
    when (config) {
      is Config.Main -> Child.Main(component.viewModel<MainViewModel>())
    }
  }

  sealed interface Config {
    data object Main : Config
  }

  sealed interface Child : Node {
    data class Main(override val viewModel: MainViewModel) : Child, Screen
  }
}
