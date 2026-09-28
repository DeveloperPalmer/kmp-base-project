package com.urent.feature.map.ui.screen.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.urent.core.ui.mvi.MviScreen

@Composable
fun MapScreen(viewModel: MapViewModel) {
  return MviScreen(viewModel) { _, _ ->
    Map(modifier = Modifier.fillMaxSize())
  }
}
