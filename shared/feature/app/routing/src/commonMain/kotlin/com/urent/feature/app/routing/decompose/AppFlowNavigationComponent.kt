package com.urent.feature.app.routing.decompose

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.urent.core.routing.flow.decompose.FlowNavigationComponent
import com.urent.feature.app.routing.AppFlowComponent
import com.urent.feature.app.routing.decompose.AppFlowNavigationComponent.Child
import com.urent.feature.app.routing.decompose.AppFlowNavigationComponent.Config

@Stable
class AppFlowNavigationComponent(
  context: ComponentContext,
  val component: AppFlowComponent,
) : FlowNavigationComponent<Config, Child>(context) {
  override fun initialConfig(): List<Config> = listOf(Config.Main)

  override val childFactory: (Config, ComponentContext) -> Child = { config, _ ->
    when (config) {
      is Config.Main -> Child.Main
    }
  }

  sealed interface Config {
    data object Main : Config
  }

  sealed interface Child {
    data object Main : Child
  }
}
