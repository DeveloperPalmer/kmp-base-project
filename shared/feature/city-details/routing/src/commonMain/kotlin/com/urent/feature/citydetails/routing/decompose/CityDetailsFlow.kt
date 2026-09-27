package com.urent.feature.citydetails.routing.decompose

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.urent.feature.citydetails.ui.screen.cityDetails.CityDetailsScreen

@Composable
fun CityDetailsFlow(component: CityDetailsFlowNavigationComponent) {
  Children(
    stack = component.stack,
    animation = stackAnimation(slide()),
  ) { child ->
    when (val instance = child.instance) {
      is CityDetailsFlowNavigationComponent.Child.CityDetails -> {
        CityDetailsScreen(viewModel = instance.viewModel)
      }
    }
  }
}
