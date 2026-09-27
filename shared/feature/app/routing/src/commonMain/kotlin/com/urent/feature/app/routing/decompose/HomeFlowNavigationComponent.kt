package com.urent.feature.app.routing.decompose

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.router.pages.Pages
import com.arkivanov.decompose.router.pages.PagesNavigation
import com.arkivanov.decompose.router.pages.childPages
import com.arkivanov.decompose.router.pages.select
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.urent.core.routing.flow.decompose.Node
import com.urent.core.routing.flow.decompose.viewModel
import com.urent.feature.app.routing.HomeFlowComponent
import com.urent.feature.app.ui.screen.home.HomeViewModel
import com.urent.feature.cities.routing.decompose.CitiesFlowNavigationComponent

@Stable
class HomeFlowNavigationComponent(
  context: ComponentContext,
  val component: HomeFlowComponent,
) {
  private val navigation = PagesNavigation<Config>()

  val pages: Value<ChildPages<Config, Child>> = context.childPages(
    source = navigation,
    serializer = null,
    initialPages = { Pages(items = listOf(Config.Cities), selectedIndex = 0) },
    handleBackButton = true,
    childFactory = ::child,
  )

  val viewModel = component.viewModel<HomeViewModel>()

  init {
    context.lifecycle.doOnDestroy { viewModel.destroy() }
  }

  fun selectPage(index: Int) = navigation.select(index = index)

  private fun child(config: Config, context: ComponentContext): Child =
    when (config) {
      is Config.Cities -> {
        Child.Cities(CitiesFlowNavigationComponent(context, component.citiesFlowComponent()))
      }
    }

  sealed interface Config {
    data object Cities : Config
  }

  sealed interface Child : Node {
    data class Cities(val component: CitiesFlowNavigationComponent) : Child
  }
}
