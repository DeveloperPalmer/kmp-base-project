package com.urent.feature.app.routing.decompose

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.pages.ChildPages
import com.arkivanov.decompose.extensions.compose.pages.PagesScrollAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.urent.feature.app.ui.screen.home.HomeScreen
import com.urent.feature.app.ui.screen.home.Tab
import com.urent.feature.cities.routing.decompose.CitiesFlow
import com.urent.feature.map.routing.decompose.MapFlow

@Composable
fun HomeFlow(component: HomeFlowNavigationComponent) {
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
        is HomeFlowNavigationComponent.Child.Cities -> {
          CitiesFlow(component = page.component)
        }
        is HomeFlowNavigationComponent.Child.Map -> {
          MapFlow(component = page.component)
        }
      }
    }
  }
}
