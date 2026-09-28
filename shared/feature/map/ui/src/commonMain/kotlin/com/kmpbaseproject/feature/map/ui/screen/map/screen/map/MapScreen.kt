package com.kmpbaseproject.feature.map.ui.screen.map.screen.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.layoutId
import com.kmpbaseproject.core.ui.mvi.MviScreen
import com.kmpbaseproject.feature.map.ui.components.Map
import com.kmpbaseproject.feature.map.ui.components.MapControlsLayer
import com.kmpbaseproject.feature.map.ui.components.MapSheet
import com.kmpbaseproject.feature.map.ui.components.ZoomControl
import com.kmpbaseproject.feature.map.ui.components.rememberMapControlsState
import com.kmpbaseproject.feature.map.ui.components.rememberMapSheetState
import com.kmpbaseproject.feature.map.ui.entity.MapControl

@Composable
fun MapScreen(viewModel: MapViewModel) {
  return MviScreen(viewModel) { _, onIntent ->
    val sheetState = rememberMapSheetState()
    val controlsState = rememberMapControlsState(listOf(sheetState))
    Box(
      modifier = Modifier
        .fillMaxSize()
        .clipToBounds(),
    ) {
      Map(
        modifier = Modifier.fillMaxSize(),
        actions = sideEffects,
      )
      MapControlsLayer(
        modifier = Modifier.fillMaxSize(),
        state = controlsState,
      ) {
        ZoomControl(
          modifier = Modifier.layoutId(MapControl.Zoom),
          visible = controlsState.isVisible(MapControl.Zoom),
          onZoomInClick = { onIntent(ViewIntent.ZoomIn) },
          onZoomOutClick = { onIntent(ViewIntent.ZoomOut) },
        )
      }
      MapSheet(
        state = sheetState
      )
    }
  }
}
