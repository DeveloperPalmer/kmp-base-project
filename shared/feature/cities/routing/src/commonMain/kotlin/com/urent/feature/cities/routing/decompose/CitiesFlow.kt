package com.urent.feature.cities.routing.decompose

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.urent.feature.cities.ui.screen.cities.CitiesScreen
import com.urent.feature.cities.ui.screen.cityDetails.CityDetailsScreen

@Composable
fun CitiesFlow(component: CitiesFlowNavigationComponent) {
  Children(
    stack = component.stack,
    animation = stackAnimation(slide()),
  ) { child ->
    when (val instance = child.instance) {
      is CitiesFlowNavigationComponent.Child.Cities -> {
        CitiesScreen(viewModel = instance.viewModel)
      }
      is CitiesFlowNavigationComponent.Child.CityDetails -> {
        CityDetailsScreen(viewModel = instance.viewModel)
      }
    }
  }
}
