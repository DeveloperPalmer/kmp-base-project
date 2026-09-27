package com.urent.feature.hometabs.routing.decompose

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.router.pages.Pages
import com.arkivanov.decompose.router.pages.PagesNavigation
import com.arkivanov.decompose.router.pages.childPages
import com.arkivanov.decompose.router.pages.navigate
import com.arkivanov.decompose.router.pages.select
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.urent.core.routing.flow.decompose.Flow
import com.urent.core.routing.flow.decompose.FlowNode
import com.urent.core.routing.flow.decompose.FlowTransition
import com.urent.core.routing.flow.decompose.viewModel
import com.urent.core.ui.routing.Event
import com.urent.feature.cities.routing.decompose.CitiesFlowNavigationComponent
import com.urent.feature.hometabs.routing.HomeTabsFlowComponent
import com.urent.feature.hometabs.routing.decompose.HomeTabsFlowNavigationComponent.Config
import com.urent.feature.hometabs.ui.entity.Tab
import com.urent.feature.hometabs.ui.routing.FlowEvent
import com.urent.feature.hometabs.ui.screen.home.HomeViewModel
import com.urent.feature.map.routing.decompose.MapFlowNavigationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@Stable
class HomeTabsFlowNavigationComponent(
  context: ComponentContext,
  val component: HomeTabsFlowComponent,
) : FlowNode<Config>() {
  private val eventScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
  private val pagesNavigation = PagesNavigation<Config>()

  val viewModel = component.viewModel<HomeViewModel>()

  val pages: Value<ChildPages<Config, Child>> = context.childPages(
    source = pagesNavigation,
    serializer = null,
    handleBackButton = true,
    childFactory = ::child,
    initialPages = {
      Pages(
        selectedIndex = 0,
        items = listOf(Config.Cities, Config.Map)
      )
    },
  )

  init {
    eventScope.launch {
      viewModel.events.collect { event -> dispatch(event) }
    }
    context.lifecycle.doOnDestroy {
      eventScope.cancel()
      viewModel.destroy()
      component.coroutineScope().cancel()
    }
  }

  fun selectPage(index: Int) {
    return pagesNavigation.select(index = index)
  }

  override fun transition(event: Event): FlowTransition<Config> {
    return when (event) {
      is FlowEvent.TabChangeRequested -> {
        FlowTransition.NavigateTo(event.tab.config())
      }
      else -> {
        FlowTransition.Ignore
      }
    }
  }

  override fun navigateTo(config: Config) {
    pagesNavigation.navigate { current -> current.copy(selectedIndex = current.items.indexOf(config)) }
  }

  // Tabs have no back stack
  override fun navigateBack() = Unit

  private fun child(config: Config, context: ComponentContext): Child {
    val child = when (config) {
      is Config.Cities -> {
        val component = CitiesFlowNavigationComponent(
          context = context,
          component = component.citiesFlowComponent()
        )
        Child.Cities(component)
      }
      is Config.Map -> {
        val component = MapFlowNavigationComponent(
          context = context,
          component = component.mapFlowComponent()
        )
        Child.Map(component)
      }
    }
    adopt(child)
    return child
  }

  private fun Tab.config(): Config {
    return when (this) {
      Tab.Cities -> Config.Cities
      Tab.Map -> Config.Map
    }
  }

  sealed interface Config {
    data object Cities : Config
    data object Map : Config
  }

  sealed interface Child : Flow {
    data class Cities(override val component: CitiesFlowNavigationComponent) : Child
    data class Map(override val component: MapFlowNavigationComponent) : Child
  }
}
