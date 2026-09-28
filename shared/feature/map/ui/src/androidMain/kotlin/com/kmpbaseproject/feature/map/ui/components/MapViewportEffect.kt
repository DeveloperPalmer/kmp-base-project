package com.kmpbaseproject.feature.map.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import com.kmpbaseproject.feature.map.domain.entity.MapViewport
import com.yandex.mapkit.mapview.MapView

@Composable
internal fun MapViewportEffect(mapView: MapView, onCameraIdle: (MapViewport) -> Unit) {
  val currentOnCameraIdle by rememberUpdatedState(onCameraIdle)
  val tracker = remember(mapView) {
    MapViewportTracker(mapView) { viewport -> currentOnCameraIdle(viewport) }
  }
  DisposableEffect(tracker) {
    tracker.start()
    onDispose { tracker.stop() }
  }
}
