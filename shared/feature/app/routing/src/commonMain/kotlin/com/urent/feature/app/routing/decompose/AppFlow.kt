package com.urent.feature.app.routing.decompose

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.urent.feature.app.ui.screen.home.HomeScreen

@Composable
fun AppFlow(component: AppFlowNavigationComponent) {
  Children(
    stack = component.stack,
    animation = stackAnimation(slide()),
  ) { child ->
    when (child.instance) {
      is AppFlowNavigationComponent.Child.Home -> {
        HomeScreen()
      }
    }
  }
}
