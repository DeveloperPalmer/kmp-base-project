package com.kmpbaseproject.feature.map.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp
import com.kmpbaseproject.feature.map.ui.entity.MapSheetValue
import com.kmpbaseproject.uikit.theme.AppTheme
import kotlin.math.roundToInt

@Composable
internal fun MapSheet(
  state: MapSheetState,
  modifier: Modifier = Modifier,
) {
  val ceiling = mapCeiling()
  Box(
    modifier = modifier
      .fillMaxSize()
      .layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        state.draggable.updateAnchors(
          DraggableAnchors {
            MapSheetValue.Collapsed at placeable.height - COLLAPSED_HEIGHT.toPx()
            MapSheetValue.Expanded at ceiling.toPx()
          },
        )
        layout(placeable.width, placeable.height) {
          placeable.place(
            x = 0,
            y = state.draggable.requireOffset().roundToInt()
          )
        }
      }
      .anchoredDraggable(
        state = state.draggable,
        orientation = Orientation.Vertical
      )
      .background(
        color = AppTheme.colors.background.primary,
        shape = AppTheme.shapes.semiMedium
      ),
  ) {
    Box(
      modifier = Modifier
        .align(Alignment.TopCenter)
        .padding(top = 8.dp)
        .size(width = 48.dp, height = 4.dp)
        .background(
          color = AppTheme.colors.dragger,
          shape = AppTheme.shapes.circle
        ),
    )
  }
}

private val COLLAPSED_HEIGHT = 64.dp
