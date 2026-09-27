package com.kmpbaseproject.feature.app.routing.decompose

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.pages.ChildPages
import com.kmpbaseproject.feature.app.ui.screen.home.HomeScreen
import com.kmpbaseproject.feature.cities.routing.decompose.CitiesFlow

@Composable
fun HomeFlow(component: HomeFlowNavigationComponent) {
  HomeScreen(viewModel = component.viewModel) {
    ChildPages(
      pages = component.pages,
      onPageSelected = component::selectPage,
    ) { _, page ->
      when (page) {
        is HomeFlowNavigationComponent.Child.Cities -> {
          CitiesFlow(component = page.component)
        }
      }
    }
  }
}
