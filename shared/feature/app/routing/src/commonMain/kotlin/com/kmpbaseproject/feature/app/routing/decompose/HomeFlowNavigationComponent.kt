package com.kmpbaseproject.feature.app.routing.decompose

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.router.pages.Pages
import com.arkivanov.decompose.router.pages.PagesNavigation
import com.arkivanov.decompose.router.pages.childPages
import com.arkivanov.decompose.router.pages.select
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.kmpbaseproject.core.routing.flow.decompose.Node
import com.kmpbaseproject.core.routing.flow.decompose.viewModel
import com.kmpbaseproject.core.ui.routing.Event
import com.kmpbaseproject.feature.app.routing.HomeFlowComponent
import com.kmpbaseproject.feature.app.ui.routing.FlowEvent
import com.kmpbaseproject.feature.app.ui.screen.home.HomeViewModel
import com.kmpbaseproject.feature.cities.routing.decompose.CitiesFlowNavigationComponent
import com.kmpbaseproject.feature.map.routing.decompose.MapFlowNavigationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@Stable
class HomeFlowNavigationComponent(
  context: ComponentContext,
  val component: HomeFlowComponent,
) {
  private val eventScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
  private val pagesNavigation = PagesNavigation<Config>()

  val viewModel = component.viewModel<HomeViewModel>()

  val pages: Value<ChildPages<Config, Child>> = context.childPages(
    source = pagesNavigation,
    serializer = null,
    initialPages = { Pages(items = listOf(Config.Cities, Config.Map), selectedIndex = 0) },
    handleBackButton = true,
    childFactory = ::child,
  )

  init {
    eventScope.launch {
      viewModel.events.collect { event -> transition(event) }
    }
    context.lifecycle.doOnDestroy {
      eventScope.cancel()
      viewModel.destroy()
    }
  }

  fun selectPage(index: Int) {
    return pagesNavigation.select(index = index)
  }

  private fun transition(event: Event) {
    return when (event) {
      is FlowEvent.TabChangeRequested -> {
        pagesNavigation.select(index = event.tab.ordinal)
      }
      else -> {}
    }
  }

  private fun child(config: Config, context: ComponentContext): Child {
    return when (config) {
      is Config.Cities -> {
        Child.Cities(CitiesFlowNavigationComponent(context, component.citiesFlowComponent()))
      }
      is Config.Map -> {
        Child.Map(MapFlowNavigationComponent(context, component.mapFlowComponent()))
      }
    }
  }

  sealed interface Config {
    data object Cities : Config
    data object Map : Config
  }

  sealed interface Child : Node {
    data class Cities(val component: CitiesFlowNavigationComponent) : Child
    data class Map(val component: MapFlowNavigationComponent) : Child
  }
}
