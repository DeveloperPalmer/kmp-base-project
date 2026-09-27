package com.urent.feature.hometabs.routing.decompose

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.pages.ChildPages
import com.arkivanov.decompose.extensions.compose.pages.PagesScrollAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.urent.feature.cities.routing.decompose.CitiesFlow
import com.urent.feature.hometabs.ui.entity.Tab
import com.urent.feature.hometabs.ui.screen.home.HomeScreen
import com.urent.feature.map.routing.decompose.MapFlow

@Composable
fun HomeTabsFlow(component: HomeTabsFlowNavigationComponent) {
  val pages by component.pages.subscribeAsState()
  HomeScreen(
    viewModel = component.viewModel,
    selectedTab = Tab.entries[pages.selectedIndex],
  ) {
    ChildPages(
      pages = pages,
      onPageSelected = component::selectPage,
      scrollAnimation = PagesScrollAnimation.Default,
      pager = { modifier, state, key, pageContent ->
        HorizontalPager(
          state = state,
          modifier = modifier,
          userScrollEnabled = false,
          key = key,
          pageContent = pageContent,
        )
      },
    ) { _, page ->
      when (page) {
        is HomeTabsFlowNavigationComponent.Child.Cities -> {
          CitiesFlow(component = page.component)
        }
        is HomeTabsFlowNavigationComponent.Child.Map -> {
          MapFlow(component = page.component)
        }
      }
    }
  }
}
