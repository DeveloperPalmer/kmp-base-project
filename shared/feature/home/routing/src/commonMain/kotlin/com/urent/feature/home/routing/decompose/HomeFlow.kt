package com.urent.feature.home.routing.decompose

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.urent.feature.citydetails.routing.decompose.CityDetailsFlow
import com.urent.feature.hometabs.routing.decompose.HomeTabsFlow

@Composable
fun HomeFlow(component: HomeFlowNavigationComponent) {
  Children(
    stack = component.stack,
    animation = stackAnimation(slide()),
  ) { child ->
    when (val instance = child.instance) {
      is HomeFlowNavigationComponent.Child.HomeTabs -> {
        HomeTabsFlow(component = instance.component)
      }
      is HomeFlowNavigationComponent.Child.CityDetails -> {
        CityDetailsFlow(component = instance.component)
      }
    }
  }
}
