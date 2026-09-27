package com.urent.feature.map.routing.decompose

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.urent.feature.map.ui.screen.map.MapScreen

@Composable
fun MapFlow(component: MapFlowNavigationComponent) {
  Children(
    stack = component.stack,
    animation = stackAnimation(slide()),
  ) { child ->
    when (val instance = child.instance) {
      is MapFlowNavigationComponent.Child.Map -> {
        MapScreen(viewModel = instance.viewModel)
      }
    }
  }
}
